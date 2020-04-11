package com.github.guigumua.robot.starter.server.listenerhandler;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.github.guigumua.robot.starter.server.filter.EventFilter;

public class ListenerHandler {
	public static final ListenerHandlerComparator COMPARATOR = new ListenerHandlerComparator();
	/**
	 * 执行的方法
	 */
	private Method method;
	/**
	 * 所属类
	 */
	private Class<?> clz;
	/**
	 * 参数
	 */
	private Object[] params;
	/**
	 * 监听类型
	 */
	private int listenType;
	/**
	 * 执行优先级
	 */
	private int sort;
	/**
	 * 正则匹配
	 */
	private String regex = ".*";
	/**
	 * 拦截器列表
	 */
	private EventFilter[] filters = new EventFilter[] {};
	/**
	 * 阻断
	 */
	private boolean isBreak;

	/**
	 * 调用对象
	 */
	private Object invokeObj;
	/**
	 * 调用时才能注入的参数
	 */
	private Map<Integer, Parameter> paramsMap = new HashMap<Integer, Parameter>();

	public EventFilter[] getFilters() {
		return filters;
	}

	public void setFilters(EventFilter[] filters) {
		this.filters = filters;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public Map<Integer, Parameter> getParamsMap() {
		return paramsMap;
	}

	public Method getMethod() {
		return method;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public boolean isBreak() {
		return isBreak;
	}

	public void setBreak(boolean isBreak) {
		this.isBreak = isBreak;
	}

	public Object getInvokeObj() {
		return invokeObj;
	}

	public void setInvokeObj(Object invokeObj) {
		this.invokeObj = invokeObj;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Class<?> getClz() {
		return clz;
	}

	public void setClz(Class<?> clz) {
		this.clz = clz;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public int getListenType() {
		return listenType;
	}

	public void setListenType(int listenType) {
		this.listenType = listenType;
	}

	public boolean hasType(int code) {
		return (this.listenType & code) == code;
	}

	public static class ListenerHandlerComparator implements Comparator<ListenerHandler> {

		@Override
		public int compare(ListenerHandler o1, ListenerHandler o2) {
			return o2.getSort() - o1.getSort() > 0 ? -1 : 1;
		}

	}
}
