package com.github.guigumua.robot.commom.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.github.guigumua.robot.commom.event.Event;
import com.github.guigumua.robot.commom.event.bean.message.DiscussMessageEvent;
import com.github.guigumua.robot.commom.event.bean.message.GroupMessageEvent;
import com.github.guigumua.robot.commom.event.bean.message.PrivateMessageEvent;
import com.github.guigumua.robot.commom.event.bean.notice.FriendAddNoticeEvent;
import com.github.guigumua.robot.commom.event.bean.notice.GroupAdminNoticeEvent;
import com.github.guigumua.robot.commom.event.bean.notice.GroupBanNoticeEvent;
import com.github.guigumua.robot.commom.event.bean.notice.GroupDecreaseNoticeEvent;
import com.github.guigumua.robot.commom.event.bean.notice.GroupIncreaseNoticeEvent;
import com.github.guigumua.robot.commom.event.bean.notice.GroupUploadNoticeEvent;
import com.github.guigumua.robot.commom.event.bean.request.FriendAddRequestEvent;
import com.github.guigumua.robot.commom.event.bean.request.GroupAddInviteRequestEvent;

public class JsonUtil {
	private final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static SerializeConfig config;

	static {
		config = new SerializeConfig();
		config.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		config.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCase);
	}

	private static final SerializerFeature[] features = {
			// 输出空置字段
			SerializerFeature.WriteMapNullValue,
			// list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullListAsEmpty,
			// 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullNumberAsZero,
			// Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullBooleanAsFalse,
			// 字符类型字段如果为null，输出为""，而不是null
			SerializerFeature.WriteNullStringAsEmpty 
	};

	/** Object TO Json String 字符串输出 */
	public static String toJSON(Object object) {
		try {
			return JSON.toJSONString(object, config, features);
		} catch (Exception e) {
			logger.error("JsonUtil | method=toJSON() | 对象转为Json字符串 Error！" + e.getMessage(), e);
		}
		return null;
	}

	/** Object TO Json String Json-lib兼容的日期输出格式 */
	public static String toJSONLib(Object object) {
		try {
			return JSON.toJSONString(object, config, features);
		} catch (Exception e) {
			logger.error("JsonUtil | method=toJSONLib() | 对象转为Json字符串 Json-lib兼容的日期输出格式   Error！" + e.getMessage(), e);
		}
		return null;
	}

	/** 转换为数组 Object */
	public static Object[] toArray(String text) {
		try {
			return toArray(text, null);
		} catch (Exception e) {
			logger.error("JsonUtil | method=toArray() | 将json格式的数据转换为数组 Object  Error！" + e.getMessage(), e);
		}
		return null;
	}

	/** 转换为数组 （可指定类型） */
	public static <T> Object[] toArray(String text, Class<T> clazz) {
		try {
			return JSON.parseArray(text, clazz).toArray();
		} catch (Exception e) {
			logger.error("JsonUtil | method=toArray() | 将json格式的数据转换为数组 （可指定类型）   Error！" + e.getMessage(), e);
		}
		return null;
	}

	/** Json 转为 Jave Bean */
	public static <T> T toBean(String text, Class<T> clazz) {
		try {
			return JSON.parseObject(text, clazz);
		} catch (Exception e) {
			logger.error("JsonUtil | method=toBean() | Json 转为  Jave Bean  Error！" + e.getMessage(), e);
		}
		return null;
	}

	/** Json 转为 Map */
	public static Map<?, ?> toMap(String json) {
		try {
			return JSON.parseObject(json);
		} catch (Exception e) {
			logger.error("JsonUtil | method=toMap() | Json 转为   Map {},{}" + e.getMessage(), e);
		}
		return null;
	}

	/** Json 转 List,Class 集合中泛型的类型，非集合本身，可json-lib兼容的日期格式 */
	public static <T> List<T> toList(String text, Class<T> clazz) {
		try {
			return JSON.parseArray(text, clazz);
		} catch (Exception e) {
			logger.error("JsonUtil | method=toList() | Json 转为   List {},{}" + e.getMessage(), e);
		}
		return null;
	}

	/** 从json字符串获取指定key的字符串 */
	public static Object getValueFromJson(final String json, final String key) {
		try {
			if (StringUtils.isBlank(json) || StringUtils.isBlank(key)) {
				return null;
			}
			return JSON.parseObject(json).getString(key);
		} catch (Exception e) {
			logger.error("JsonUtil | method=getStringFromJson() | 从json获取指定key的字符串 Error！" + e.getMessage(), e);
		}
		return null;
	}

	public static Event getEvent(String jsonStr) {
		String type = null;
		if ((type = (String) JsonUtil.getValueFromJson(jsonStr, "message_type")) != null
		/* && !StringUtils.isWhitespace(type) */) {
			if ("private".equals(type)) {
				return JsonUtil.toBean(jsonStr, PrivateMessageEvent.class);
			} else if ("group".equals(type)) {
				return JsonUtil.toBean(jsonStr, GroupMessageEvent.class);
			} else {
				// if ("discuss".equals(type))
				return JsonUtil.toBean(jsonStr, DiscussMessageEvent.class);
			}
		} else if ((type = (String) JsonUtil.getValueFromJson(jsonStr, "notice_type")) != null) {
			if ("group_upload".equals(type)) {
				return JsonUtil.toBean(jsonStr, GroupUploadNoticeEvent.class);
			} else if ("group_admin".equals(type)) {
				return JsonUtil.toBean(jsonStr, GroupAdminNoticeEvent.class);
			} else if ("group_decrease".equals(type)) {
				return JsonUtil.toBean(jsonStr, GroupDecreaseNoticeEvent.class);
			} else if ("group_increase".equals(type)) {
				return JsonUtil.toBean(jsonStr, GroupIncreaseNoticeEvent.class);
			} else if ("group_ban".equals(type)) {
				return JsonUtil.toBean(jsonStr, GroupBanNoticeEvent.class);
			} else {
				// if ("friend_add".equals(type))
				return JsonUtil.toBean(jsonStr, FriendAddNoticeEvent.class);
			}
		} else if ((type = (String) JsonUtil.getValueFromJson(jsonStr, "request_type")) != null) {
			if ("friend".equals(type)) {
				return JsonUtil.toBean(jsonStr, FriendAddRequestEvent.class);
			} else {
				// group
				return JsonUtil.toBean(jsonStr, GroupAddInviteRequestEvent.class);

			}
		}
		return null;
	}
}
