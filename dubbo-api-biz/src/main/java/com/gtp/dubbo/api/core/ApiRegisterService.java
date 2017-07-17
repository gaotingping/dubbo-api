package com.gtp.dubbo.api.core;

import java.util.Map;

import com.gtp.dubbo.api.metadata.ApiMethodInfo;

/**
 * api 服务注册等管理
 */
public interface ApiRegisterService {

	public ApiMethodInfo get(String app, String service, String method);

	public Map<String, ApiMethodInfo> get(String app, String service);

	public Map<String, Map<String, ApiMethodInfo>> get(String app);

	public void register(String app, Map<String, Map<String, ApiMethodInfo>> p);

	public Map<String, Map<String, Map<String, ApiMethodInfo>>> getAll();
	
}
