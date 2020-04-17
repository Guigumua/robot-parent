package com.github.guigumua.robot.starter.server.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.github.guigumua.robot.common.event.EventType;

public class ListenerManager {
	private static final Map<EventType, Set<ListenerContext>> listeners = new HashMap<>();
	private static final ListenerManager instance = new ListenerManager();

	private ListenerManager() {
		for (EventType e : EventType.values()) {
			if (e == EventType.MESSAGE | e == EventType.NOTICE | e == EventType.REQUEST) {
				continue;
			}
			listeners.put(e, new TreeSet<>(ListenerContext.COMPARATOR));
		}
	}

	public static ListenerManager getInstance() {
		return instance;
	}


	public Set<ListenerContext> getListenerChain(EventType type) {
		return listeners.get(type);
	}

	public void addListenerContext(ListenerContext context) {
		int listenType = context.getListenType();
		EventType[] values = EventType.values();
		for (EventType e : values) {
			if (e == EventType.MESSAGE || e == EventType.NOTICE || e == EventType.REQUEST) {
				continue;
			}
			if ((e.getCode() & listenType) == e.getCode()) {
				listeners.get(e).add(context);
			}
		}
	}
}
