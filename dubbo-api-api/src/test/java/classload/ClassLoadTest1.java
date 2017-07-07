package classload;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.gtp.dubbo.api.annotation.ApiService;
import com.gtp.dubbo.api.metadata.ApiApplicationInfo;
import com.gtp.dubbo.api.utils.ClassLoadUtils;

/**
 * 扫描jar下的所有类
 * 不知后续是否有问题，但是比较精简和无依赖
 * 
 * 1.重新加载会不会导致重复加载？需要验证
 * 2.按照jvm的机制,已加载过的类不会再加载(父类委托机制)
 *   因为在一个Java应用中，Class是根据它的全名（包名+类名）
 *   和加载它的 ClassLoader来唯一标识的
 *   
 * @author gaotingping@cyberzone.cn
 */
public class ClassLoadTest1 {

	/**
	 * 卸载不可控制，是否会产生连续垃圾?
	 */
	private static ClassLoader loader;

	public static void main(String[] args) throws Exception{

		String path = "E:/pf/api.jar";
		
		ApiApplicationInfo app = ClassLoadUtils.load(path);
		System.out.println(app);
		
//		File file = new File(path);
//		URL url = file.toURI().toURL();
//		loader = new URLClassLoader(new URL[] {url});
//
//		JarFile jf = new JarFile(path);
//		
//		Properties  prop=new Properties();
//		prop.load(jf.getInputStream(jf.getJarEntry("META-INF/api.txt")));
//		System.out.println(prop);
//
//		Enumeration<JarEntry> it = jf.entries();
//		while (it.hasMoreElements()) {
//			JarEntry tmp = it.nextElement();
//			//仅处理类 
//			if (!tmp.isDirectory() && tmp.getName().endsWith(".class")) {
//				
//				//className
//				String clazzName = tmp.getName();
//				
//				//org/spring/dubbo/IServerImpl.class
//				//org.spring.dubbo.IServerImpl
//	            int endIndex = clazzName.lastIndexOf(".");  
//				clazzName=clazzName.substring(0, endIndex);
//				clazzName = clazzName.replace("/", ".");
//				//System.out.println(clazzName);
//				
//				Class<?> c = loader.loadClass(clazzName);
//				//System.out.println(ReflectUtils.allFields(c));
//				//c = Class.forName(clazzName);
//				ApiService apiService = c.getAnnotation(ApiService.class);
//				System.out.println(apiService);
//				System.out.println(c);
//			}else{
//				System.out.println("不是类:"+tmp);
//			}
//		}
//		jf.close();
	}
}
