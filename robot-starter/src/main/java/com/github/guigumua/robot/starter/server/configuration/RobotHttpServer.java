package com.github.guigumua.robot.starter.server.configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.net.InetSocketAddress;
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

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class RobotHttpServer implements RobotServer {
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

        private final ChannelHandler handler = new RobotHttpServerHandler();

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline p = ch.pipeline();
//			p.addLast(new LoggingHandler(LogLevel.INFO));
            p.addLast(new HttpServerCodec());
            p.addLast(new HttpObjectAggregator(1 << 22));
            p.addLast(handler);
        }

    }

    @Sharable
    private class RobotHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

        private final DefaultFullHttpResponse DEFAULT_RESPONSE = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.NO_CONTENT);

        @Override
        public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
            Event event = resolveMsg(msg);
            if (event == null) {
                ctx.writeAndFlush(DEFAULT_RESPONSE.retain());
                return;
            }
            registerClientByEvent(ctx.channel(), event);
            Set<ListenerContext> contexts = mappingFilter(event);
            boolean isResponsed = false; // 是否直接响应过
            for (ListenerContext context : contexts) {
                paramInject(context, event);
                Object[] params = context.getParams();
                if (preFilter(context, event, params)) {
                    continue;
                }
                // 执行
                doHandler(context, event, params);
                // 直接响应，只会执行第一个直接响应
                if (!isResponsed) {
                    isResponsed = resultHandler(context, ctx.channel());
                }
                logger.debug("调用{}.{}执行成功", context.getClass().getName(), context.getMethod().getName());
                if (postFilter(context, event, params)) {
                    continue;
                }
                // 执行完成后是否跳过其他监听器的执行
                if (context.isBreak()) {
                    break;
                }
            }
            if (!isResponsed) {
                ctx.writeAndFlush(DEFAULT_RESPONSE.retain());
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
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
        FullHttpRequest request = (FullHttpRequest) msg;
        String idStr = request.headers().get("X-Self-ID");
        if (idStr != null) {
            String json = request.content().toString(CharsetUtil.UTF_8);
            Event event = JsonUtil.getEvent(json);
            if (event != null) {
                event.setSelfId(Long.parseLong(idStr));
            }
            logger.debug("事件解析完成：{}", json);
            return event;
        } else {
            return null;
        }
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
                logger.debug("映射拦截器处理完成，通过{}个映射拦截器处理", mappingFilters.size());
                return message.matches(regex);
            }).collect(Collectors.toSet());
        }
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
    public boolean preFilter(ListenerContext context, Event event, Object... params) {
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
        if (globalPostFilter != null && globalPostFilter.apply(event, context, args)){
            logger.debug("全局后置拦截器：{}拦截成功",globalPostFilter.getClass());
            return true;
        }
        ArrayList<PostFilter> postFilters = context.getPostFilters();
        for (PostFilter filter : postFilters) {
            if (!filter.apply(event, context, args)) {
                logger.debug("后置拦截器：{}拦截成功", filter.getClass());
                return true;
            }
        }
        logger.debug("后置拦截器处理完成，通过{}个后置拦截器处理", postFilters.size());
        return false;
    }

    @Override
    public boolean resultHandler(ListenerContext context, Channel channel, Object... args) {
        Object result = context.getResult();
        if (result instanceof Event.EventResponse) {
            DefaultFullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            res.content().writeBytes(JsonUtil.toJSON(result).getBytes(CharsetUtil.UTF_8));
            res.headers().add(HttpHeaderNames.CONTENT_LENGTH, res.content().readableBytes());
            channel.writeAndFlush(res);
            logger.debug("结果处理完成，已直接响应事件");
            return true;
        }
        logger.debug("结果处理完成，没有直接响应事件，将默认回复空响应");
        return false;
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
}
