package com.github.guigumua.robot.starter.test.listener;

import org.springframework.stereotype.Component;

import com.github.guigumua.robot.common.event.EventType;
import com.github.guigumua.robot.common.event.message.MessageEvent;
import com.github.guigumua.robot.common.event.message.PrivateMessageEvent;
import com.github.guigumua.robot.common.util.HttpRequest;
import com.github.guigumua.robot.common.util.JsonUtil;
import com.github.guigumua.robot.starter.annotation.Filter;
import com.github.guigumua.robot.starter.annotation.Listener;
import com.github.guigumua.robot.starter.client.RobotClient;

@Component
public class HelloListener {
	@Listener(value = EventType.PRIVATE_MESSAGE)
	@Filter(value = "^hello.*")
	public void hello(PrivateMessageEvent event, RobotClient client) {
		client.sendPrivateMsg(event.getUserId(), event.getMessage());
	}

	@Listener(EventType.MESSAGE)
	@Filter(regex = "^舔狗日记$")
	public void tg(MessageEvent e, RobotClient client) {
		String body = HttpRequest.get("http://api.yyhy.me/tg.php?type=api").body("UTF-8");
		Object value = JsonUtil.getValueFromJson(body, "content");
		client.sendMsg(e, value.toString());
	}
}