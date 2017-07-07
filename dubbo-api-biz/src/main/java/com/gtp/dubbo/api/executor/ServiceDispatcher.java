package com.gtp.dubbo.api.executor;

import com.gtp.dubbo.api.metadata.ApiRequestInfo;
import com.gtp.dubbo.api.metadata.ApiResponseInfo;

/**
 * 服务转发
 * 
 * @author gaotingping@cyberzone.cn
 */
public interface ServiceDispatcher{
	
	//初始化
	public void init();
	
	//服务
	public ApiResponseInfo doService(ApiRequestInfo request);
	
	//销毁
	public void destroy();
}
