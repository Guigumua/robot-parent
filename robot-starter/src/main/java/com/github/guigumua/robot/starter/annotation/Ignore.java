package com.github.guigumua.robot.starter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 当类上有{@link Listener}注解时，会将类中所有声明pulic的方法注册为监听器，这个注解可以使方法不被注册为监听器
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ignore {

}
