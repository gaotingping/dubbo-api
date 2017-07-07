package com.gtp.dubbo.api.metadata;

import com.gtp.dubbo.api.annotation.ApiDescribe;

/**
 * API请求体抽象
 * 
 * @author gaotingping@cyberzone.cn
 */
@ApiDescribe("请求封装")
public class ApiRequestInfo {

	private String app;/*应用标识*/
	
	private String service;/*service：服务标识*/
	
	private String method;/*方法标识*/
	
	private String content;/*请求体*/
	
	private String channel;/*产品渠道:ios/pc/android*/
	
	private String domain;/*数据域，同一个服务可能对多个产品提供支持，用域划分数据空间*/
	
	private String token;/*登录凭证*/

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	/**
	 * FIXME 
	 * 注入环境或了解环境，可能也是迫切的需求
	 * 可选:系统级别的参数,什么服务分类等
	 */
	//private Map<String,Object> reqContext;/*请求上下文或环境,搞个map吧,可以注入header参数，cookie等*/
}
