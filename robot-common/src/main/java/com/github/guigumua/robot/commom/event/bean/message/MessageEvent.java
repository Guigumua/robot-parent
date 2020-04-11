package com.github.guigumua.robot.commom.event.bean.message;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.commom.event.Event;
import com.github.guigumua.robot.commom.event.EventType;
import com.github.guigumua.robot.commom.util.JsonUtil;

public class MessageEvent implements Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = 912212471724045580L;
	protected long time;
	protected String postType;
	protected String messageType;
	protected long selfId;
	protected int messageId;
	protected String message;
	protected String rawMessage;
	protected int font;
	protected Sender sender;
	protected long userId;
	@JSONField(serialize = false, deserialize = false)
	private EventType eventType = EventType.MESSAGE;

	@Override
	public EventType getEventType() {
		return this.eventType;
	}

	@JSONField(serialize = false, deserialize = false)
	protected MsgEventResponse reponse = new MsgEventResponse();

	@Override
	public MsgEventResponse getResponse() {
		return this.reponse;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public long getSelfId() {
		return selfId;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRawMessage() {
		return rawMessage;
	}

	public void setRawMessage(String rawMessage) {
		this.rawMessage = rawMessage;
	}

	public int getFont() {
		return font;
	}

	public void setFont(int font) {
		this.font = font;
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public static class Sender implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3190245875695642272L;
		private long userId;
		private String nickname;
		private String card;
		private String sex;
		private int age;
		private String area;
		private String level;
		private String role;
		private String title;

		public long getUserId() {
			return userId;
		}

		public void setUserId(long userId) {
			this.userId = userId;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getCard() {
			return card;
		}

		public void setCard(String card) {
			this.card = card;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		@Override
		public String toString() {
			return JsonUtil.toJSON(this);
		}
	}

	public static class MsgEventResponse implements EventResponse {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7353214620653989953L;
		private boolean block;
		private String reply;
		private boolean autoEscape;

		@Override
		public void setBlock(boolean block) {
			this.block = block;
		}

		@Override
		public boolean isBlock() {
			return this.block;
		}

		public String getReply() {
			return reply;
		}

		public void setReply(String reply) {
			this.reply = reply;
		}

		public boolean isAutoEscape() {
			return autoEscape;
		}

		public void setAutoEscape(boolean autoEscape) {
			this.autoEscape = autoEscape;
		}

	}

}
