package com.github.guigumua.robot.starter.configuration;

import java.util.List;

import com.github.guigumua.robot.starter.client.RobotManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.guigumua.robot.starter.server.properties.RobotAutoConfigurationProperties;
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
	public void configureManager() {
		RobotManager robotManager = RobotManager.getInstance();
		List<Client> clients = properties.getClient().getClients();
		boolean useWs = properties.getClient().isUseWs();
		for (Client client : clients) {
			robotManager.registerRobotClient(client.getHost(), client.getPort(), client.isUseWs() || useWs);
		}
		logger.debug("robot manager 自动注册了{}个 robot client", robotManager.count());
	}
}
