package com.github.guigumua.robot.common.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.ValueFilter;

public class CoolQWebSocketRequestSerializeFilter implements PropertyFilter, ValueFilter {
	private Map<String, Object> params = new HashMap<>();

	@Override
	public Object process(Object object, String name, Object value) {
		// 如果是params则替换为map
		if ("params".equals(name)) {
			return params;
		}
		return value;
	}

	@Override
	public boolean apply(Object object, String name, Object value) {
		// 不让action和params以外的字段序列化
		// action和params正常序列化
		// 其他字段丢到params中
		if (!"params".equals(name) && !"action".equals(name)) {
			// 将值保存到map中
			// 如果值已经存在,说明是进行params的序列化,需要返回true
			if (params.put(name, value) != null) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
}
