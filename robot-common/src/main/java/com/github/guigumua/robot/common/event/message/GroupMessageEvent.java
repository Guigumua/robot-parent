package com.github.guigumua.robot.common.event.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.guigumua.robot.common.event.EventType;

import java.io.Serializable;

public class GroupMessageEvent extends MessageEvent {
    /**
     *
     */
    private static final long serialVersionUID = 3674426525546659733L;
    private final String messageType = "group";
    private String subType;
    private long groupId;
    private Anonymous anonymous;

    @JSONField(serialize = false)
    @Override
    public EventType getEventType() {
        return EventType.GROUP_MESSAGE;
    }

    @Override
    public String getMessageType() {
        return messageType;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public Anonymous getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Anonymous anonymous) {
        this.anonymous = anonymous;
    }

    public static class Anonymous implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = -5189548584889177192L;
        private long id;
        private String name;
        private String flag;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }

    @JSONField(serialize = false)
    private EventResponse response = new EventResponse();

    public EventResponse getResponse() {
        return response;
    }

    public static class EventResponse extends MessageEvent.EventResponse {
        /**
         *
         */
        private static final long serialVersionUID = -150416743303743437L;
        private boolean atSender;
        private boolean delete;
        private boolean kick;
        private boolean ban;
        private int banDuration;

        public boolean isAtSender() {
            return atSender;
        }

        public EventResponse setAtSender(boolean atSender) {
            this.atSender = atSender;
            return this;
        }

        public boolean isDelete() {
            return delete;
        }

        public EventResponse setDelete(boolean delete) {
            this.delete = delete;
            return this;
        }

        public boolean isKick() {
            return kick;
        }

        public EventResponse setKick(boolean kick) {
            this.kick = kick;
            return this;
        }

        public boolean isBan() {
            return ban;
        }

        public EventResponse setBan(boolean ban) {
            this.ban = ban;
            return this;
        }

        public int getBanDuration() {
            return banDuration;
        }

        public EventResponse setBanDuration(int banDuration) {
            this.banDuration = banDuration;
            return this;
        }
    }

}
