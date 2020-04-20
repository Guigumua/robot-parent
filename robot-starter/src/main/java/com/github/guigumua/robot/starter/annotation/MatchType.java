package com.github.guigumua.robot.starter.annotation;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.common.event.message.GroupMessageEvent;
import com.github.guigumua.robot.common.event.message.MessageEvent;
import com.github.guigumua.robot.common.event.notice.*;
import com.github.guigumua.robot.common.event.request.GroupAddInviteRequestEvent;
import com.github.guigumua.robot.starter.server.listener.ListenerContext;

import java.util.function.BiPredicate;

public enum MatchType {
    /**
     * 有任何一个正则匹配
     */
    ANY_REGEX((context, event) -> {
        if (event instanceof MessageEvent) {
            for (String regex : context.getRegex()) {
                MessageEvent e = (MessageEvent) event;
                String msg = e.getMessage();
                if (msg.matches(regex)) {
                    return true;
                }
            }
        }
        return false;
    }),
    /**
     * 没有任何一个正则匹配
     */
    NONE_REGEX((context, event) -> {
        if (event instanceof MessageEvent) {
            for (String regex : context.getRegex()) {
                MessageEvent e = (MessageEvent) event;
                String msg = e.getMessage();
                if (msg.matches(regex)) {
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
            for (String regex : context.getRegex()) {
                MessageEvent e = (MessageEvent) event;
                String msg = e.getMessage();
                if (!msg.matches(regex)) {
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
                return true;
            }
        }
        return false;
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
        }
        return false;
    }),
    /**
     * 不包含群。
     * 如果是群事件，任何群事件的groupId匹配到在@Filter注解中设置的group，则返回false
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
        }
        return true;
    }),
    /**
     * 包含at
     */
    CONTAIN_AT((context, event) -> {
        MessageEvent e = (MessageEvent) event;
        String msg = e.getMessage();
        if (msg.matches("\\[CQ:at,qq=\\d+\\]")) {
            return true;
        }
        return false;
    }),
    /**
     * 包含at自己
     */
    CONTAIN_AT_SELF((context, event) -> {
        MessageEvent e = (MessageEvent) event;
        String msg = e.getMessage();
        if (msg.matches("\\[CQ:at,qq="+e.getSelfId()+"\\]")) {
            return true;
        }
       return false;
    }),
    /**
     * 包含@qq
     */
    CONTAIN_AT_QQ((context, event) -> {
        MessageEvent e = (MessageEvent) event;
        String msg = e.getMessage();
        for (long qq : context.getQq()) {
            if (msg.matches("\\[CQ:at,qq="+qq+"\\]")) {
                return true;
            }
        }
        return false;
    }),

    ;

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
    private static boolean excludeGroup(long groupId,ListenerContext context){
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

}
