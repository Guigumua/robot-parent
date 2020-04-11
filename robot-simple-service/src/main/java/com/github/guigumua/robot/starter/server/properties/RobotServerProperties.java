package com.github.guigumua.robot.starter.server.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("robot.service")
public class RobotServerProperties {
	private List<Robot> robots = new ArrayList<>();
	private String host = "0.0.0.0";
	private int port = 80;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public List<Robot> getRobots() {
		return robots;
	}

	public void setRobots(List<Robot> robots) {
		this.robots = robots;
	}

	public static class Robot {
		private long id;
		private String host = "127.0.0.1";
		private int port = 5700;
		private String secret;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public String getSecret() {
			return secret;
		}

		public void setSecret(String secret) {
			this.secret = secret;
		}

	}
}
