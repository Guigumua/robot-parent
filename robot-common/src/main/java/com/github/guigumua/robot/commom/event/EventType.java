package com.github.guigumua.robot.commom.event;

public enum EventType {
	PRIVATE_MESSAGE("message_private", 1), 
	GROUP_MESSAGE("message_grou", 1 << 1), 
	DISCUSS_MESSAGE("message_discuss", 1 << 2),
	MESSAGE("message", (1 << 3) - 1), 
	GROUP_UPLOAD_NOTICE("notice_group_upload", 1 << 3),
	GROUP_ADMIN_NOTICE("notice_group_admin", 1 << 4),
	GROUP_DECREASE_NOTICE("notice_group_decrease", 1 << 5),
	GROUP_INCREASE_NOTICE("notice_group_increase", 1 << 6), 
	GROUP_BAN_NOTICE("notice_group_ban", 1 << 6),
	FRIEND_ADD_NOTICE("notice_friend_add", 1 << 7), 
	NOTICE("notice", (1 << 8) - 1 ^ (1 << 3) - 1),
	FRIEND_REQUSET("request_friend", 1 << 8), 
	GROUP_REQUEST("request_group", 1 << 9),
	REQUEST("notice", (1 << 10) - 1 ^ (1 << 8) - 1 ^ (1 << 3) - 1),
	ALL("event", (1 << 10) - 1),
	;
	private String type;
	private int code;

	public String getType() {
		return type;
	}

	public int getCode() {
		return code;
	}

	private EventType(String type, int code) {
		this.type = type;
		this.code = code;
	}

	public boolean hasType(EventType e) {
		return (this.getCode() & e.getCode()) == e.getCode();
	}

	public static boolean isListenMsg(int code) {
		return (GROUP_MESSAGE.getCode() & code) == GROUP_MESSAGE.getCode()
				|| (PRIVATE_MESSAGE.getCode() & code) == PRIVATE_MESSAGE.getCode()
				|| (DISCUSS_MESSAGE.getCode() & code) == DISCUSS_MESSAGE.getCode();
	}
}
