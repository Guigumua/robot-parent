package com.github.guigumua.robot.starter.server.ws;

import com.github.guigumua.robot.starter.server.RobotServerEventProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(RobotServerEventProcessor.class)
@ConditionalOnProperty(value = "robot.server.use-ws", havingValue = "true")
public class DefaultWebSocketServerEventProcessor extends RobotWebSocketServerEventProcessorAdapter {
}
