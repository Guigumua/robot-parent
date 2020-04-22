package com.github.guigumua.robot.starter.test.listener;

import com.github.guigumua.robot.common.event.EventType;
import com.github.guigumua.robot.common.event.message.MessageEvent;
import com.github.guigumua.robot.common.util.CQCode;
import com.github.guigumua.robot.starter.annotation.Filter;
import com.github.guigumua.robot.starter.annotation.Listener;
import com.github.guigumua.robot.starter.client.RobotClient;
import com.github.guigumua.robot.starter.test.bean.entity.Setu;
import com.github.guigumua.robot.starter.test.bean.service.SetuService;
import com.github.guigumua.robot.starter.test.filter.TestMappingFilter;
import com.github.guigumua.robot.starter.test.filter.TestPostFilter;
import com.github.guigumua.robot.starter.test.filter.TestPreFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Listener(isBreak = true)
public class SetuListener {
	@Autowired
	private SetuService setuService;

	@Listener(type = EventType.MESSAGE, isBreak = true, sort = 1)
	@Filter(value = "^setu\\s*(开启|关闭|(\\d{1,2}:\\d{2}(\\-\\d{1,2}:\\d{2})?))?$", filters = { TestPreFilter.class,
			TestPostFilter.class, TestMappingFilter.class })
	public void setu(MessageEvent event, RobotClient client) throws MalformedURLException, IOException {
//        HttpRequest request = HttpRequest.get("https://api.lolicon.app/setu/?apikey=" + "5e670c1065c5e472933258");
//        String json = request.body();
//        String url = JSONObject.parseObject(json).getJSONArray("data").getJSONObject(0).getString("url");
//        CQCode image = CQCode.getImage("https://search.pstatic.net/common?type=origin&src=" + url);
//        client.sendMsg(event, image.toString());
		Setu setu = setuService.randomSetu();
		client.sendMsg(event, CQCode.getImage(setu.getFile()).toString());
	}

	@Listener(type = EventType.MESSAGE, isBreak = true)
	@Filter("^转发\\s*\\d+\\s*\\[CQ:image.*")
	public void tansferImage(MessageEvent e, RobotClient client) throws IOException {
		String msg = e.getMessage();
		String groupId = StringUtils.substringBetween(msg, "发", "[").trim();
		List<CQCode> codes = CQCode.getCQCodes(msg);
		for (CQCode code : codes) {
			String url = code.get("url").toString();
			String fileName = code.get("file").toString();
			BufferedImage image = ImageIO.read(new URL(url));
			int rgb = image.getRGB(0, 0);
			image.setRGB(0, 0, rgb + 1);
			BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
					BufferedImage.TYPE_INT_RGB);
			/** 下面这个是画板 */
			Graphics g = bufferedImage.getGraphics();
			// Image x坐标 y坐标 图片宽度 图片高度 **
			g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
			g.dispose();
			File file = new File(fileName);
			ImageIO.write(bufferedImage, "png", file);
			client.sendGroupAndDelete(Long.parseLong(groupId),
					CQCode.getImage("file:///" + file.getAbsolutePath().replaceAll("\\\\", "/")).toString(), 30,
					TimeUnit.SECONDS);
		}
	}
}
