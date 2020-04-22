package com.github.guigumua.robot.starter.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

public abstract class AbstractRobotClient implements RobotClient {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected final NioEventLoopGroup group = new NioEventLoopGroup();
	protected final Bootstrap bootstrap = new Bootstrap();
	protected final ChannelHandler handler;
	protected final String host;
	protected final int port;

	protected long selfId;
	protected boolean async;

	protected AbstractRobotClient(String host, int port, ChannelHandler handler) {
		this.host = host;
		this.port = port;
		this.handler = handler;
		bootstrap.group(group).option(ChannelOption.SO_KEEPALIVE, true).channel(NioSocketChannel.class)
				.handler(new RobotClientPipeline(handler));
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public int getPort() {
		return port;
	}

	public boolean isAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	public long getSelfId() {
		return selfId;
	}

	protected ChannelHandler getHandler() {
		return handler;
	}

	public static class RobotClientPipeline extends ChannelInitializer<SocketChannel> {
		protected final ChannelHandler handler;

		private RobotClientPipeline(ChannelHandler handler) {
			this.handler = handler;
		}

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ChannelPipeline p = ch.pipeline();
			p.addLast(new HttpClientCodec());
			p.addLast(new HttpObjectAggregator(1 << 20));
			p.addLast(handler);
		}
	}
}
