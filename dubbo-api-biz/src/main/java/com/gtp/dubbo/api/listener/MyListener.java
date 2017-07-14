package com.gtp.dubbo.api.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import com.alibaba.fastjson.JSON;
import com.gtp.dubbo.api.common.AppConfig;
import com.gtp.dubbo.api.core.ApiInit;
import com.gtp.dubbo.api.core.ApiManager;
import com.gtp.dubbo.api.params.ParameterBinder;
import com.gtp.dubbo.api.params.support.DefaultParameterBinder;

/**
 * 负责日期得初始化
 * 
 * @author gaotingping@cyberzone.cn
 */
public class MyListener implements ApplicationListener<ApplicationContextEvent>, ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(MyListener.class);

	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context=applicationContext;
	}

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		if(event instanceof ContextRefreshedEvent){
			logger.info("初始化容器==start");

			try {

				// 初始化本地服务
				ParameterBinder parameterBinder = null;
				try {
					parameterBinder = context.getBean(ParameterBinder.class);
				} catch (Exception e) {
					parameterBinder = new DefaultParameterBinder();
				}
				if (parameterBinder == null) {
					parameterBinder = new DefaultParameterBinder();
				}
				
				AppConfig appConfig = context.getBean(AppConfig.class);

				// 初始化远程服务
				ApiInit.parseJar(parameterBinder,appConfig);
				
				//数据
				System.out.println(JSON.toJSONString(ApiManager.getPool()));

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			logger.info("初始化容器==end");
		}
	}
}
