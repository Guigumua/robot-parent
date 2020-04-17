package com.github.guigumua.robot.common.event;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.event.request.RequestEvent;

public interface Event extends Serializable {
	String getPostType();

	EventType getEventType();

	EventResponse getResponse();

	@JSONField(serialize = false, deserialize = false)
	long getSelfId();

	void setSelfId(long selfId);

	interface EventResponse extends Serializable {
		Event.EventResponse setBlock(boolean isBlock);

		boolean isBlock();
	}

}
