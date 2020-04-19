package com.github.guigumua.robot.starter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import com.github.guigumua.robot.starter.server.filter.ListenerFilter;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Filter {
	/**
	 * 监听消息的正则
	 * 
	 * @return 正则
	 */
	@AliasFor(attribute = "value")
	String[] regex() default ".*";

	/**
	 * 同regex
	 * 
	 * @return 同regex
	 */
	@AliasFor(attribute = "regex")
	String[] value() default ".*";

	long[] group() default {};

	long[] qq() default {};

	boolean isAt() default false;

	/**
	 * 拦截器
	 * 
	 * @return 拦截器列表
	 */
	Class<? extends ListenerFilter>[] filters() default {};
}
