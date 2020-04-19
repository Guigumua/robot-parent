package com.github.guigumua.robot.starter.server.http;

import com.github.guigumua.robot.starter.server.RobotServerEventProcessor;
import com.github.guigumua.robot.starter.server.ws.DefaultWebSocketServerEventProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(DefaultWebSocketServerEventProcessor.class)
@ConditionalOnMissingBean(RobotServerEventProcessor.class)
@ConditionalOnProperty(value = "robot.server.use-ws",havingValue = "false",matchIfMissing = true)
public class DefaultRobotHttpServerEventProcessor extends RobotHttpServerEventProcessorAdapter {
}
