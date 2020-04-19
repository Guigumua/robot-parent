package com.github.guigumua.robot.starter.server.configuration;

import com.github.guigumua.robot.starter.server.RobotServer;
import com.github.guigumua.robot.starter.server.RobotServerEventProcessor;
import com.github.guigumua.robot.starter.server.filter.RegexMappingFilter;
import com.github.guigumua.robot.starter.server.http.RobotHttpServer;
import com.github.guigumua.robot.starter.server.ws.RobotWebSocketServer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.guigumua.robot.starter.server.listener.ListenerManager;
import com.github.guigumua.robot.starter.server.properties.RobotAutoConfigurationProperties;
import com.github.guigumua.robot.starter.server.properties.RobotAutoConfigurationProperties.RobotServerProperties;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(RobotAutoConfigurationProperties.class)
@Import(RegexMappingFilter.class)
public class RobotServerAutoConfiguration {

    private final RobotAutoConfigurationProperties properties;

    public RobotServerAutoConfiguration(RobotAutoConfigurationProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ListenerManager configureListenerManager() {
        return ListenerManager.getInstance();
    }

    @Bean
    public RobotServer configureRobotServer(RobotServerEventProcessor processor) {
        RobotServerProperties serverProperties = properties.getServer();
        RobotServer server;
        if (serverProperties.isUseWs()) {
            RobotWebSocketServer webSocketServer = new RobotWebSocketServer(serverProperties.getHost(),
                    serverProperties.getPort(), processor);
            if (serverProperties.isUseDefaultHttpReject()) {
                webSocketServer.setUseDefaultReject(true);
                webSocketServer.setHttpHost(serverProperties.getDefaultHttpRejectHost());
                webSocketServer.setHttpPort(serverProperties.getDefaultHttpRejectPort());
            }
            server = webSocketServer;
        } else {
            server = new RobotHttpServer(serverProperties.getHost(), serverProperties.getPort(), processor);
        }
        return server;
    }

}
