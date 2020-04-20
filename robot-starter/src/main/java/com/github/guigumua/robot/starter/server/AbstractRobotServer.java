package com.github.guigumua.robot.starter.server;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.starter.annotation.GlobalFilter;
import com.github.guigumua.robot.starter.server.filter.MappingFilter;
import com.github.guigumua.robot.starter.server.filter.PostFilter;
import com.github.guigumua.robot.starter.server.filter.PreFilter;
import com.github.guigumua.robot.starter.server.listener.ListenerContext;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractRobotServer implements RobotServer {
    protected String host;
    protected int port;

    protected ApplicationContext applicationContext;
    protected Set<MappingFilter> globalMappingFilters = new HashSet<>();
    protected Set<PreFilter> globalPreFilters = new HashSet<>();
    protected Set<PostFilter> globalPostFilters = new HashSet<>();

    protected RobotServerEventProcessor processor;

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    public void handlerEvent(Object msg, ChannelHandlerContext ctx) throws Exception {
        Event event = processor.resolveEvent(msg);
        if (event == null) {
            processor.defaultRespond(ctx);
            return;
        }
        Set<ListenerContext> listenerContexts = processor.mapping(event, globalMappingFilters);
        boolean isResponded = false;
        for (ListenerContext listenerContext : listenerContexts) {
            Object[] params = processor.paramInject(listenerContext, event);
            if (!processor.preFilter(listenerContext, event, params, globalPreFilters)) {
                continue;
            }
            Object result = processor.doHandler(listenerContext, event, params);
            if (processor.postFilter(listenerContext, event, params, globalPostFilters)) {
                continue;
            }
            if (processor.resultHandler(listenerContext, result, ctx.channel())) {
                isResponded = true;
            }
            if (listenerContext.isBreak()) {
                break;
            }
        }
        if (!isResponded) {
            processor.defaultRespond(ctx);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        Map<String, Object> filters = applicationContext.getBeansWithAnnotation(GlobalFilter.class);
        for (Object o : filters.values()) {
            if (o instanceof MappingFilter) {
                globalMappingFilters.add((MappingFilter) o);
            } else if (o instanceof PreFilter) {
                globalPreFilters.add((PreFilter) o);
            } else if (o instanceof PostFilter) {
                globalPostFilters.add((PostFilter) o);
            }
        }
    }
}
