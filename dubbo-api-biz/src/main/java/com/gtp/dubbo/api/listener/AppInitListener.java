package com.gtp.dubbo.api.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import com.gtp.dubbo.api.core.ApiJarService;

/**
 * 负责日期得初始化
 * 
 * @author gaotingping@cyberzone.cn
 */
public class AppInitListener implements ApplicationListener<ApplicationContextEvent>{

	private static final Logger logger = LoggerFactory.getLogger(AppInitListener.class);

	@Autowired
	private ApiJarService apiInitService;

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		if(event instanceof ContextRefreshedEvent){
			
			logger.info("初始化容器==start");

			try {
				// 初始化远程服务
				apiInitService.initAll();

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			logger.info("初始化容器==end");
		}
	}
}
