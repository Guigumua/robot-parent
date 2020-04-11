package com.github.guigumua.robot.starter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import com.github.guigumua.robot.commom.event.EventType;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Listener {
	/**
	 * 监听类型，默认监听所有
	 * 
	 * @return
	 */
	@AliasFor(attribute = "value")
	EventType[] type() default EventType.ALL;

	@AliasFor(attribute = "type")
	EventType[] value() default EventType.ALL;

	/**
	 * 优先级，越大优先级越低
	 * 
	 * @return
	 */
	int sort() default Integer.MAX_VALUE;

	/**
	 * 当这个监听器执行完成后，是否跳过优先级在之后的其他监听器的执行
	 * 
	 * @return
	 */
	boolean isBreak() default false;
}
