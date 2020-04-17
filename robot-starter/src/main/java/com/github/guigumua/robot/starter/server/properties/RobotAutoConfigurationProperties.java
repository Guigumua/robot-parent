package com.github.guigumua.robot.starter.server.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("robot")
public class RobotAutoConfigurationProperties {
	private boolean useWs;
	private RobotServerProperties server;
	private RobotClientProperties client;

	public boolean isUseWs() {
		return useWs;
	}

	public void setUseWs(boolean useWs) {
		this.useWs = useWs;
	}

	public RobotServerProperties getServer() {
		return server;
	}

	public void setServer(RobotServerProperties server) {
		this.server = server;
	}

	public RobotClientProperties getClient() {
		return client;
	}

	public void setClient(RobotClientProperties client) {
		this.client = client;
	}

	public static class RobotServerProperties {
		private String host = "0.0.0.0";
		private int port;
		private boolean useWs;
		private boolean useDefaultHttpReject = true;
		private String defaultHttpRejectHost = "0.0.0.0";
		private int defaultHttpRejectPort = 80;

		public boolean isUseDefaultHttpReject() {
			return useDefaultHttpReject;
		}

		public void setUseDefaultHttpReject(boolean useDefaultHttpReject) {
			this.useDefaultHttpReject = useDefaultHttpReject;
		}

		public String getDefaultHttpRejectHost() {
			return defaultHttpRejectHost;
		}

		public void setDefaultHttpRejectHost(String defaultHttpRejectHost) {
			this.defaultHttpRejectHost = defaultHttpRejectHost;
		}

		public int getDefaultHttpRejectPort() {
			return defaultHttpRejectPort;
		}

		public void setDefaultHttpRejectPort(int defaultHttpRejectPort) {
			this.defaultHttpRejectPort = defaultHttpRejectPort;
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

		public boolean isUseWs() {
			return useWs;
		}

		public void setUseWs(boolean useWs) {
			this.useWs = useWs;
		}

	}

	public static class RobotClientProperties {
		private List<Client> clients = new ArrayList<>();
		private boolean useWs;

		public boolean isUseWs() {
			return useWs;
		}

		public void setUseWs(boolean useWs) {
			this.useWs = useWs;
		}

		public List<Client> getClients() {
			return clients;
		}

		public void setClients(List<Client> clients) {
			this.clients = clients;
		}

		public static class Client {
			private long id;
			private String host = "127.0.0.1";
			private int port = 5700;
			private String secret;
			private boolean useWs;

			public boolean isUseWs() {
				return useWs;
			}

			public void setUseWs(boolean useWs) {
				this.useWs = useWs;
			}

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

}
