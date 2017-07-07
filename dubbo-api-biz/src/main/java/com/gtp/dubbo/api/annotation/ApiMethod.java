package com.gtp.dubbo.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法标识
 * 
 * @author gaotingping@cyberzone.cn
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiMethod {
	
	String value();/*方法标识*/
	
	String desc();/*方法描述*/
	
	boolean skipBP() default false;/*跳过参数绑定*/
}
