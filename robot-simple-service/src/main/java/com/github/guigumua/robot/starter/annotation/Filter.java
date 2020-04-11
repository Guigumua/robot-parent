package com.github.guigumua.robot.starter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Filter {
	/**
	 * 监听消息的正则
	 * 
	 * @return
	 */
	@AliasFor(attribute = "value")
	String regex() default ".*";

	/**
	 * 同regex
	 * 
	 * @return
	 */
	@AliasFor(attribute = "regex")
	String value() default ".*";

	/**
	 * 拦截器，在监听器将要执行前执行
	 * 
	 * @return
	 */
	Class<? extends com.github.guigumua.robot.starter.server.filter.EventFilter>[] filters() default {};
}
