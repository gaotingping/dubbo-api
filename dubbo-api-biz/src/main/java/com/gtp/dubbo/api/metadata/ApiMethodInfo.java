package com.gtp.dubbo.api.metadata;

import java.lang.reflect.Method;
import java.util.List;

import com.gtp.dubbo.api.annotation.ApiDescribe;

/**
 * API方法抽象
 * 
 * @author gaotingping@cyberzone.cn
 */
@ApiDescribe("方法封装")
public class ApiMethodInfo {

	private String methodCode;/*方法编码,唯一标识*/
	
	private Object instance;/*实例*/
	
	private Method method;/*方法*/
	
	private  List<ApiParamInfo> paramNames;/*参数列表与类型*/
	
	private String methodDesc;/*方法描述*/
	
	public String getMethodCode() {
		return methodCode;
	}

	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}

	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public List<ApiParamInfo> getParamNames() {
		return paramNames;
	}

	public void setParamNames(List<ApiParamInfo> paramNames) {
		this.paramNames = paramNames;
	}

	public String getMethodDesc() {
		return methodDesc;
	}

	public void setMethodDesc(String methodDesc) {
		this.methodDesc = methodDesc;
	}
}
