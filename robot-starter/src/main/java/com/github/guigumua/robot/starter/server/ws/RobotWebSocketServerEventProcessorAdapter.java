package com.github.guigumua.robot.starter.server.ws;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.common.util.JsonUtil;
import com.github.guigumua.robot.starter.server.RobotServerEventProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;


public class RobotWebSocketServerEventProcessorAdapter implements RobotServerEventProcessor {
    protected final TextWebSocketFrame DEFAULT_RESPONSE = new TextWebSocketFrame();

    @Override
    public Event resolveEvent(Object msg) {
        if (msg instanceof TextWebSocketFrame) {
            TextWebSocketFrame frame = (TextWebSocketFrame) msg;
            String json = frame.text();
            return JsonUtil.getEvent(json);
        }
        return null;
    }

    @Override
    public void defaultRespond(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(DEFAULT_RESPONSE.retain());
    }
}
