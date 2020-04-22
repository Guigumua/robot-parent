package com.github.guigumua.robot.starter.annotation;

import java.util.function.BiPredicate;
import java.util.regex.Pattern;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.common.event.message.GroupMessageEvent;
import com.github.guigumua.robot.common.event.message.MessageEvent;
import com.github.guigumua.robot.common.event.notice.GroupAdminNoticeEvent;
import com.github.guigumua.robot.common.event.notice.GroupBanNoticeEvent;
import com.github.guigumua.robot.common.event.notice.GroupDecreaseNoticeEvent;
import com.github.guigumua.robot.common.event.notice.GroupIncreaseNoticeEvent;
import com.github.guigumua.robot.common.event.notice.GroupUploadNoticeEvent;
import com.github.guigumua.robot.common.event.request.GroupAddInviteRequestEvent;
import com.github.guigumua.robot.common.util.CQCode;
import com.github.guigumua.robot.starter.server.listener.ListenerContext;

public enum MatchType {
	/**
	 * 有任何一个正则匹配
	 */
	ANY_REGEX((context, event) -> {
		if (event instanceof MessageEvent) {
			// 如果是聊天消息类型，则获取消息内容进行匹配
			MessageEvent e = (MessageEvent) event;
			String msg = e.getMessage();
			for (Pattern pattern : context.getPattern()) {
				if (pattern.matcher(msg).find()) {
					// 当有正则能够匹配消息则返回true
					return true;
				}
			}
			// 如果所有正则都不能匹配消息，则返回false
			return false;
		}
		// 不是消息类型，返回true
		return true;
	}),
	/**
	 * 没有任何一个正则匹配
	 */
	NONE_REGEX((context, event) -> {
		if (event instanceof MessageEvent) {
			MessageEvent e = (MessageEvent) event;
			String msg = e.getMessage();
			for (Pattern pattern : context.getPattern()) {
				if (pattern.matcher(msg).find()) {
					return false;
				}
			}
		}
		return true;
	}),
	/**
	 * 必须匹配所有正则
	 */
	ALL_REGEX((context, event) -> {
		if (event instanceof MessageEvent) {
			MessageEvent e = (MessageEvent) event;
			String msg = e.getMessage();
			for (Pattern pattern : context.getPattern()) {
				if (!pattern.matcher(msg).find()) {
					return false;
				}
			}
		}
		return true;
	}),
	/**
	 * 包含qq号码
	 */
	INCLUDE_QQ((context, event) -> {
		long userId = event.getUserId();
		for (long qq : context.getQq()) {
			if (qq == userId) {
				return true;
			}
		}
		return false;
	}),
	/**
	 * 不包含qq
	 */
	EXCLUDE_QQ((context, event) -> {
		long userId = event.getUserId();
		for (long qq : context.getQq()) {
			if (qq == userId) {
				return false;
			}
		}
		return true;
	}),
	/**
	 * 包含群
	 */
	INCLUDE_GROUP((context, event) -> {
		switch (event.getEventType()) {
		case GROUP_MESSAGE: {
			GroupMessageEvent e = (GroupMessageEvent) event;
			return includeGroup(e.getGroupId(), context);
		}
		case GROUP_REQUEST: {
			GroupAddInviteRequestEvent e = (GroupAddInviteRequestEvent) event;
			return includeGroup(e.getGroupId(), context);
		}
		case GROUP_BAN_NOTICE: {
			GroupBanNoticeEvent e = (GroupBanNoticeEvent) event;
			return includeGroup(e.getGroupId(), context);
		}
		case GROUP_ADMIN_NOTICE: {
			GroupAdminNoticeEvent e = (GroupAdminNoticeEvent) event;
			return includeGroup(e.getGroupId(), context);
		}
		case GROUP_DECREASE_NOTICE: {
			GroupDecreaseNoticeEvent e = (GroupDecreaseNoticeEvent) event;
			return includeGroup(e.getGroupId(), context);
		}
		case GROUP_UPLOAD_NOTICE: {
			GroupUploadNoticeEvent e = (GroupUploadNoticeEvent) event;
			return includeGroup(e.getGroupId(), context);
		}
		case GROUP_INCREASE_NOTICE: {
			GroupIncreaseNoticeEvent e = (GroupIncreaseNoticeEvent) event;
			return includeGroup(e.getGroupId(), context);
		}
		default:
			break;
		}
		return false;
	}),
	/**
	 * 不包含群。 如果是群事件，任何群事件的groupId匹配到在@Filter注解中设置的group，则返回false
	 */
	EXCLUDE_GROUP((context, event) -> {
		switch (event.getEventType()) {
		case GROUP_MESSAGE: {
			GroupMessageEvent e = (GroupMessageEvent) event;
			return excludeGroup(e.getGroupId(), context);
		}
		case GROUP_REQUEST: {
			GroupAddInviteRequestEvent e = (GroupAddInviteRequestEvent) event;
			return excludeGroup(e.getGroupId(), context);
		}
		case GROUP_BAN_NOTICE: {
			GroupBanNoticeEvent e = (GroupBanNoticeEvent) event;
			return excludeGroup(e.getGroupId(), context);
		}
		case GROUP_ADMIN_NOTICE: {
			GroupAdminNoticeEvent e = (GroupAdminNoticeEvent) event;
			return excludeGroup(e.getGroupId(), context);
		}
		case GROUP_DECREASE_NOTICE: {
			GroupDecreaseNoticeEvent e = (GroupDecreaseNoticeEvent) event;
			return excludeGroup(e.getGroupId(), context);
		}
		case GROUP_UPLOAD_NOTICE: {
			GroupUploadNoticeEvent e = (GroupUploadNoticeEvent) event;
			return excludeGroup(e.getGroupId(), context);
		}
		case GROUP_INCREASE_NOTICE: {
			GroupIncreaseNoticeEvent e = (GroupIncreaseNoticeEvent) event;
			return excludeGroup(e.getGroupId(), context);
		}
		default:
			break;
		}
		return true;
	}),
	/**
	 * 包含at
	 */
	CONTAIN_AT((context, event) -> {
		if (event instanceof MessageEvent) {
			MessageEvent e = (MessageEvent) event;
			String msg = e.getMessage();
			if (msg.contains("[CQ:at,qq=")) {
				return true;
			}
		}
		return false;
	}),
	/**
	 * 包含at自己
	 */
	CONTAIN_AT_SELF((context, event) -> {
		if (event instanceof MessageEvent) {
			MessageEvent e = (MessageEvent) event;
			String msg = e.getMessage();
			if (msg.contains("[CQ:at,qq=" + e.getSelfId() + "]")) {
				return true;
			}
		}
		return false;
	}),
	/**
	 * 包含@qq
	 */
	CONTAIN_AT_QQ((context, event) -> {
		if (event instanceof MessageEvent) {
			MessageEvent e = (MessageEvent) event;
			String msg = e.getMessage();
			for (long qq : context.getQq()) {
				if (msg.contains("[CQ:at,qq=" + qq + "]")) {
					return true;
				}
			}
		}
		return false;
	}),
	/**
	 * 除去cq后任何正则匹配到
	 */
	ANY_REGEX_EXCLUDE_CQ((context, event) -> {
		if (event instanceof MessageEvent) {
			MessageEvent e = (MessageEvent) event;
			String msg = e.getMessage();
			String excludeCQMsg = CQCode.removeAllCQ(msg);
			for (Pattern pattern : context.getPattern()) {
				if (pattern.matcher(excludeCQMsg).find()) {
					return true;
				}
			}
		}
		return false;
	}),
	/**
	 * 
	 */
	ALL_REGEX_EXCLUDE_CQ((context, event) -> {
		if (event instanceof MessageEvent) {
			MessageEvent e = (MessageEvent) event;
			String msg = e.getMessage();
			String excludeCQMsg = CQCode.removeAllCQ(msg);
			for (Pattern pattern : context.getPattern()) {
				if (!pattern.matcher(excludeCQMsg).find()) {
					return false;
				}
			}
		}
		return true;
	}),
	/**
	 * 
	 */
	NONE_REGEX_EXCLUDE_CQ((context, event) -> {
		if (event instanceof MessageEvent) {
			MessageEvent e = (MessageEvent) event;
			String msg = e.getMessage();
			String excludeCQMsg = CQCode.removeAllCQ(msg);
			for (Pattern pattern : context.getPattern()) {
				if (pattern.matcher(excludeCQMsg).find()) {
					return false;
				}
			}
		}
		return true;
	}),
	/**
	 * 
	 */
	ONLY_IMAGE((context, event) -> {
		if (event instanceof MessageEvent) {
			MessageEvent e = (MessageEvent) event;
			String msg = e.getMessage();
			return CQCode.onlyImage(msg);
		}
		return true;
	});

	private BiPredicate<ListenerContext, Event> predicate;

	private MatchType(BiPredicate<ListenerContext, Event> predicate) {
		this.predicate = predicate;
	}

	private static boolean includeGroup(long groupId, ListenerContext context) {
		for (long group : context.getGroup()) {
			if (group == groupId) {
				return true;
			}
		}
		return false;
	}

	private static boolean excludeGroup(long groupId, ListenerContext context) {
		for (long group : context.getGroup()) {
			if (group == groupId) {
				return false;
			}
		}
		return true;
	}

	public boolean matches(ListenerContext context, Event event) {
		return this.predicate.test(context, event);
	}

	public static enum MatchTypeModel {
		/**
		 * 
		 */
		ANY(0),
		/**
		 * 
		 */
		NONE(-1),
		/**
		 * 
		 */
		ALL(1),;

		private final int model;

		private MatchTypeModel(int model) {
			this.model = model;
		}

		public int getModel() {
			return model;
		}

	}
}
