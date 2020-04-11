package com.github.guigumua.robot.commom.event;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public interface Event extends Serializable {
	String getPostType();

	EventType getEventType();

	@JSONField(serialize = false)
	EventResponse getResponse();

	long getSelfId();

	public static interface EventResponse extends Serializable {
		void setBlock(boolean isBlock);

		boolean isBlock();
	}

}
