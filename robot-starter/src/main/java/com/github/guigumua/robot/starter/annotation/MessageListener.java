package com.github.guigumua.robot.starter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import com.github.guigumua.robot.common.event.EventType;

@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Listener(EventType.MESSAGE)
public @interface MessageListener {
	/**
	 * 优先级，越大优先级越低
	 * 
	 * @return
	 */
	@AliasFor(annotation = Listener.class)
	int sort() default Integer.MAX_VALUE;

	/**
	 * 当这个监听器执行完成后，是否跳过优先级在之后的其他监听器的执行
	 * 
	 * @return
	 */
	@AliasFor(annotation = Listener.class)
	boolean isBreak() default false;
}