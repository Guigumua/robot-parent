package com.github.guigumua.robot.starter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import com.github.guigumua.robot.starter.annotation.MatchType.MatchTypeModel;
import com.github.guigumua.robot.starter.server.filter.ListenerFilter;

/**
 * 
 * @author Administrator
 *
 */
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

	/**
	 *
	 * @return 群号
	 */
	long[] group() default {};

	/**
	 *
	 * @return QQ号
	 */
	long[] qq() default {};

	/**
	 *
	 * @return 匹配方式
	 */
	MatchType[] matchType() default MatchType.ANY_REGEX;

	/**
	 * 多匹配方式的处理逻辑
	 * 
	 * @return
	 */
	MatchTypeModel matchTypeModel() default MatchTypeModel.ANY;

	/**
	 * 拦截器
	 * 
	 * @return 拦截器列表
	 */
	Class<? extends ListenerFilter>[] filters() default {};
}
