package com.gtp.dubbo.api.enums;

/**
 * 框架错误码:给错误码让前端自定义文案
 */
public interface ApiErrorCode {
	
	String SYSTEM_ERROR="1001";
	String PARAM_ERROR="10002";
	String PARAM_NOT_JSON="10003";
	String APP_ERROR="10004";
	String SERVICE_ERROR="10005";
	String METHOD_ERROR="10006";
}
