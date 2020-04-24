package com.github.guigumua.robot.starter.test.listener;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.github.guigumua.robot.common.event.EventType;
import com.github.guigumua.robot.common.event.message.GroupMessageEvent;
import com.github.guigumua.robot.common.event.message.MessageEvent;
import com.github.guigumua.robot.common.event.notice.GroupDecreaseNoticeEvent;
import com.github.guigumua.robot.common.event.notice.GroupIncreaseNoticeEvent;
import com.github.guigumua.robot.common.event.request.GroupAddInviteRequestEvent;
import com.github.guigumua.robot.common.request.GetStrangerInfoRequest.ResponseData;
import com.github.guigumua.robot.common.util.CQCode;
import com.github.guigumua.robot.starter.annotation.Filter;
import com.github.guigumua.robot.starter.annotation.Listener;
import com.github.guigumua.robot.starter.client.RobotClient;

@Component
public class AdminListener implements ApplicationRunner {
	@Autowired
	private RobotClient globalClient;

	@Listener(EventType.GROUP_REQUEST)
	public void groupRequest(GroupAddInviteRequestEvent event, RobotClient client) {
		String comment = event.getComment();
		client.setGroupAddRequest(event, true, "");
		String s = CQCode.appender().append("欢迎新人加入！\n").append(CQCode.getAt(event.getUserId())).append("你是从")
				.append(comment).append("来的呢").buildStr();
		client.sendGroupMsg(event.getGroupId(), s);
	}

	@Listener(EventType.GROUP_MESSAGE)
	@Filter("自闭")
	public void zibi(GroupMessageEvent event, RobotClient client) {
		MessageEvent.Sender sender = event.getSender();
		String role = sender.getRole();
		if ("member".equals(role)) {
			client.setGroupBan(event.getGroupId(), event.getUserId(), RandomUtils.nextInt(1, 6) * 60);
		} else if ("owner".equals(role) || "admin".equals(role)) {
			client.sendGroupMsg(event.getGroupId(), CQCode.getAt(event.getUserId()).toString() + "我无法给你想要的自闭呢");
		}
	}

	@Listener(EventType.GROUP_INCREASE_NOTICE)
	public void test(GroupIncreaseNoticeEvent event, RobotClient client) {
		long userId = event.getUserId();
		ResponseData strangerInfo = client.getStrangerInfo(userId);
		String nickname = strangerInfo.getNickname();
		client.sendGroupMsg(event.getGroupId(), nickname);
	}

	@Listener(EventType.GROUP_DECREASE_NOTICE)
	public void test(GroupDecreaseNoticeEvent event, RobotClient client) {
		client.sendGroupMsg(event.getGroupId(), "群成员减少");
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		globalClient.sendGroupAndDelete(794096539L, "hello world!", 30, TimeUnit.SECONDS);
	}
}
