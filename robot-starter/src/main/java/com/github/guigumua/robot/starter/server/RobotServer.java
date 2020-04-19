package com.github.guigumua.robot.starter.server;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.context.ApplicationContextAware;

public interface RobotServer extends ApplicationContextAware {

    String getHost();

    int getPort();

    /**
     * 启动服务器
     *
     * @throws InterruptedException
     */
    void start() throws InterruptedException;

    /**
     * 处理事件
     *
     * @param msg 事件
     * @param ctx netty的channel handler上下文
     */
    void handlerEvent(Object msg, ChannelHandlerContext ctx) throws Exception;

}
