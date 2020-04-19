package com.github.guigumua.robot.starter.server.ws;

import com.github.guigumua.robot.starter.server.AbstractRobotServer;
import com.github.guigumua.robot.starter.server.RobotServerEventProcessor;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class RobotWebSocketServer extends AbstractRobotServer {
    private static final Logger logger = LoggerFactory.getLogger(RobotWebSocketServer.class);
    private WebSocketClientHandshaker handShaker;
    private ChannelPromise handshakeFuture;
    private final NioEventLoopGroup group = new NioEventLoopGroup();
    private final ChannelInitializer<SocketChannel> pipeline = new RobotWebSocketServerPipeline();
    private final Bootstrap bootstrap = new Bootstrap();
    private final String host;
    private final int port;
    private URI uri;
    /**
     * 用于拒绝http事件的配置
     */
    private boolean useDefaultHttpReject;
    private String defaultHttpRejectHost;
    private int defaultHttpRejectPort;

    public RobotWebSocketServer(String host, int port) {
        this.host = host;
        this.port = port;
        bootstrap.group(group).channel(NioSocketChannel.class).handler(pipeline);
    }

    public RobotWebSocketServer(String host, int port, RobotServerEventProcessor processor) {
        this.host = host;
        this.port = port;
        this.processor = processor;
        bootstrap.group(group).channel(NioSocketChannel.class).handler(pipeline);
    }

    public boolean isUseDefaultReject() {
        return useDefaultHttpReject;
    }

    public void setUseDefaultReject(boolean useDefaultReject) {
        this.useDefaultHttpReject = useDefaultReject;
    }

    public String getHttpHost() {
        return defaultHttpRejectHost;
    }

    public void setHttpHost(String httpHost) {
        this.defaultHttpRejectHost = httpHost;
    }

    public int getHttpPort() {
        return defaultHttpRejectPort;
    }

    public void setHttpPort(int httpPort) {
        this.defaultHttpRejectPort = httpPort;
    }

    private class RobotWebSocketServerPipeline extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) {
            ChannelPipeline p = ch.pipeline();
            p.addLast(new HttpClientCodec(), new HttpObjectAggregator(1 << 20));
            p.addLast("handler", new RobotWebSocketServerHandler());
            handshakeFuture = ch.newPromise();
        }
    }

    private class RobotWebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            Channel ch = ctx.channel();
            FullHttpResponse response;
            // 判断接收的请求是否是牵手
            if (!handShaker.isHandshakeComplete()) {
                try {
                    response = (FullHttpResponse) msg;
                    // 握手协议返回，设置结束握手
                    handShaker.finishHandshake(ch, response);
                    // 设置成功
                    handshakeFuture.setSuccess();
                    logger.debug("连接coolq event的websocket连接牵手成功！uri：{}", uri);
                } catch (WebSocketHandshakeException var7) {
                    FullHttpResponse res = (FullHttpResponse) msg;
                    String errorMsg = String.format("WebSocket客户端连接失败，状态为:%s", res.status());
                    handshakeFuture.setFailure(new Exception(errorMsg));
                }
            } else if (msg instanceof FullHttpResponse) {
                response = (FullHttpResponse) msg;
                // 可以吧字符码转为指定类型
                // this.listener.onFail(response.status().code(),
                // response.content().toString(CharsetUtil.UTF_8));
                throw new IllegalStateException("未预料的错误(getStatus=" + response.status() + ", content="
                        + response.content().toString(CharsetUtil.UTF_8) + ')');
            } else {// 如果不是牵手
                WebSocketFrame frame = (WebSocketFrame) msg;
                if (frame instanceof TextWebSocketFrame) {
                    handlerEvent(msg, ctx);
                } else if (frame instanceof BinaryWebSocketFrame) {
                    logger.debug(frame.toString());
                } else if (frame instanceof PongWebSocketFrame) {
                    logger.debug(frame.toString());
                } else if (frame instanceof CloseWebSocketFrame) {
                    ch.close();
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            processor.defaultRespond(ctx);
            logger.error("事件处理过程中发生异常：{}", cause.getMessage());
            cause.printStackTrace();
        }

    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void start() throws InterruptedException {
        try {
            uri = new URI("ws://" + host + ":" + port + "/event");
            HttpHeaders httpHeaders = new DefaultHttpHeaders();
            // 进行握手
            this.handShaker = WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null,
                    true, httpHeaders);
            // 需要协议的host和port
            Channel channel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();
            handShaker.handshake(channel);
            Thread.sleep(1000);
            this.handshakeFuture.sync();
            if (useDefaultHttpReject) {
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                ChannelFuture future = serverBootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                        .channel(NioServerSocketChannel.class).childHandler(new DefaultRejectHttpPipeline())
                        .bind(defaultHttpRejectHost, defaultHttpRejectPort);
                logger.debug("http拒绝服务器已启动");
                future.channel().closeFuture().sync();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static class DefaultRejectHttpPipeline extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) {
            ChannelPipeline p = ch.pipeline();
            p.addLast(new HttpServerCodec());
            p.addLast(new HttpObjectAggregator(1 << 20));
            p.addLast(new DefaultRejectHttpHandler());
        }

    }

    @Sharable
    private static class DefaultRejectHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

        @Override
        public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {
            DefaultFullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.NO_CONTENT);
            ctx.writeAndFlush(res);
        }
    }
}
