package com.gtp.dubbo.api.metadata;

import java.util.Date;

import com.gtp.dubbo.api.annotation.ApiDescribe;

/**
 * API响应体抽象
 * 
 * @author gaotingping@cyberzone.cn
 */
@ApiDescribe("响应封装")
public class ApiResponseInfo {

	@ApiDescribe("接口处理状态:true/false")
	private boolean status=false;
	
	@ApiDescribe("发生错误时的错误码")
	private String error;
	
	@ApiDescribe("业务数据")
	private Object result;
	
	@ApiDescribe("当前时间")
	private Date stime=new Date();
	
	public ApiResponseInfo(Object data) {
		super();
		this.status=true;
		this.result = data;
	}

	public ApiResponseInfo(String error) {
		super();
		this.status=false;
		this.error = error;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object data) {
		this.result = data;
	}

	public Date getStime() {
		return stime;
	}

	public void setStime(Date stime) {
		this.stime = stime;
	}

	public static ApiResponseInfo failure(String error) {
		return new ApiResponseInfo(error);
	}

	public static ApiResponseInfo result(Object data) {
		return new ApiResponseInfo(data);
	}
}
