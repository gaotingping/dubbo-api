package com.gtp.dubbo.api.utils;

import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.gtp.dubbo.api.annotation.ApiService;
import com.gtp.dubbo.api.metadata.ApiApplicationInfo;

/**
 * 加载工具
 */
public class ClassLoadUtils {

	private static ClassLoader loader;

	private final static String CONF_FILE = "META-INF/api.txt";

	public static ApiApplicationInfo load(String path) throws Exception {

		ApiApplicationInfo application = new ApiApplicationInfo();

		//File file = new File(path);
		//URL url = file.toURI().toURL();
		//loader = new URLClassLoader(new URL[] { url });
		loader=ClassLoadUtils.class.getClassLoader();
		JarFile jf = new JarFile(path);

		// 配置
		Properties prop = new Properties();
		prop.load(jf.getInputStream(jf.getJarEntry(CONF_FILE)));

		application.setName(prop.getProperty("name"));
		application.setProtocol(prop.getProperty("protocol"));
		application.setAddress(prop.getProperty("address"));

		Enumeration<JarEntry> it = jf.entries();
		while (it.hasMoreElements()) {
			JarEntry tmp = it.nextElement();
			if (!tmp.isDirectory() && tmp.getName().endsWith(".class")) {
				String clazzName = tmp.getName();
				int endIndex = clazzName.lastIndexOf(".");
				clazzName = clazzName.substring(0, endIndex);
				clazzName = clazzName.replace("/", ".");
				Class<?> c = loader.loadClass(clazzName);
				ApiService apiService = c.getAnnotation(ApiService.class);
				System.out.println(c+">"+apiService);
				if(apiService!=null){
					application.addService(c);
				}
			}
		}
		jf.close();

		return application;
	}
}
