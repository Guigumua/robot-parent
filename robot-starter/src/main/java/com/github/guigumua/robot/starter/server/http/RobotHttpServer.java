package com.github.guigumua.robot.starter.server.http;

import com.github.guigumua.robot.starter.server.AbstractRobotServer;
import com.github.guigumua.robot.starter.server.RobotServerEventProcessor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RobotHttpServer extends AbstractRobotServer {
    private final Logger logger = LoggerFactory.getLogger(RobotHttpServer.class);
    private final String host;
    private final int port;
    private ServerBootstrap bootstrap = new ServerBootstrap();
    private NioEventLoopGroup boss = new NioEventLoopGroup();
    private NioEventLoopGroup worker = new NioEventLoopGroup();
    private ChannelInitializer<SocketChannel> pipeline = new RobotHttpServerPipeline();

    public RobotHttpServer(String host, int port) {
        this.host = host;
        this.port = port;
        bootstrap.group(boss, worker).channel(NioServerSocketChannel.class).childHandler(pipeline);
    }

    public RobotHttpServer(String host, int port, RobotServerEventProcessor processor) {
        this.host = host;
        this.port = port;
        this.processor = processor;
        bootstrap.group(boss, worker).channel(NioServerSocketChannel.class).childHandler(pipeline);
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void start() throws InterruptedException {
        ChannelFuture future = bootstrap.bind(host, port).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    logger.info("服务器启动成功于：{}:{}", host, port);
                } else {
                    logger.error("服务器启动失败于： {}:{}", host, port);
                    Throwable cause = future.cause();
                    cause.printStackTrace();
                }
            }
        });
        future.channel().closeFuture().sync();
    }

    private class RobotHttpServerPipeline extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline p = ch.pipeline();
            p.addLast(new HttpServerCodec());
            p.addLast(new HttpObjectAggregator(1 << 20));
            p.addLast(new RobotHttpServerHandler());
        }
    }

    @Sharable
    private class RobotHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

        @Override
        public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
            handlerEvent(msg, ctx);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            logger.info("事件处理过程中发生异常,异常信息：{}", cause.getMessage());
            cause.printStackTrace();
        }

    }

}
