package com.gtp.dubbo.api.params;

import java.lang.reflect.Method;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.gtp.dubbo.api.metadata.ApiParamInfo;

/**
 * 参数绑定
 * 
 * @author gaotingping@cyberzone.cn
 */
public interface ParameterBinder {

     
	/**
	 * 获得参数上的所有注解
	 */
	public List<ApiParamInfo> getParamNames(Method method);


	/**
	 * 根据参数名称获得参数对应的输入值
	 */
	public Object[] getParamValues(Method method,List<ApiParamInfo> paramNames,JSONObject args);
}
