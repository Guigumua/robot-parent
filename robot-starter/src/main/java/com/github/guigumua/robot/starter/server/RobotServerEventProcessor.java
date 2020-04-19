package com.github.guigumua.robot.starter.server;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.starter.client.RobotClient;
import com.github.guigumua.robot.starter.client.RobotManager;
import com.github.guigumua.robot.starter.server.filter.MappingFilter;
import com.github.guigumua.robot.starter.server.filter.PostFilter;
import com.github.guigumua.robot.starter.server.filter.PreFilter;
import com.github.guigumua.robot.starter.server.listener.ListenerContext;
import com.github.guigumua.robot.starter.server.listener.ListenerManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public interface RobotServerEventProcessor {
    /**
     * 解析msg
     *
     * @param msg 读事件发生时的消息内容
     * @return 事件
     */
    Event resolveEvent(Object msg);

    /**
     * 进行默认响应
     */
    void defaultRespond(ChannelHandlerContext ctx);

    /**
     * 通过事件 映射到可以执行的该事件的监听器
     *
     * @param event 事件
     * @return 事件所映射的监听器集合
     */
    default Set<ListenerContext> mapping(Event event, Set<MappingFilter> globalMappingFilters) {
        ListenerManager manager = ListenerManager.getInstance();
        Set<ListenerContext> listenerContexts = manager.getListenerChain(event.getEventType());
        listenerContexts = listenerContexts.stream().filter(listenerContext -> {
            for (MappingFilter filter : globalMappingFilters) {
                if (!filter.apply(event, listenerContext)) {
                    return false;
                }
            }
            ArrayList<MappingFilter> mappingFilters = listenerContext.getMappingFilters();
            for (MappingFilter filter : mappingFilters) {
                if (!filter.apply(event, listenerContext)) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toSet());
        return listenerContexts;
    }

    /**
     * 参数注入
     *
     * @param context 监听器上下文
     * @param event   事件
     */
    default void paramInject(ListenerContext context, Event event) {
        // 参数注入
        Map<Integer, Parameter> paramsMap = context.getParamsMap();
        Object[] params = context.getParams();
        for (Map.Entry<Integer, Parameter> e : paramsMap.entrySet()) {
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
            Field[] fields = event.getClass().getFields();
            // event的参数
            for (Field field : fields) {
                if (field.getName().equals(p.getName()) && field.getType() == p.getType()) {
                    try {
                        params[i] = field.get(event);
                    } catch (IllegalAccessException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * @param context       监听上下文
     * @param event         事件
     * @param globalFilters 全局拦截器
     * @return 是否放行
     */
    default boolean preFilter(ListenerContext context, Event event, Set<PreFilter> globalFilters) {
        for (PreFilter filter : globalFilters) {
            if (!filter.apply(event, context)) {
                return false;
            }
        }
        for (PreFilter filter : context.getPreFilters()) {
            if (!filter.apply(event, context)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 执行监听器
     *
     * @param context 监听器上下文
     * @param event   事件
     */
    default void doHandler(ListenerContext context, Event event)
            throws Exception {
        Object result = context.getMethod().invoke(context.getInvokeObj(), context.getParams());
        context.setResult(result);
    }

    /**
     * 后置拦截器
     *
     * @param context 监听器上下文
     * @param event   事件
     * @return 是否拦截后续逻辑（v0.0.1目前只能拦截@Listener注解中的isBreak）
     */
    default boolean postFilter(ListenerContext context, Event event, Set<PostFilter> globalPostFilters) {
        for (PostFilter filter : globalPostFilters) {
            if (!filter.apply(event, context)) {
                return false;
            }
        }
        for (PostFilter filter : context.getPostFilters()) {
            if (!filter.apply(event, context)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 结果处理器
     *
     * @param context 监听器上下文
     * @param channel 此次事件中读写用的通道
     * @return 是否用通道写回了结果
     */
    default boolean resultHandler(ListenerContext context, Channel channel) {
        return false;
    }
}
