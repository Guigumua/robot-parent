package com.github.guigumua.robot.common.event.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.common.event.request.RequestEvent;

import java.io.Serializable;

public abstract class MessageEvent implements Event {
    /**
     *
     */
    private static final long serialVersionUID = 8255566531964280847L;
    @JSONField(serialize = false)
    protected long selfId;
    protected long time;
    protected final String postType = "message";
    protected int messageId;
    protected String message;
    protected String rawMessage;
    protected int font;
    protected Sender sender;
    protected long userId;

    public abstract String getMessageType();

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getPostType() {
        return postType;
    }

    public long getSelfId() {
        return selfId;
    }

    public void setSelfId(long selfId) {
        this.selfId = selfId;
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
    }

    public static abstract class EventResponse implements Event.EventResponse {

        /**
         *
         */
        private static final long serialVersionUID = 1481432659424908907L;
        protected boolean isBlock;
        protected String reply;

        @Override
        public boolean isBlock() {
            return isBlock;
        }

        @Override
        public MessageEvent.EventResponse setBlock(boolean isBlock) {
            this.isBlock = isBlock;
            return this;
        }

        public String getReply() {
            return reply;
        }

        public EventResponse setReply(String reply) {
            this.reply = reply;
            return this;
        }
    }
}
