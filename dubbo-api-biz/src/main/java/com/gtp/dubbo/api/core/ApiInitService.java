package com.gtp.dubbo.api.core;

/**
 * 读取配置文件获得jar 解析jar
 * 
 * @author gaotingping@cyberzone.cn
 */
public interface ApiInitService {

	/**
	 * 重启的时候，全部重新加载
	 * 
	 * @param parameterBinder
	 * @param appConfig
	 * @throws Exception
	 */
	public void initAll() throws Exception;

	/**
	 * 刷新单个jar
	 * 
	 * @param parameterBinder
	 * @param appConfig
	 * @throws Exception
	 */
	public void refresh(String jarPath) throws Exception;

	/**
	 * 刷新jar工作区，以文件的md5值为区分是否更新
	 * 
	 * @param parameterBinder
	 * @param appConfig
	 * @throws Exception
	 */
	public void refreshAll() throws Exception;
}
