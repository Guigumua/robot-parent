package com.github.guigumua.robot.common.event;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public interface Event extends Serializable {
	String getPostType();

	EventType getEventType();

	EventResponse getResponse();

	@JSONField(serialize = false, deserialize = false)
	long getSelfId();

	void setSelfId(long selfId);

	public static interface EventResponse extends Serializable {
		void setBlock(boolean isBlock);

		boolean isBlock();
	}

}
