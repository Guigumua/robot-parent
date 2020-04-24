package com.github.guigumua.robot.starter.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.guigumua.robot.starter.server.properties.RobotAutoConfigurationProperties;
import com.github.guigumua.robot.starter.server.properties.RobotAutoConfigurationProperties.RobotClientProperties;
import com.github.guigumua.robot.starter.server.properties.RobotAutoConfigurationProperties.RobotClientProperties.Client;

/**
 * 配置机器人
 * 
 * @author Administrator
 *
 */
@Configuration
@EnableConfigurationProperties(RobotAutoConfigurationProperties.class)
public class RobotClientAutoConfiguration {
	private Logger logger = LoggerFactory.getLogger(RobotClientAutoConfiguration.class);
	@Autowired
	private RobotAutoConfigurationProperties properties;

	@Bean
	public RobotManager robotManager() {
		return RobotManager.getInstance();
	}

	@Bean
	public RobotClient client() {
		RobotManager robotManager = RobotManager.getInstance();
		RobotClientProperties clientProperties = properties.getClient();
		String host = clientProperties.getHost();
		RobotClient globalClient = null;
		if (host != null) {
			int port = clientProperties.getPort();
			globalClient = robotManager.registerRobotClient(host, port, clientProperties.isUseWs());
		}
		List<Client> clients = properties.getClient().getClients();
		boolean useWs = properties.getClient().isUseWs();
		for (Client client : clients) {
			robotManager.registerRobotClient(client.getHost(), client.getPort(), client.isUseWs() || useWs);
		}
		if (globalClient == null) {
			globalClient = RobotManager.getGlobalClient();
		}
		logger.debug("robot manager 自动注册了{}个 robot client", robotManager.count());
		return globalClient;
	}
}
