package com.github.guigumua.robot.starter.server.listener;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.github.guigumua.robot.starter.annotation.MatchType;
import com.github.guigumua.robot.starter.annotation.MatchType.MatchTypeModel;
import com.github.guigumua.robot.starter.server.filter.MappingFilter;
import com.github.guigumua.robot.starter.server.filter.PostFilter;
import com.github.guigumua.robot.starter.server.filter.PreFilter;

import io.netty.util.DefaultAttributeMap;

public class ListenerContext extends DefaultAttributeMap implements Comparable<ListenerContext> {
	public static final ListenerContextComparator COMPARATOR = new ListenerContextComparator();
	/**
	 * 执行的方法
	 */
	private Method method;
	/**
	 * 所属类
	 */
	private Class<?> clazz;
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
	 * 正则对应的pattern
	 */
	private Pattern[] pattern = new Pattern[] { Pattern.compile(".*") };
	/**
	 * {@link com.github.guigumua.robot.starter.annotation.Filter}注解上的qq
	 */
	private long[] qq = new long[] {};
	/**
	 * {@link com.github.guigumua.robot.starter.annotation.Filter}注解上的group
	 */
	private long[] group = new long[] {};
	/**
	 * 匹配拦截器列表
	 */
	private List<MappingFilter> mappingFilters = new ArrayList<>();
	/**
	 * 前置拦截器列表
	 */
	private List<PreFilter> preFilters = new ArrayList<>();
	/**
	 * 后置拦截器列表
	 */
	private List<PostFilter> postFilters = new ArrayList<>();
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
	private Map<Integer, Parameter> paramsMap = new HashMap<>();
	/**
	 * 匹配的方式
	 */
	private MatchType[] matchTypes = new MatchType[] { MatchType.ANY_REGEX };
	/**
	 * 多匹配方式的处理逻辑
	 */
	private MatchTypeModel matchTypeMode = MatchTypeModel.ANY;

	public MatchType[] getMatchTypes() {
		return matchTypes;
	}

	public Pattern[] getPattern() {
		return pattern;
	}

	public long[] getQq() {
		return qq;
	}

	public long[] getGroup() {
		return group;
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

	public boolean isBreak() {
		return isBreak;
	}

	public Object getInvokeObj() {
		return invokeObj;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Object[] getParams() {
		return params;
	}

	public int getListenType() {
		return listenType;
	}

	public List<MappingFilter> getMappingFilters() {
		return mappingFilters;
	}

	public List<PreFilter> getPreFilters() {
		return preFilters;
	}

	public List<PostFilter> getPostFilters() {
		return postFilters;
	}

	public MatchTypeModel getMatchTypeModel() {
		return matchTypeMode;
	}

	@Override
	public int compareTo(ListenerContext o) {
		return o.getSort() - getSort() >= 0 ? -1 : 1;
	}

	public static class ListenerContextComparator implements Comparator<ListenerContext> {

		@Override
		public int compare(ListenerContext o1, ListenerContext o2) {
			return o2.getSort() - o1.getSort() >= 0 ? -1 : 1;
		}

	}

	@Override
	public String toString() {
		return "ListenerContext:{" + clazz.getSimpleName() + "." + method.getName() + "}";
	}

	public static ListenerContextBuilder builder() {
		return new ListenerContextBuilder(new ListenerContext());
	}

	public static class ListenerContextBuilder {
		private final ListenerContext context;

		private ListenerContextBuilder(ListenerContext context) {
			this.context = context;
		}

		public void method(Method method) {
			context.method = method;
		}

		public void clazz(Class<?> clazz) {
			context.clazz = clazz;
		}

		public void params(Object[] params) {
			context.params = params;
		}

		public void listenType(int code) {
			context.listenType = code;
		}

		public void sort(int sort) {
			context.sort = sort;
		}

		public void pattern(Pattern[] pattern) {
			context.pattern = pattern;
		}

		public void qq(long[] qq) {
			context.qq = qq;
		}

		public void group(long[] group) {
			context.group = group;
		}

		public void mappingFilters(List<MappingFilter> mappingFilters) {
			context.mappingFilters = mappingFilters;
		}

		public void preFilters(List<PreFilter> preFilters) {
			context.preFilters = preFilters;
		}

		public void postFilters(List<PostFilter> postFilters) {
			context.postFilters = postFilters;
		}

		public void isBreak(boolean isBreak) {
			context.isBreak = isBreak;
		}

		public void invokeObj(Object invokeObj) {
			context.invokeObj = invokeObj;
		}

		public void paramsMap(Map<Integer, Parameter> paramsMap) {
			context.paramsMap = paramsMap;
		}

		public void matchTypes(MatchType[] matchTypes) {
			context.matchTypes = matchTypes;
		}

		public void matchTypeMode(MatchTypeModel matchTypeMode) {
			context.matchTypeMode = matchTypeMode;
		}

		public ListenerContext build() {
			return context;
		}
	}

}
