package com.github.guigumua.robot.starter.server.configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.common.event.EventType;
import com.github.guigumua.robot.common.event.message.MessageEvent;
import com.github.guigumua.robot.common.util.JsonUtil;
import com.github.guigumua.robot.starter.annotation.GlobalFilter;
import com.github.guigumua.robot.starter.client.RobotClient;
import com.github.guigumua.robot.starter.configuration.RobotManager;
import com.github.guigumua.robot.starter.server.filter.MappingFilter;
import com.github.guigumua.robot.starter.server.filter.PostFilter;
import com.github.guigumua.robot.starter.server.filter.PreFilter;
import com.github.guigumua.robot.starter.server.listener.ListenerContext;
import com.github.guigumua.robot.starter.server.listener.ListenerManager;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.util.CharsetUtil;

public class RobotWebSocketServer implements RobotServer {
    private static final Logger logger = LoggerFactory.getLogger(RobotWebSocketServer.class);
    private WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;
    private final NioEventLoopGroup group = new NioEventLoopGroup();
    private final ChannelInitializer<SocketChannel> pipeline = new RobotWebSocketServertPipeline();
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

    public RobotWebSocketServer(String host, int port) {
        this.host = host;
        this.port = port;
        bootstrap.group(group).channel(NioSocketChannel.class).handler(pipeline);
    }

    private class RobotWebSocketServertPipeline extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline p = ch.pipeline();
            p.addLast(new HttpClientCodec(), new HttpObjectAggregator(1 << 20));
            p.addLast("handler", new RobotWebSocketServerHandler());
            handshakeFuture = ch.newPromise();
        }
    }

    private class RobotWebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//			handshakeFuture = ctx.newPromise();
        }

        private final TextWebSocketFrame DEFAULT_RESPONSE = new TextWebSocketFrame();

        @Override
        public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            long start = System.currentTimeMillis();
            Channel ch = ctx.channel();
            FullHttpResponse response;
            // 判断接收的请求是否是牵手
            if (!handshaker.isHandshakeComplete()) {
                try {
                    response = (FullHttpResponse) msg;
                    // 握手协议返回，设置结束握手
                    handshaker.finishHandshake(ch, response);
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
                    // 解析
                    Event event = resolveMsg(msg);
                    if (event == null) {
                        ctx.writeAndFlush(DEFAULT_RESPONSE.retain());
                        return;
                    }
                    // 通过事件注册机器人
                    registerClientByEvent(ctx.channel(), event);
                    // 映射获取所有上下文对象
                    Set<ListenerContext> contexts = mappingFilter(event);
                    // 是否直接响应过
                    boolean isResponsed = false;
                    // 处理
                    for (ListenerContext context : contexts) {
                        // 参数注入
                        paramInject(context, event);
                        Object[] params = context.getParams();
                        // 前置拦截器
                        if (preFilter(context, event, params)) {
                            continue;
                        }
                        // 执行监听器
                        doHandler(context, event, params);
                        // 直接响应，只会执行第一个直接响应
                        // 后置拦截器
                        if (postFilter(context, event, params)) {
                            continue;
                        }
                        if (!isResponsed) {
                            isResponsed = resultHandler(context, ctx.channel());
                        }
                        // 执行完成后是否跳过其他监听器的执行
                        if (context.isBreak()) {
                            break;
                        }
                    }
                    // 没有直接响应则写回默认响应
                    if (!isResponsed) {
                        ctx.writeAndFlush(DEFAULT_RESPONSE.retain());
                    }
                } else if (frame instanceof BinaryWebSocketFrame) {
//					System.out.println("二进制WebSocketFrame");
                } else if (frame instanceof PongWebSocketFrame) {
                    // 返回心跳监测
                    // System.out.println("WebSocket客户端接收到pong");
                } else if (frame instanceof CloseWebSocketFrame) {
//					System.out.println("接收关闭贞");
                    ch.close();
                }
            }
            long end = System.currentTimeMillis();
            logger.info("事件处理耗时：{}ms",end - start);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            TextWebSocketFrame res = new TextWebSocketFrame();
            ctx.writeAndFlush(res);
            logger.error("事件处理过程中发生异常：{}", cause.getLocalizedMessage());
        }

    }

    private ApplicationContext context;
    private MappingFilter globalMappingFilter;
    private PreFilter globalPreFilter;
    private PostFilter globalPostFilter;
    private final Lock lock = new ReentrantLock();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        Map<String, Object> filters = context.getBeansWithAnnotation(GlobalFilter.class);
        for (Object o : filters.values()) {
            if (o instanceof MappingFilter && globalMappingFilter == null) {
                globalMappingFilter = (MappingFilter) o;
            } else if (o instanceof PreFilter && globalPreFilter == null) {
                globalPreFilter = (PreFilter) o;
            } else if (o instanceof PostFilter && globalPostFilter == null) {
                globalPostFilter = (PostFilter) o;
            }
        }
        logger.debug("完成初始化全局拦截器");
    }

    @Override
    public Event resolveMsg(Object msg) {
        TextWebSocketFrame frame = (TextWebSocketFrame) msg;
        String json = frame.text();
        logger.debug("事件解析完成：{}", json);
        return JsonUtil.getEvent(json);
    }

    @Override
    public void registerClientByEvent(Channel channel, Event event) {
        long id = event.getSelfId();
        lock.lock();
        RobotClient client = RobotManager.getInstance().get(id);
        // 如果接收到的事件来自没有在robotManager中注册的cq端，就新增到robotManager中
        if (client == null) {
            logger.info("开始通过上报事件注册机器人，使用默认端口5700");
            InetSocketAddress addr = (InetSocketAddress) channel.remoteAddress();
            String host = addr.getHostName();
            RobotManager.getInstance().registerRobotClient(host, 5700, false);
        }
        lock.unlock();
    }

    @Override
    public Set<ListenerContext> mappingFilter(Event event) {
        EventType type = event.getEventType();
        Set<ListenerContext> contexts = ListenerManager.getInstance().getListenerChain(type);
        // 如果是监听消息事件，先正则过滤
        if (EventType.isListenMsg(type)) {
            contexts = contexts.stream().filter(context -> {
                if (globalMappingFilter != null && !globalMappingFilter.apply(event, context)) {
                    logger.debug("事件：{}映射监听器时被全局映射拦截器：{}拦截", event, globalMappingFilter.getClass());
                    return false;
                }
                String regex = context.getRegex();
                String message = ((MessageEvent) event).getMessage();
                ArrayList<MappingFilter> mappingFilters = context.getMappingFilters();
                for (MappingFilter filter : mappingFilters) {
                    if (!filter.apply(event, context)) {
                        logger.debug("事件：{}映射监听器：{}成功但被映射拦截器：{}拦截", event, context.getMethod(), filter.getClass());
                        return false;
                    }
                }
                return message.matches(regex);
            }).collect(Collectors.toSet());
        }
        logger.debug("发现事件映射的监听器序列：{}", contexts);
        return contexts;
    }

    @Override
    public void paramInject(ListenerContext context, Event event) {
        // 参数注入
        Map<Integer, Parameter> paramsMap = context.getParamsMap();
        Object[] params = context.getParams();
        for (Entry<Integer, Parameter> e : paramsMap.entrySet()) {
            Integer i = e.getKey();
            Parameter p = e.getValue();
            // 如果事件的类是参数类的子类
            if (p.getType().isInstance(event)) {
                params[i] = event;
            }
            // 如果参数类是RobotClient
            if (p.getType().isAssignableFrom(RobotClient.class)) {
                params[i] = RobotManager.getInstance().get(event.getSelfId());
            }
        }
        logger.debug("参数注入完成，方法{}的参数列表为{}", context.getMethod().getName(), Arrays.toString(params));
    }

    @Override
    public boolean preFilter(ListenerContext context, Event event, Object... args) {
        // 前置拦截器
        if (globalPreFilter != null && !globalPreFilter.apply(event, context)) {
            logger.debug("监听器被全局前置拦截器：{}拦截", globalPreFilter.getClass());
            return true;
        }
        ArrayList<PreFilter> filters = context.getPreFilters();
        for (PreFilter filter : filters) {
            if (!filter.apply(event, context)) {
                logger.debug("监听器被前置拦截器：{}拦截", filter.getClass());
                return true;
            }
        }
        logger.debug("前置拦截器处理完成，通过{}个前置拦截器处理", filters.size());
        return false;

    }

    @Override
    public Object doHandler(ListenerContext context, Event event, Object... args)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object result = context.getMethod().invoke(context.getInvokeObj(), args);
        context.setResult(result);
        logger.debug("调用{}.{}执行成功", context.getInvokeObj().getClass().getName(), context.getMethod().getName());
        return result;
    }

    @Override
    public boolean postFilter(ListenerContext context, Event event, Object... args) {
        if (globalPostFilter != null && globalPostFilter.apply(event, context, args)) {
            logger.debug("全局后置拦截器：{}拦截成功",globalPostFilter.getClass());
            return true;
        }
        ArrayList<PostFilter> postFilters = context.getPostFilters();
        for (PostFilter filter : postFilters) {
            if (!filter.apply(event, context, args)) {
                logger.debug("后置拦截器：{}拦截成功",filter.getClass());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean resultHandler(ListenerContext context, Channel channel, Object... args) {
        logger.debug("websocket不需要结果处理，已跳过");
        return false;
    }

    @Override
    public void start() throws InterruptedException {
        try {
            uri = new URI("ws://" + host + ":" + port + "/event");
            HttpHeaders httpHeaders = new DefaultHttpHeaders();
            // 进行握手
            this.handshaker = WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null,
                    true, httpHeaders);
            // 需要协议的host和port
            Channel channel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();
            handshaker.handshake(channel);
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

    private class DefaultRejectHttpPipeline extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline p = ch.pipeline();
            p.addLast(new HttpServerCodec());
            p.addLast(new HttpObjectAggregator(1 << 20));
            p.addLast(new DefaultRejectHttpHandler());
        }

    }

    @Sharable
    private class DefaultRejectHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

        @Override
        public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
            DefaultFullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.NO_CONTENT);
            ctx.writeAndFlush(res);
        }
    }
}
