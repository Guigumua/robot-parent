package com.github.guigumua.robot.starter.server.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.guigumua.robot.starter.configuration.RobotManager;
import com.github.guigumua.robot.starter.server.listenerhandler.HandlerManager;
import com.github.guigumua.robot.starter.server.properties.RobotServerProperties;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Configuration
@EnableConfigurationProperties(RobotServerProperties.class)
public class RobotServerAutoConfiguration implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(RobotServerAutoConfiguration.class);

	@Bean
	public HandlerManager configureHandlerManager() {
		return new HandlerManager();
	}

	@Bean
	public RobotServerHandler configureHandler(HandlerManager handlerManager, RobotManager robotManager) {
		return new RobotServerHandler(handlerManager, robotManager);
	}

	@Bean
	public RobotServerPipeline configurePipeline(RobotServerHandler handler) {
		return new RobotServerPipeline(handler);
	}

	@Autowired
	private ApplicationContext context;

	@Autowired
	private RobotServerProperties properties;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		RobotServerPipeline pipeline = context.getBean(RobotServerPipeline.class);
		ServerBootstrap bootstrap = new ServerBootstrap();
		NioEventLoopGroup boss = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();
		String host = properties.getHost();
		int port = properties.getPort();
		ChannelFuture future = bootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
				.childOption(ChannelOption.SO_KEEPALIVE, true).childHandler(pipeline).bind(host, port)
				.addListener(new ChannelFutureListener() {
					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						if (future.isSuccess()) {
							logger.info("server start success in {}:{}", host, port);
						} else {
							logger.error("server start failed in {}:{}", host, port);
						}
					}
				});
		future.channel().closeFuture().sync();
	}
}
