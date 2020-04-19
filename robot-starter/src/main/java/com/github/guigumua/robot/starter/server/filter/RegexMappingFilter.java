package com.github.guigumua.robot.starter.server.filter;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.common.event.message.MessageEvent;
import com.github.guigumua.robot.starter.annotation.GlobalFilter;
import com.github.guigumua.robot.starter.server.listener.ListenerContext;

@GlobalFilter
public class RegexMappingFilter implements MappingFilter{
    @Override
    public boolean apply(Event event, ListenerContext context, Object... args) {
        if(event instanceof MessageEvent){
            MessageEvent e = (MessageEvent) event;
            String message = e.getMessage();
            for (String regex : context.getRegex()) {
                if (!message.matches(regex)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int sort() {
        return Integer.MIN_VALUE;
    }
}
