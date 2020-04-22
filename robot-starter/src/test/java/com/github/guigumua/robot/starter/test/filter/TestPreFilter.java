package com.github.guigumua.robot.starter.test.filter;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.common.event.message.GroupMessageEvent;
import com.github.guigumua.robot.starter.client.RobotClient;
import com.github.guigumua.robot.starter.client.RobotManager;
import com.github.guigumua.robot.starter.server.filter.PreFilter;
import com.github.guigumua.robot.starter.server.listener.ListenerContext;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestPreFilter implements PreFilter {
    private final Map<Long, Boolean> switchs = new HashMap<>();
    private String st = "00:00";
    private String et = "24:00";

    @Override
    public boolean apply(Event event, ListenerContext context, Object... args) {
        GroupMessageEvent e;
        if (event instanceof GroupMessageEvent) {
            e = (GroupMessageEvent) event;
            String msg = e.getMessage();
            long groupId = e.getGroupId();
            RobotManager manager = RobotManager.getInstance();
            RobotClient robotClient = manager.get(event.getSelfId());
            if (msg.contains("开启")) {
                switchs.put(groupId, true);
                robotClient.sendMsg(e, "色图功能开启");
                return false;
            } else if (msg.contains("关闭")) {
                switchs.put(groupId, false);
                robotClient.sendMsg(e, "色图功能关闭");
                return false;
            } else if (msg.contains(":")) {
                String range = StringUtils.substring(msg, 4).trim();
                if (msg.contains("-")) {
                    String[] strings = StringUtils.split(range, "-");
                    this.st = strings[0];
                    this.et = strings[1];
                } else {
                    this.st = range;
                }
                return false;
            } else if (isTimeRange(st, et)) {
                if (switchs.get(groupId) != null) {
                    if (switchs.get(groupId)) {
                        return true;
                    } else {
                        robotClient.sendMsg(e, "功能已关闭");
                        return false;
                    }
                } else {
                    return true;
                }
            } else {
                robotClient.sendMsg(e, "不在开启时间段内");
            }
            return false;
        }
        return true;
    }

    private boolean isTimeRange(String st, String et) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            Date now = df.parse(df.format(new Date()));
            Date begin = df.parse(st);
            Date end = df.parse(et);
            Calendar nowTime = Calendar.getInstance();
            nowTime.setTime(now);
            Calendar beginTime = Calendar.getInstance();
            beginTime.setTime(begin);
            Calendar endTime = Calendar.getInstance();
            endTime.setTime(end);
            return nowTime.before(endTime) && nowTime.after(beginTime);

        } catch (ParseException e1) {
            e1.printStackTrace();
            return false;
        }

    }

}
