package com.gtp.dubbo.api.metadata;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gtp.dubbo.api.annotation.ApiParam;

//参数封装
public class ApiParamInfo implements Serializable{

	private static final long serialVersionUID = -5324244498094129649L;

	private boolean isList;/*是否集合*/
	
	private Class<?> type;/*类型*/
	
	private ApiParam apiParam;/*注解*/

	public boolean getIsList() {
		return isList;
	}

	public void setIsList(boolean isList) {
		this.isList = isList;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public ApiParam getApiParam() {
		return apiParam;
	}

	public void setApiParam(ApiParam apiParam) {
		this.apiParam = apiParam;
	}

	@Override
	public String toString() {
		return "[isList=" + isList + ", type=" + type + ", apiParam=" + JSON.toJSONString(apiParam) + "]";
	}
}
