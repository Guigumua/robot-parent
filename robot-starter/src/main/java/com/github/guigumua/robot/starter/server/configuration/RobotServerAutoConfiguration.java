package com.github.guigumua.robot.starter.server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.guigumua.robot.starter.server.listener.ListenerManager;
import com.github.guigumua.robot.starter.server.properties.RobotAutoConfigurationProperties;
import com.github.guigumua.robot.starter.server.properties.RobotAutoConfigurationProperties.RobotServerProperties;

@Configuration
@EnableConfigurationProperties(RobotAutoConfigurationProperties.class)
public class RobotServerAutoConfiguration implements ApplicationRunner {
//	private static final Logger logger = LoggerFactory.getLogger(RobotServerAutoConfiguration.class);

	@Bean
	public ListenerManager configureHandlerManager() {
		return ListenerManager.getInstance();
	}

	@Autowired
	private RobotAutoConfigurationProperties properties;
	@Autowired
	private ApplicationContext context;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		RobotServerProperties serverProperties = properties.getServer();
		RobotServer server;
		if (serverProperties.isUseWs()) {
			RobotWebSocketServer webSocketServer = new RobotWebSocketServer(serverProperties.getHost(),
					serverProperties.getPort());
			if (serverProperties.isUseDefaultHttpReject()) {
				webSocketServer.setUseDefaultReject(true);
				webSocketServer.setHttpHost(serverProperties.getDefaultHttpRejectHost());
				webSocketServer.setHttpPort(serverProperties.getDefaultHttpRejectPort());
			}
			server = webSocketServer;
		} else {
			server = new RobotHttpServer(serverProperties.getHost(), serverProperties.getPort());
		}
		server.setApplicationContext(context);
		server.start();
	}

}
