package com.gtp.dubbo.api.core;

import java.util.HashMap;
import java.util.Map;

import com.gtp.dubbo.api.metadata.ApiMethodInfo;

/**
 * API管理类:单例
 * 
 * #层次结构 应用(app)-项目 --->服务(service)-接口 -->方法(method)-方法
 * 
 * 1.每个子项，要求在父目录下唯一,因为服务太多的话，你要求全局唯一这不合适 2.分层次，你可以分层次管理
 * 3.每个对象抽取为单独的类，并且要人容纳一些属性参数
 * 
 * @author gaotingping@cyberzone.cn
 *
 */
public class ApiManager {

	/**
	 * 方法元信息:app-service-method
	 */
	private static Map<String, Map<String, Map<String, ApiMethodInfo>>> pool = new HashMap<>();

	public static ApiMethodInfo get(String app, String service, String method) {

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

	public static Map<String, ApiMethodInfo> get(String app, String service) {

		Map<String, Map<String, ApiMethodInfo>> serviceV = pool.get(app);
		if (serviceV == null) {
			return null;
		}

		return serviceV.get(service);
	}

	public static Map<String, Map<String, ApiMethodInfo>> get(String app) {

		return pool.get(app);
	}

	/**
	 * 在指定应用下，注册服务
	 * 
	 * @param serviceModule
	 * @param p
	 */
	public static void register(String app, Map<String, Map<String, ApiMethodInfo>> p) {
		pool.put(app, p);
	}
}
