package com.gtp.dubbo.api.metadata;

import com.gtp.dubbo.api.annotation.ApiDescribe;
import com.gtp.dubbo.api.enums.ApiErrorCode;

/**
 * API响应体抽象
 * 
 * @author gaotingping@cyberzone.cn
 */
@ApiDescribe("响应封装")
public class ApiResponseInfo {

	@ApiDescribe("接口处理状态:true/false")
	private boolean status=false;/*接口处理状态:true/false*/
	
	@ApiDescribe("发生错误时的错误码")
	private ApiErrorCode error;/*发生错误时的错误码*/
	
	@ApiDescribe("业务数据")
	private Object data;/*业务数据*/
	
	public ApiResponseInfo(Object data) {
		super();
		this.status=true;
		this.data = data;
	}

	public ApiResponseInfo(ApiErrorCode error) {
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

	public ApiErrorCode getError() {
		return error;
	}

	public void setError(ApiErrorCode error) {
		this.error = error;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static ApiResponseInfo failure(ApiErrorCode error) {
		return new ApiResponseInfo(error);
	}

	public static ApiResponseInfo result(Object data) {
		return new ApiResponseInfo(data);
	}
}
