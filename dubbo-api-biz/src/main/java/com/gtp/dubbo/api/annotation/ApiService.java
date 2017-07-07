package com.gtp.dubbo.api.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务或接口标识
 * 
 * @author gaotingping@cyberzone.cn
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ApiService {
	
	String value();/*标识*/
	
	String desc();/*描述*/
}
