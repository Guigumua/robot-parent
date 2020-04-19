package com.github.guigumua.robot.starter.server.http;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.common.util.JsonUtil;
import com.github.guigumua.robot.starter.server.RobotServerEventProcessor;
import com.github.guigumua.robot.starter.server.listener.ListenerContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.StringUtils;

public class RobotHttpServerEventProcessorAdapter implements RobotServerEventProcessor {
    protected final DefaultFullHttpResponse DEFAULT_RESPONSE = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
            HttpResponseStatus.NO_CONTENT);
    @Override
    public Event resolveEvent(Object msg) {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String idStr = request.headers().get("X-Self-ID");
            if (!StringUtils.isWhitespace(idStr)) {
                long id = Long.parseLong(idStr);
                Event event = JsonUtil.getEvent(request.content().toString(CharsetUtil.UTF_8));
                if (event != null) {
                    event.setSelfId(id);
                }
            }
        }
        return null;
    }

    @Override
    public void defaultRespond(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(DEFAULT_RESPONSE.retain());
    }

    @Override
    public boolean resultHandler(ListenerContext context, Channel channel) {
        Object result = context.getResult();
        if (result instanceof Event.EventResponse) {
            DefaultFullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            ByteBuf content = res.content();
            content.writeBytes(JsonUtil.toJSON(result).getBytes(CharsetUtil.UTF_8));
            res.headers().add(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            channel.writeAndFlush(res);
            return true;
        }
        return false;
    }
}
