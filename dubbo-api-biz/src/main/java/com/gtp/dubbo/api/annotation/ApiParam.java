package com.gtp.dubbo.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数标识
 * 
 * @author gaotingping@cyberzone.cn
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiParam {

	String value();/*名称*/
	
	String desc() default "";/*描述*/
}
