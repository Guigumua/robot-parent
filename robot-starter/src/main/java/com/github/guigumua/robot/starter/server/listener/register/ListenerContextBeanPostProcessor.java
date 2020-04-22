package com.github.guigumua.robot.starter.server.listener.register;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;

import com.github.guigumua.robot.common.event.Event;
import com.github.guigumua.robot.common.event.EventType;
import com.github.guigumua.robot.starter.annotation.Filter;
import com.github.guigumua.robot.starter.annotation.Ignore;
import com.github.guigumua.robot.starter.annotation.Listener;
import com.github.guigumua.robot.starter.client.RobotClient;
import com.github.guigumua.robot.starter.server.filter.ListenerFilter;
import com.github.guigumua.robot.starter.server.filter.MappingFilter;
import com.github.guigumua.robot.starter.server.filter.PostFilter;
import com.github.guigumua.robot.starter.server.filter.PreFilter;
import com.github.guigumua.robot.starter.server.listener.ListenerContext;
import com.github.guigumua.robot.starter.server.listener.ListenerContext.ListenerContextBuilder;
import com.github.guigumua.robot.starter.server.listener.ListenerManager;

/**
 * 加载监听器
 *
 * @author Administrator
 */
@Configuration
public class ListenerContextBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
	private ApplicationContext applicationContext;
	private static final Logger logger = LoggerFactory.getLogger(ListenerContextBeanPostProcessor.class);

	private boolean isListener(Object bean) {
		if (AnnotatedElementUtils.isAnnotated(bean.getClass(), Listener.class))
			return true;
		Method[] methods = bean.getClass().getMethods();
		for (Method method : methods) {
			if (AnnotatedElementUtils.isAnnotated(method, Listener.class)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (isListener(bean)) {
			// 遍历bean的方法
			Class<?> clz = bean.getClass();
			Method[] methods = clz.getDeclaredMethods();
			ListenerManager listenerManager = ListenerManager.getInstance();
			// 如果类上有Listener注解，则将所有方法注册
			if (clz.isAnnotationPresent(Listener.class)) {
				for (Method method : methods) {
					if (!method.isAnnotationPresent(Ignore.class))
						listenerManager.addListenerContext(registerContext(bean, method));
				}
			} else {
				// 否则，遍历每个方法，只有方法上有@Listener注解才注册
				for (Method method : methods) {
					if (method.isAnnotationPresent(Listener.class) && !method.isAnnotationPresent(Ignore.class)) {
						listenerManager.addListenerContext(registerContext(bean, method));
					}
				}
			}
		}
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}

	private ListenerContext registerContext(Object bean, Method method) {
		ListenerContextBuilder builder = ListenerContext.builder();
		resolveAnnotationListener(bean, method, builder);
		resolveAnnotationFilter(method, builder);
		resolveParameters(method, builder);
		// 其他
		builder.method(method);
		builder.invokeObj(bean);
		builder.clazz(bean.getClass());
		logger.info("监听器： {{}.{}} 注册成功！", bean.getClass(), method.getName());
		return builder.build();
	}

	private ListenerContextBuilder resolveAnnotationListener(Object bean, Method method,
			ListenerContextBuilder builder) {
		if (method.isAnnotationPresent(Listener.class)) {
			Listener listener = AnnotationUtils.findAnnotation(method, Listener.class);
			resolveAnnotationListener(builder, listener);
		} else if (bean.getClass().isAnnotationPresent(Listener.class)) {
			Listener listener = AnnotationUtils.findAnnotation(bean.getClass(), Listener.class);
			resolveAnnotationListener(builder, listener);
		}
		return builder;
	}

	private void resolveAnnotationListener(ListenerContextBuilder builder, Listener listener) {
		EventType[] types = listener.type();
		int code = 0;
		for (EventType type : types) {
			code = code | type.getCode();
		}
		builder.listenType(code);
		builder.isBreak(listener.isBreak());
		builder.sort(listener.sort());
	}

	private ListenerContextBuilder resolveAnnotationFilter(Method method, ListenerContextBuilder builder) {
		if (method.isAnnotationPresent(Filter.class)) {
			Filter filter = AnnotationUtils.findAnnotation(method, Filter.class);
			String[] regexes = filter.value();
			Pattern[] pattern = new Pattern[regexes.length];
			for (int i = 0; i < pattern.length; i++) {
				pattern[i] = Pattern.compile(regexes[i]);
			}
			Class<? extends ListenerFilter>[] filterClasses = filter.filters();
			ListenerContext context = builder.build();
			List<MappingFilter> mappingFilters = context.getMappingFilters();
			List<PreFilter> preFilters = context.getPreFilters();
			List<PostFilter> postFilters = context.getPostFilters();
			// 所有拦截器配置
			for (Class<? extends ListenerFilter> filterClz : filterClasses) {
				try {
					if (MappingFilter.class.isAssignableFrom(filterClz)) {
						ListenerFilter newInstance;
						newInstance = filterClz.newInstance();
						mappingFilters.add((MappingFilter) newInstance);
					} else if (PreFilter.class.isAssignableFrom(filterClz)) {
						ListenerFilter newInstance = filterClz.newInstance();
						preFilters.add((PreFilter) newInstance);
					} else if (PostFilter.class.isAssignableFrom(filterClz)) {
						ListenerFilter newInstance = filterClz.newInstance();
						postFilters.add((PostFilter) newInstance);
					}
				} catch (InstantiationException | IllegalAccessException e) {
					logger.error("类{}需要无参构造方法！", filterClz.getName());
				}
			}
			builder.mappingFilters(mappingFilters);
			builder.preFilters(preFilters);
			builder.postFilters(postFilters);
			builder.pattern(pattern);
			builder.group(filter.group());
			builder.qq(filter.qq());
			builder.matchTypes(filter.matchType());
			builder.matchTypeMode(filter.matchTypeModel());
		}
		return builder;
	}

	private ListenerContextBuilder resolveParameters(Method method, ListenerContextBuilder builder) {
		Parameter[] parameters = method.getParameters();
		Object[] params = new Object[parameters.length];
		HashMap<Integer, Parameter> paramsMap = new HashMap<Integer, Parameter>();
		for (int i = 0; i < parameters.length; ++i) {
			Class<?> clazz = parameters[i].getType();
			// event类型和RobotClient跳过
			if (Event.class.isAssignableFrom(clazz) || RobotClient.class.isAssignableFrom(clazz)) {
				paramsMap.put(i, parameters[i]);
				continue;
			}
			Map<String, ?> beans = applicationContext.getBeansOfType(clazz);
			if (beans.size() != 0) {
				// 如果容器内存在则直接注入
				if (beans.size() == 1) {
					params[i] = beans.values().stream().findFirst().orElse(null);
				} else {
					for (Entry<String, ?> entry : beans.entrySet()) {
						String name = entry.getKey();
						if (name.equals(parameters[i].getName())) {
							params[i] = entry.getValue();
						}
					}
				}
			} else {
				// 容器内没有则尝试获取
				try {
					// 有无参构造，可以构造一个实例
					Constructor<?> c = clazz.getConstructor();
					if (c.isAccessible()) {
						Object object = c.newInstance();
						params[i] = object;
					} else {
						paramsMap.put(i, parameters[i]);
					}
				} catch (Exception e2) {
					// 没有无参构造，不能构造等待调用时注入
					paramsMap.put(i, parameters[i]);
				}
			}
		}
		builder.paramsMap(paramsMap);
		builder.params(params);
		return builder;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
