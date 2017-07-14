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

	private Method method;/*方法*/
	
	private String methodDesc;/*方法描述*/
	
	private String methodCode;/*方法编码,唯一标识*/
	
	private Object instance;/*实例*/
	
	private  List<ApiParamInfo> inParams;/*参数列表与类型*/
	
	private ApiParamInfo outParams;/*返回值*/
	
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

	public String getMethodDesc() {
		return methodDesc;
	}

	public void setMethodDesc(String methodDesc) {
		this.methodDesc = methodDesc;
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

	@Override
	public String toString() {
		return "[method=" + method + ", methodDesc=" + methodDesc + ", methodCode=" + methodCode
				+ ", inParams=" + inParams + ", outParams=" + outParams + "]";
	}
}
