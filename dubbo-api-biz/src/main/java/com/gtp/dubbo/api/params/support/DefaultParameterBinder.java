package com.gtp.dubbo.api.params.support;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gtp.dubbo.api.metadata.ApiParamInfo;
import com.gtp.dubbo.api.params.ParameterBinder;
import com.gtp.dubbo.api.utils.ReflectUtils;


@Service
public class DefaultParameterBinder implements ParameterBinder {

	public List<ApiParamInfo> getInParams(Method method){
		return ReflectUtils.getParameterInfo(method);
	}
	
	public ApiParamInfo getOutParams(Method method){
		return ReflectUtils.getReturnInfo(method);
	}

	public Object[] getParamValues(List<ApiParamInfo> paramNames,
			JSONObject args) {
		
		int size=paramNames.size();
		Object [] paramValues = new Object[size];
		for (int i = 0; i < size; i++) {
			ApiParamInfo bp = paramNames.get(i);
			paramValues[i] = getInptVal(args,bp);
		}
		
		return paramValues;
	}

	private Object getInptVal(JSONObject args, ApiParamInfo p) {
		if(p.getIsList()){
			String v = args.getString(p.getApiParam().value());
			return JSON.parseArray(v,p.getType());
		}else{
			return args.getObject(p.getApiParam().value(), p.getType());
		}
	}
}
