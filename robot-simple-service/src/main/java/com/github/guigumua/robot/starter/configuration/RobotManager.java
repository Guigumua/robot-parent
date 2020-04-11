package com.github.guigumua.robot.starter.configuration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.guigumua.robot.commom.request.bean.GetLoginInfoRequest;
import com.github.guigumua.robot.commom.request.bean._GetVipInfoRequest;
import com.github.guigumua.robot.common.client.RobotClient;

public class RobotManager {
	private static final Logger logger = LoggerFactory.getLogger(RobotManager.class);
	private static final Map<Long, RobotClient> CLIENTS = new HashMap<>();

	public RobotClient remove(long id) {
		return CLIENTS.remove(id);
	}

	public RobotClient get(long id) {
		return CLIENTS.get(id);
	}

	public Collection<RobotClient> clients() {
		return CLIENTS.values();
	}

	public void registerRobotClient(long id, String host, int port) {
		// 占位
		CLIENTS.put(id, RobotClient.PLACEHOLDER_CLIENT);
		RobotClient client = new RobotClient(host, port);
		GetLoginInfoRequest.ResponseData info = client.getLoginInfo();
		long userId = info.getUserId();
		String nickname = info.getNickname();
		_GetVipInfoRequest.ResponseData vipInfo = client._getVipInfo(userId);
		int level = vipInfo.getLevel();
		logger.info("机器人注册成功");
		logger.info("账号：{},等级：{},昵称：{}", userId, level, nickname);
		// 实际插入的client
		CLIENTS.put(userId, client);
	}

	public int count() {
		return CLIENTS.size();
	}
}
