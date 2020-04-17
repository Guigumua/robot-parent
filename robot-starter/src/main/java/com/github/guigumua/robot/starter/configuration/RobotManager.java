package com.github.guigumua.robot.starter.configuration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.guigumua.robot.common.request.GetLoginInfoRequest;
import com.github.guigumua.robot.common.request._GetVipInfoRequest;
import com.github.guigumua.robot.starter.client.RobotClient;
import com.github.guigumua.robot.starter.client.http.RobotHttpClient;
import com.github.guigumua.robot.starter.client.ws.RobotWebSocketClient;

public class RobotManager {
	private static final RobotManager instance = new RobotManager();
	private static final Logger logger = LoggerFactory.getLogger(RobotManager.class);
	private static final Map<Long, RobotClient> CLIENTS = new HashMap<>();

	private RobotManager() {
	}

	public RobotClient remove(long id) {
		return CLIENTS.remove(id);
	}

	public RobotClient get(long id) {
		return CLIENTS.get(id);
	}

	public Collection<RobotClient> clients() {
		return CLIENTS.values();
	}

	public static RobotManager getInstance() {
		return instance;
	}

	public RobotClient registerRobotClient(String host, int port, boolean useWs) {
		RobotClient client;
		if (useWs) {
			client = new RobotWebSocketClient(host, port);
		} else {
			client = new RobotHttpClient(host, port);
		}
		try {
			GetLoginInfoRequest.ResponseData info = client.getLoginInfo();
			long userId = info.getUserId();
			String nickname = info.getNickname();
			_GetVipInfoRequest.ResponseData vipInfo = client._getVipInfo(userId);
			int level = vipInfo.getLevel();
			logger.info("机器人注册成功");
			logger.info("账号：{},等级：{},昵称：{}", userId, level, nickname);
			CLIENTS.put(userId, client);
		} catch (Exception e) {
			logger.warn("机器人注册失败！{}", e.getMessage());
		}
		return client;
	}

	public int count() {
		return CLIENTS.size();
	}
}
