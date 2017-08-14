package com.gtp.dubbo.api.metadata;

import java.lang.reflect.Method;
import java.util.List;

import com.gtp.dubbo.api.annotation.ApiDescribe;
import com.gtp.dubbo.api.annotation.ApiMethod;

/**
 * API方法抽象
 * 
 * @author gaotingping@cyberzone.cn
 */
@ApiDescribe("方法封装")
public class ApiMethodInfo {

	private Method method;/*方法*/

	private Object instance;/*实例*/
	
	private ApiMethod annotate;/*方法注解*/
	
	private  List<ApiParamInfo> inParams;/*参数列表与类型*/
	
	private ApiParamInfo outParams;/*返回值*/

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
	
	public List<ApiParamInfo> getInParams() {
		return inParams;
	}

	public void setInParams(List<ApiParamInfo> inParams) {
		this.inParams = inParams;
	}

	public ApiParamInfo getOutParams() {
		return outParams;
	}

	public void setOutParams(ApiParamInfo outParams) {
		this.outParams = outParams;
	}

	public ApiMethod getAnnotate() {
		return annotate;
	}

	public void setAnnotate(ApiMethod annotate) {
		this.annotate = annotate;
	}
}
