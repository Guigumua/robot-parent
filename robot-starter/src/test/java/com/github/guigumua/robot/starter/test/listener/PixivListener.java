package com.github.guigumua.robot.starter.test.listener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.guigumua.robot.common.event.EventType;
import com.github.guigumua.robot.common.event.message.MessageEvent;
import com.github.guigumua.robot.common.util.CQCode;
import com.github.guigumua.robot.common.util.HttpRequest;
import com.github.guigumua.robot.starter.annotation.Filter;
import com.github.guigumua.robot.starter.annotation.Listener;
import com.github.guigumua.robot.starter.client.RobotClient;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PixivListener {
	@Listener(type = EventType.MESSAGE, isBreak = true)
	@Filter("^pixiv\\s*\\d+")
	public void pid(MessageEvent e, RobotClient client) {
		String pid = StringUtils.deleteWhitespace(StringUtils.substringAfter(e.getMessage(), "v"));
		HttpRequest req = HttpRequest.get("https://api.imjad.cn/pixiv/v2/?id" + pid);
		String json = req.body();
		String url = JSONObject.parseObject(json).getJSONObject("illust").getJSONObject("image_urls")
				.getString("large");
		String proxyUrl = "https://search.pstatic.net/common?type=origin&src="
				+ StringUtils.replace(url, "ximg.net/c/600x1200_90_webp", "ixiv.cat");
		client.sendMsg(e, CQCode.getImage(proxyUrl).toString());
	}

	@Listener(type = EventType.MESSAGE, isBreak = true)
	@Filter(value = "^pixiv\\s?\\[CQ:image.*")
	public void pic(MessageEvent e, RobotClient client) {
		List<CQCode> codes = CQCode.getCQCodes(e.getMessage());
		codes.stream().forEach(code -> {
			String url = code.get("url");
			Document document;
			try {
				document = Jsoup.connect("https://saucenao.com/search.php").data("url", url).userAgent(
						"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")
						.post();
				Elements titles = document.getElementsByClass("resulttitle");
				Elements contents = document.getElementsByClass("resultcontentcolumn");
				String _url = contents.get(0).child(1).attr("href");
				String title = titles.get(0).child(0).text();
				String pid = contents.get(0).child(1).text();
				String member = contents.get(0).child(5).text();
				String msg = String.format("链接：%s\n标题：%s\npid：%s\n上传者：%s", _url, title, pid, member);
				client.sendMsg(e, msg);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

	}

	@Listener(type = EventType.MESSAGE, isBreak = true)
	@Filter(value = "^pixiv\\s*\\S((?!CQ:image).)*$")
	public void pixiv(MessageEvent e, RobotClient client) {
		String msg = e.getMessage();
		String word = StringUtils.deleteWhitespace(StringUtils.substring(msg, 5));
		String url = searchByWord(word);
		if (url != null) {
			String proxyUrl = "https://search.pstatic.net/common?type=origin&src="
					+ StringUtils.replace(url, "ximg.net/c/600x1200_90_webp", "ixiv.cat");
			client.sendMsg(e, CQCode.getImage(proxyUrl).toString());
		} else {
			client.sendMsg(e, "没找到你想要的图片");
		}
	}

	private static Map<String, List<String>> cache = new HashMap<>();
	private static Map<String, Integer> pageCache = new HashMap<>();
	private static String filterTags = "r18 R18 r-18 R-18 R-18G r-18G";

	public static String searchByWord(String word) {
		int page = 1;
		// 关键字查询过
		if (cache.containsKey(word)) {
			List<String> list = cache.get(word);
			// 有查询结果缓存
			if (list.size() != 0) {
				return list.remove(0);
			} else {
				// 没有说明要新的一页了
				page = pageCache.get(word) + 1;
				pageCache.put(word, page);
			}
		}
		// 没有查询过关键字
		HttpRequest req = HttpRequest
				.get("https://api.imjad.cn/pixiv/v2/" + String.format("?type=search&page=%d&word=%s", page, word));
		String json = req.body();
		JSONObject jsonObject = JSONObject.parseObject(json);
		JSONArray illusts = jsonObject.getJSONArray("illusts");
		if (illusts == null) {
			return null;
		}
		List<String> list = new ArrayList<>();
		illusts.stream()
				// 过滤
				.filter(o -> {
					for (Object tag : ((JSONObject) o).getJSONArray("tags")) {
						String name = ((JSONObject) tag).getString("name");
						// 如果tag是任何过滤字段的一个就不放行
						if (StringUtils.contains(filterTags, name)) {
							return false;
						}
					}
					// 不包含过滤tag
					return true;
					// 排序 输出收藏的DESC
				}).collect(Collectors.toList()).stream().sorted((a, b) -> {
					int bookmark1 = ((JSONObject) a).getInteger("total_bookmarks");
					int bookmark2 = ((JSONObject) b).getInteger("total_bookmarks");
					return bookmark2 - bookmark1;
					// 获取所有结果保存到list
				}).forEach(o -> {
					String url = ((JSONObject) o).getJSONObject("image_urls").getString("large");
					list.add(url);
				});
		if (list.size() == 0) {
			return null;
		}
		// 缓存结果
		cache.put(word, list);
		// 页缓存
		pageCache.put(word, page);
		return cache.get(word).remove(0);
	}
}
