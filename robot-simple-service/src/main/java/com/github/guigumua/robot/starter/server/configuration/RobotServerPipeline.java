package com.github.guigumua.robot.starter.server.configuration;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class RobotServerPipeline extends ChannelInitializer<SocketChannel> {

	private ChannelHandler handler;

	public RobotServerPipeline(ChannelHandler handler) {
		this.handler = handler;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
//		p.addLast(new LoggingHandler(LogLevel.INFO));
		p.addLast(new HttpServerCodec());
		p.addLast(new HttpObjectAggregator(1 << 22));
		p.addLast(handler);
	}

}
