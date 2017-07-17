package com.gtp.dubbo.api.core.support;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.gtp.dubbo.api.annotation.ApiMethod;
import com.gtp.dubbo.api.annotation.ApiService;
import com.gtp.dubbo.api.common.AppConfig;
import com.gtp.dubbo.api.core.ApiJarService;
import com.gtp.dubbo.api.core.ApiRegisterService;
import com.gtp.dubbo.api.listener.AppInitListener;
import com.gtp.dubbo.api.metadata.ApiApplicationInfo;
import com.gtp.dubbo.api.metadata.ApiMethodInfo;
import com.gtp.dubbo.api.params.ParameterBinder;
import com.gtp.dubbo.api.utils.ClassLoadUtils;
import com.gtp.dubbo.api.utils.Md5Utils;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ApiInitServiceImpl implements ApiJarService{

	private static final Logger logger = LoggerFactory.getLogger(AppInitListener.class);

	private static final Map<String, String> jarCache = new HashMap<>();
	
	@Autowired
	private ParameterBinder parameterBinder;
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private ApiRegisterService registerService;

	public void initAll() throws Exception {

		String jarPath = appConfig.getJarPath();

		// file is exits
		File dir = new File(jarPath);
		if (!dir.exists()) {
			logger.error("Jar path is not exits:" + jarPath);
			return;
		}

		// 只取jar
		String[] list = dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		});

		if (list == null || list.length < 1) {
			logger.error("Jar path is empty:" + jarPath);
			return;
		}

		for (String path : list) {
			String v2 = Md5Utils.md5File(jarPath + path);
			jarCache.put(jarPath + path, v2);
			parseJar(jarPath + path);
		}

	}

	/**
	 * 加载单个jar
	 */
	private void parseJar(String jarPath) throws Exception {

		logger.info("===开始注册dubbo服务===");
		logger.info("jarPath=" + jarPath);

		ApiApplicationInfo app = ClassLoadUtils.load(jarPath);

		/**
		 * 1.每个应用一个 2.每个jar一个服务 3.zk等地址，放到配置文件
		 */
		// 当前应用配置
		ApplicationConfig application = new ApplicationConfig();
		application.setName(app.getName());

		// 连接注册中心配置
		RegistryConfig registry = new RegistryConfig();
		registry.setProtocol(app.getProtocol());
		registry.setAddress(app.getAddress());

		/**
		 * 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接
		 * 以及与服务提供方的连接引用远程服务,此实例很重，封装了与注册中
		 * 心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接 
		 * 泄漏,每个接口一个对象reference对象
		 */
		// 每个服务一个reference
		List<Class<?>> list = app.getServices();
		if (list == null || list.isEmpty()) {
			return;
		}

		Map<String, Map<String, ApiMethodInfo>> services = new HashMap<>();

		for (Class<?> c : list) {

			ReferenceConfig reference = new ReferenceConfig();
			reference.setApplication(application);
			reference.setRegistry(registry); /* 多个注册中心可以用setRegistries(),其内部也是这样转换的 */
			reference.setInterface(c);
			reference.setCheck(false);/* #bug fix 不检测服务提供者，防止个别服务错误导致api启动不了 */

			// reference.setVersion("1.0.0");//超时 版本 重试次数
			// reference.setTimeout(6000);
			// reference.setRetries(0);

			// 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
			Object instance = reference.get();

			// 解析这个类IService，获得所有方法
			ApiService apiService = c.getAnnotation(ApiService.class);

			Map<String, ApiMethodInfo> methods = new HashMap<>();

			for (Method method : c.getDeclaredMethods()) {/* DeclaredMethod所有的，不含继承,包括私有的 */
				ApiMethod serviceCode = method.getAnnotation(ApiMethod.class);
				if (serviceCode != null) {
					ApiMethodInfo m = new ApiMethodInfo();
					m.setInstance(instance); // 全局缓存
					m.setMethod(method);
					m.setMethodCode(serviceCode.value());
					m.setMethodDesc(serviceCode.desc());
					m.setInParams(parameterBinder.getInParams(method));
					m.setOutParams(parameterBinder.getOutParams(method));
					methods.put(serviceCode.value(), m);
				}
			}

			services.put(apiService.value(), methods);
		}

		registerService.register(app.getName(), services);

		logger.info("===结束注册dubbo服务===");
	}

	public void refresh(String jarPath) throws Exception {

		// file is exits
		File f = new File(jarPath);
		if (!f.exists()) {
			logger.error("Jar path is not exits:" + jarPath);
			return;
		}

		String md5 = Md5Utils.md5File(jarPath);
		if (md5 == null) {
			logger.info("忽略path(文件为空或未改变):" + jarPath);
			return;
		}

		if (jarCache.containsKey(jarPath)) {
			String v = jarCache.get(jarPath);
			if (!v.equals(md5)) {
				//文件更新了,重新加载
				parseJar(jarPath);
			} else {
				logger.info("忽略path(文件为空或未改变):" + jarPath);
			}
		} else {
			jarCache.put(jarPath, md5);
			parseJar(jarPath);
		}
	}

	public void refreshAll() throws Exception {

		String jarPath = appConfig.getJarPath();

		// file is exits
		File dir = new File(jarPath);
		if (!dir.exists()) {
			logger.error("Jar path is not exits:" + jarPath);
			return;
		}

		// 只取jar
		String[] list = dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		});

		if (list == null || list.length < 1) {
			logger.error("Jar path is empty:" + jarPath);
			return;
		}

		for (String path : list) {

			String md5 = Md5Utils.md5File(jarPath + path);
			if (md5 == null) {
				logger.info("忽略path(文件为空或未改变):" + path);
				continue;
			}

			if (jarCache.containsKey(jarPath + path)) {
				String v1 = jarCache.get(jarPath + path);
				if (!v1.equals(md5)) {
					parseJar(jarPath + path);
				} else {
					logger.info("忽略path(文件为空或未改变):" + path);
				}
			} else {
				jarCache.put(jarPath + path, md5);
				parseJar(jarPath + path);
			}

		}
	}
}
