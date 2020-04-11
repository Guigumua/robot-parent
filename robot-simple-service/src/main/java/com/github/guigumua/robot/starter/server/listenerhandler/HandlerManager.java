package com.github.guigumua.robot.starter.server.listenerhandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.github.guigumua.robot.commom.event.EventType;

public class HandlerManager {
	private Map<EventType, Set<ListenerHandler>> listeners = new HashMap<>();

	{
		for (EventType e : EventType.values()) {
			if (e == EventType.MESSAGE | e == EventType.NOTICE | e == EventType.REQUEST) {
				continue;
			}
			listeners.put(e, new TreeSet<>(ListenerHandler.COMPARATOR));
		}
	}

	public Set<ListenerHandler> getHandlers(EventType type) {
		return listeners.get(type);
	}

	public Set<ListenerHandler> getListenerChain(EventType e) {
		return listeners.get(e);
	}

	public void add(ListenerHandler handler) {
		int listenType = handler.getListenType();
		EventType[] values = EventType.values();
		for (EventType e : values) {
			if (e == EventType.MESSAGE || e == EventType.NOTICE || e == EventType.REQUEST) {
				continue;
			}
			if ((e.getCode() & listenType) == e.getCode()) {
				listeners.get(e).add(handler);
			}
		}
	}
}
