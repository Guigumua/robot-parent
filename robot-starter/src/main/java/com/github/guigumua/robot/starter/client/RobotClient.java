package com.github.guigumua.robot.starter.client;

import com.github.guigumua.robot.starter.client.api.CQHttpApi;

public interface RobotClient extends CQHttpApi {

	boolean isUseWs();

	String getHost();

	int getPort();

	boolean isAsync();

	void setAsync(boolean async);

	long getSelfId();

}
