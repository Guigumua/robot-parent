package com.github.guigumua.robot.starter.server.listener;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.github.guigumua.robot.starter.server.filter.MappingFilter;
import com.github.guigumua.robot.starter.server.filter.PostFilter;
import com.github.guigumua.robot.starter.server.filter.PreFilter;

public class ListenerContext {
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
    private String[] regex;
    /**
     * 匹配拦截器列表
     */
    private ArrayList<MappingFilter> mappingFilters = new ArrayList<MappingFilter>();
    /**
     * 前置拦截器列表
     */
    private ArrayList<PreFilter> preFilters = new ArrayList<PreFilter>();
    /**
     * 后置拦截器列表
     */
    private ArrayList<PostFilter> postFilters = new ArrayList<PostFilter>();
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

    /**
     * 监听器执行的结果
     */
    private Object result;

    public String[] getRegex() {
        return regex;
    }

    public void setRegex(String[] regex) {
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

    public ArrayList<MappingFilter> getMappingFilters() {
        return mappingFilters;
    }

    public ArrayList<PreFilter> getPreFilters() {
        return preFilters;
    }

    public ArrayList<PostFilter> getPostFilters() {
        return postFilters;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static class ListenerHandlerComparator implements Comparator<ListenerContext> {

        @Override
        public int compare(ListenerContext o1, ListenerContext o2) {
            return o2.getSort() - o1.getSort() > 0 ? -1 : 1;
        }

    }

    @Override
    public String toString() {
        return "ListenerContext:{" + clz.getSimpleName() + "." + method.getName() + "}";
    }
}
