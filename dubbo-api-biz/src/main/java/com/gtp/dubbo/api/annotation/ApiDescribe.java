package com.gtp.dubbo.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 其它输入，输出描述
 * 
 * @author gaotingping@cyberzone.cn
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
public @interface ApiDescribe {
	
	String value(); /*字段或实体描述*/
}
