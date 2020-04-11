package com.github.guigumua.robot.commom.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class CQCode {
	private static final String prefix = "[";
	private static final String separator = ",";
	private static final String suffix = "]";
	private static final String link = "=";
//	[CQ:face,id={1}]
	public static final String FACE_TYPE = "CQ:face";
//	[CQ:emoji,id={1}]
	public static final String EMOJI_TYPE = "CQ:emoji";
//	[CQ:bface,id={1}]
	public static final String BFACE_TYPE = "CQ:bface";
//	[CQ:contact,type={1},id={2}]
	public static final String CONTACT_TYPE = "CQ:contact";
//	[CQ:sface,id={1}]
	public static final String SFACE_TYPE = "CQ:sface";
//	[CQ:image,file={1}]
	public static final String IMAGE_TYPE = "CQ:image";
//	[CQ:record,file={1},magic={2}]
	public static final String RECORD_TYPE = "CQ:record";
//	[CQ:at,qq={1}]
	public static final String AT_TYPE = "CQ:at";
//	[CQ:rps,type={1}]
	public static final String RPS_TYPE = "CQ:rps";
//	[CQ:dice,type={1}]
	public static final String DICE_TYPE = "CQ:dice";
//	[CQ:shake]
	public static final String SHAKE_TYPE = "CQ:shake";
//	[CQ:anonymous,ignore={1}]
	public static final String ANONYMOUS_TYPE = "CQ:anonymous";
//	[CQ:location,lat={1},lon={2},title={3},content={4}]
	public static final String LOCATION_TYPE = "CQ:location";
//	[CQ:sign,location={1},title={2},image={3}]
	public static final String SIGN_TYPE = "CQ:sign";
//	[CQ:music,type={1},id={2},style={3}]
//	[CQ:music,type=custom,url={1},audio={2},title={3},content={4},image={5}]
	public static final String MUSICE_TYPE = "CQ:music";
//	[CQ:share,url={1},title={2},content={3},image={4}]
	public static final String SHARE_TYPE = "CQ:share";
	private String type;

	private Map<String, Object> data = new HashMap<>();
	/**
	 * 获取cq码类型
	 * @return
	 */
	public String getType() {
		return type;
	}

	private void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取cq码的值
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return data.get(key);
	}
	
	private Object put(String key, Object value) {
		return data.put(key, value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix).append(type);
		for (Entry<String, Object> e : data.entrySet()) {
			builder.append(separator).append(e.getKey()).append(link).append(e.getValue());
		}
		builder.append(suffix);
		return builder.toString();
	}

	private static final String regex = "\\[CQ:[a-z]*(,[a-z]*=[^,\\s]*)*\\]";
	private static final Pattern p = Pattern.compile(regex);

	/**
	 * 获取消息中的cqcode
	 * 
	 * @param message 消息内容
	 * @return 消息中的第一个cqcode
	 */
	public static CQCode getCQCode(String message) {
		Matcher matcher = p.matcher(message);
		if (matcher.find()) {
			CQCode cqCode = new CQCode();
			String cqString = matcher.group();
			String[] split = StringUtils.substring(cqString, 1, cqString.length() - 1).split(",");
			cqCode.setType(split[0]);
			for (int i = 1; i < split.length; ++i) {
				String[] e = split[i].split("=");
				cqCode.put(e[0], e[1]);
			}
			return cqCode;
		}
		return null;
	}

	/**
	 * 获取消息中的所有cqcode
	 * 
	 * @param message 消息
	 * @return 消息中的所有cqcode数组
	 */
	public static List<CQCode> getCQCodes(String message) {
		List<CQCode> cqCodes = new ArrayList<>();
		Matcher matcher = p.matcher(message);
		while (matcher.find()) {
			CQCode cqCode = new CQCode();
			String cqString = matcher.group();
			String[] split = StringUtils.substringBetween(cqString, "[", "]").split(",");
			cqCode.setType(split[0]);
			for (int i = 1; i < split.length; ++i) {
				String[] e = split[i].split("=");
				cqCode.put(e[0], e[1]);
			}
			cqCodes.add(cqCode);
		}
		return cqCodes;
	}

	/**
	 * 获取系统表情的cqcode
	 * 
	 * @param id 系统表情id
	 * @return
	 */
	public static CQCode getFace(int id) {
		CQCode code = new CQCode();
		code.setType(FACE_TYPE);
		code.put("id", id);
		return code;
	}

	/**
	 * 获取emoji表情的cqcode
	 * 
	 * @param id emoji表情的id
	 * @return
	 */
	public static CQCode getEmoji(int id) {
		CQCode code = new CQCode();
		code.setType(EMOJI_TYPE);
		code.put("id", id);
		return code;
	}

	public static CQCode getBface(int id) {
		CQCode code = new CQCode();
		code.setType(BFACE_TYPE);
		code.put("id", id);
		return code;
	}

	/**
	 * 推送名片
	 * 
	 * @param type 名片类型 group private
	 * @param id   推送的id
	 * @return
	 */
	public static CQCode getContact(String type, int id) {
		CQCode code = new CQCode();
		code.setType(CONTACT_TYPE);
		code.put("id", id);
		code.put("type", type);
		return code;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public static CQCode getSface(int id) {
		CQCode code = new CQCode();
		code.setType(SFACE_TYPE);
		code.put("id", id);
		return code;
	}

	/**
	 * 获取图片的cq
	 * 
	 * @param file data/image下的图片路径，或者网络图片url，或者是file
	 *             url(file:///****),又或者使用base64编码(base64://***)
	 * 
	 * @return
	 */
	public static CQCode getImage(String file) {
		CQCode code = new CQCode();
		code.setType(IMAGE_TYPE);
		code.put("file", file);
		return code;
	}

	/**
	 * 获取语音
	 * 
	 * @param file  语音文件路径，同图片格式
	 * @param magic 是否变声
	 * @return
	 */
	public static CQCode getRecord(String file, boolean magic) {
		CQCode code = new CQCode();
		code.setType(RECORD_TYPE);
		code.put("record", file);
		code.put("magic", magic);
		return code;
	}

	/**
	 * 
	 * @param userId 被@的人的id
	 * @return
	 */
	public static CQCode getAt(long userId) {
		CQCode code = new CQCode();
		code.setType(AT_TYPE);
		code.put("qq", String.valueOf(userId));
		return code;
	}

	/**
	 * 猜拳
	 * 
	 * @param type 1:石头 2:剪刀 3:布
	 * @return
	 */
	public static CQCode getRps(int type) {
		CQCode code = new CQCode();
		code.setType(RPS_TYPE);
		code.put("type", type);
		return code;
	}

	/**
	 * 掷骰子
	 * 
	 * @param type 点数
	 * @return
	 */
	public static CQCode getDice(int type) {
		CQCode code = new CQCode();
		code.setType(DICE_TYPE);
		code.put("type", type);
		return code;
	}

	/**
	 * 戳一戳 私聊有效
	 * 
	 * @param type
	 * @return
	 */
	public static CQCode getShake() {
		CQCode code = new CQCode();
		code.setType(SHAKE_TYPE);
		return code;
	}

	/**
	 * 匿名，加在群消息开头
	 * 
	 * @param ignore 为 true 时，代表不强制使用匿名，如果匿名失败将转为普通消息发送。 为 false 或 ignore
	 *               参数被忽略时，代表强制使用匿名，如果匿名失败将取消该消息的发送。
	 * @return
	 */
	public static CQCode getAnonymous(boolean ignore) {
		CQCode code = new CQCode();
		code.setType(ANONYMOUS_TYPE);
		code.put("ignore", ignore);
		return code;
	}

	/**
	 * 位置分享
	 * 
	 * @param content 位置名称
	 * @param title   位置标题
	 * @param lat     经度
	 * @param lon     纬度
	 * @return
	 */
	public static CQCode getLocation(String content, String title, double lat, double lon) {
		CQCode code = new CQCode();
		code.setType("CQ:location");
		code.put("content", content);
		code.put("title", title);
		code.put("lat", String.valueOf(lat));
		code.put("lon", String.valueOf(lon));
		code.put("style", String.valueOf(1));
		return code;
	}

	/**
	 * 音乐分享
	 * 
	 * @param type qq或者163
	 * @param id   对应平台的音乐数字id
	 * @return
	 */
	public static CQCode getMusic(String type, long id) {
		CQCode code = new CQCode();
		code.setType(MUSICE_TYPE);
		code.put("type", type);
		code.put("id", id);
		return code;
	}

	/**
	 * 自定义音乐分享
	 * 
	 * @param url      音乐分享链接，点击后将会跳转的页面
	 * @param audioUrl 音乐的链接
	 * @param title    音乐标题，建议12字以内
	 * @param content  音乐简介，建议30字以内
	 * @param image    音乐封面图片
	 * @return
	 */
	public static CQCode getCustomMusic(String url, String audioUrl, String title, String content, String image) {
		CQCode code = new CQCode();
		code.setType(MUSICE_TYPE);
		code.put("url", url);
		code.put("audio", audioUrl);
		code.put("title", title);
		code.put("content", content);
		code.put("image", image);
		return code;
	}

	/**
	 * 分享链接
	 * 
	 * @param url     跳转的链接
	 * @param title   标题
	 * @param content 简介
	 * @param image   分享封面
	 * @return
	 */
	public static CQCode getShare(String url, String title, String content, String image) {
		CQCode code = new CQCode();
		code.setType(SHARE_TYPE);
		code.put("url", url);
		code.put("title", title);
		code.put("content", content);
		code.put("image", image);
		return code;
	}
}
