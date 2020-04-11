package com.github.guigumua.robot.starter.configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import com.github.guigumua.robot.commom.event.Event;
import com.github.guigumua.robot.commom.event.EventType;
import com.github.guigumua.robot.starter.annotation.Filter;
import com.github.guigumua.robot.starter.annotation.Listener;
import com.github.guigumua.robot.starter.server.listenerhandler.HandlerManager;
import com.github.guigumua.robot.starter.server.listenerhandler.ListenerHandler;

/**
 * 加载监听器
 * 
 * @author Administrator
 *
 */
@Configuration
public class ListenerHandlerPostProcesser implements BeanPostProcessor, ApplicationContextAware {
	private ApplicationContext context;
	private static final Logger logger = LoggerFactory.getLogger(ListenerHandlerPostProcesser.class);

	private boolean isListener(Object bean) {
		Method[] methods = bean.getClass().getMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(Listener.class)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (isListener(bean)) {
			// 遍历bean的方法
			Class<? extends Object> clz = bean.getClass();
			Method[] methods = clz.getMethods();
			for (Method method : methods) {
				// 如果是listener，获取注解参数
				if (method.isAnnotationPresent(Listener.class)) {
					ListenerHandler handler = new ListenerHandler();
					Listener listener = method.getAnnotation(Listener.class);
					EventType[] types = listener.type();
					// 位运算获取监听类型的code
					int code = 0;
					for (EventType type : types) {
						code = code | type.getCode();
					}
					handler.setListenType(code);
					// 设置优先级
					int sort = listener.sort();
					handler.setSort(sort);
					// 参数预处理
					Parameter[] parameters = method.getParameters();
					handler.setInvokeObj(bean);
					Object[] params = new Object[parameters.length];
					for (int i = 0; i < parameters.length; ++i) {
						Class<?> clazz = parameters[i].getType();
						Map<Integer, Parameter> pm = handler.getParamsMap();
						try {
							// 如果容器内存在则直接注入
							Object obj = context.getBean(clazz);
							if (obj != null) {
								params[i] = obj;
							}
						} catch (NoSuchBeanDefinitionException e) {
							// 容器内没有则尝试获取
							try {
								// event类型的对象就跳过，等待调用时注入
								if (Event.class.isAssignableFrom(clazz)) {
									pm.put(i, parameters[i]);
									continue;
								}
								// 有无参构造，可以构造一个实例
								Constructor<?> c = clazz.getDeclaredConstructor();
								if (c.isAccessible()) {
									Object object = c.newInstance();
									params[i] = object;
								}
							} catch (Exception e2) {
								// 没有无参构造，不能构造等待调用时注入
								pm.put(i, parameters[i]);
							}
						}
					}

					if (method.isAnnotationPresent(Filter.class)) {
						Filter filter = method.getAnnotation(Filter.class);
						String regex = filter.value();
						handler.setRegex(regex);
						Class<? extends com.github.guigumua.robot.starter.server.filter.EventFilter>[] classes = filter.filters();
						com.github.guigumua.robot.starter.server.filter.EventFilter[] filters = new com.github.guigumua.robot.starter.server.filter.EventFilter[classes.length];
						for (int i = 0; i < classes.length; ++i) {
							try {
								filters[i] = classes[i].newInstance();
							} catch (InstantiationException | IllegalAccessException e) {
								logger.error("{}类没有无参构造方法，获取拦截器实例失败", classes[i]);
							}
						}
						handler.setFilters(filters);
					}
					// 其他
					handler.setBreak(listener.isBreak());
					handler.setClz(clz);
					handler.setParams(params);
					handler.setMethod(method);
					HandlerManager manager = context.getBean(HandlerManager.class);
					manager.add(handler);
					logger.info("listener handler register successed: {}.{}", handler.getClz(),
							handler.getMethod().getName());
				}
			}
		}
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
