package com.github.guigumua.robot.starter.configuration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.guigumua.robot.starter.server.properties.RobotServerProperties;
import com.github.guigumua.robot.starter.server.properties.RobotServerProperties.Robot;

/**
 * 配置机器人
 * 
 * @author Administrator
 *
 */
@Configuration
@EnableConfigurationProperties(RobotServerProperties.class)
public class RobotClientAutoConfiguration {
	private Logger logger = LoggerFactory.getLogger(RobotClientAutoConfiguration.class);
	@Autowired
	private RobotServerProperties properties;

	@Bean
	public RobotManager configureManager() {
		RobotManager robotManager = new RobotManager();
		List<Robot> robots = properties.getRobots();
		for (Robot robot : robots) {
			robotManager.registerRobotClient(robot.getId(), robot.getHost(), robot.getPort());
		}
		logger.debug("robot manager 自动注册了{}个 robot client", robotManager.count());
		return robotManager;
	}
}
