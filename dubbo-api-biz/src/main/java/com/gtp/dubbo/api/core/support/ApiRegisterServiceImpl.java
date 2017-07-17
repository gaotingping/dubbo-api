package com.gtp.dubbo.api.core.support;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gtp.dubbo.api.core.ApiRegisterService;
import com.gtp.dubbo.api.metadata.ApiMethodInfo;

/**
 * api 服务注册等管理
 */
@Service
public class ApiRegisterServiceImpl implements ApiRegisterService{

	/**
	 * 方法元信息:app-service-method
	 */
	private final static Map<String, Map<String, Map<String, ApiMethodInfo>>> pool = new HashMap<>();

	public ApiMethodInfo get(String app, String service, String method) {

		// app下所有service
		Map<String, Map<String, ApiMethodInfo>> serviceV = pool.get(app);
		if (serviceV == null) {
			return null;
		}

		Map<String, ApiMethodInfo> methodV = serviceV.get(service);
		if (methodV == null) {
			return null;
		}

		return methodV.get(method);
	}

	public Map<String, ApiMethodInfo> get(String app, String service) {

		Map<String, Map<String, ApiMethodInfo>> serviceV = pool.get(app);
		if (serviceV == null) {
			return null;
		}

		return serviceV.get(service);
	}

	public Map<String, Map<String, ApiMethodInfo>> get(String app) {

		return pool.get(app);
	}

	/**
	 * 在指定应用下，注册服务
	 * 
	 * @param serviceModule
	 * @param p
	 */
	public void register(String app, Map<String, Map<String, ApiMethodInfo>> p) {
		pool.put(app, p);
	}

	public Map<String, Map<String, Map<String, ApiMethodInfo>>> getAll() {
		return pool;
	}
	
}
