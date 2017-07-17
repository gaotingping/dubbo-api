package com.gtp.dubbo.api.params;

import java.lang.reflect.Method;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.gtp.dubbo.api.metadata.ApiParamInfo;

/**
 * 参数解析与绑定
 * 
 * @author gaotingping@cyberzone.cn
 */
public interface ParameterBinder {

     
	/**
	 * 获得“输入”参数
	 * 
	 * @param method
	 * @return
	 */
	public List<ApiParamInfo> getInParams(Method method);
	
	
	/**
	 * 获得“输出”参数
	 * 
	 * @param method
	 * @return
	 */
	public ApiParamInfo getOutParams(Method method);

	
	/**
	 * 根据实参和形参获得输入值
	 * 
	 * @param paramNames
	 * @param args
	 * @return
	 */
	public Object[] getParamValues(List<ApiParamInfo> paramNames,JSONObject args);
}
