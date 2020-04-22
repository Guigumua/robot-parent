package com.github.guigumua.robot.starter.server.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.common.util.JsonUtil;
import com.github.guigumua.robot.starter.server.RobotServerEventProcessor;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class RobotWebSocketServerEventProcessorAdapter implements RobotServerEventProcessor {
//	protected final TextWebSocketFrame DEFAULT_RESPONSE = new TextWebSocketFrame();
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Event resolveEvent(Object msg) {
		if (msg instanceof TextWebSocketFrame) {
			TextWebSocketFrame frame = (TextWebSocketFrame) msg;
			String json = frame.text();
			logger.debug("事件解析完成：{}", json);
			return JsonUtil.getEvent(json);
		}
		return null;
	}
}
