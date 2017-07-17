package com.gtp.dubbo.api.executor.support;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gtp.dubbo.api.annotation.ApiMethod;
import com.gtp.dubbo.api.core.ApiRegisterService;
import com.gtp.dubbo.api.enums.ApiErrorCode;
import com.gtp.dubbo.api.executor.ServiceDispatcher;
import com.gtp.dubbo.api.metadata.ApiMethodInfo;
import com.gtp.dubbo.api.metadata.ApiParamInfo;
import com.gtp.dubbo.api.metadata.ApiRequestInfo;
import com.gtp.dubbo.api.metadata.ApiResponseInfo;
import com.gtp.dubbo.api.params.ParameterBinder;
import com.gtp.dubbo.api.utils.CommonBiz;

/**
 * 服务转发器
 * 
 * @author gaotingping@cyberzone.cn
 *
 *         Bean在实例化的过程中：Constructor > @PostConstruct >InitializingBean >
 *         init-method Bean在销毁的过程中：@PreDestroy > DisposableBean > destroy-method
 *         implements InitializingBean,DisposableBean
 */
@Service
public class DefaultServiceDispatcher implements ServiceDispatcher {

	private static final Logger logger = LoggerFactory.getLogger(DefaultServiceDispatcher.class);

	@Autowired
	private ParameterBinder parameterBinder;
	
	@Autowired
	private ApiRegisterService registerService;

	@Override
	public ApiResponseInfo doService(ApiRequestInfo request) {

		try {

			//参数处理
			String app = request.getApp();
			String service = request.getService();
			String method = request.getMethod();
			
			if(CommonBiz.isEmpty(app,service,method)){
				return ApiResponseInfo.failure(ApiErrorCode.PARAM_ERROR);
			}
			
			// 获得方法
			ApiMethodInfo m = registerService.get(app, service, method);
			if (m == null) {
				return ApiResponseInfo.failure(ApiErrorCode.METHOD_ERROR);
			}

			// 方法输入参数为空
			List<ApiParamInfo> pts = m.getInParams();
			if (pts == null || pts.isEmpty()) {
				return doExecute(m.getMethod(), m.getInstance(), null);
			}

			// 方法输入参数不为空，但是输入为空
			JSONObject args = JSONObject.parseObject(request.getContent());
			if (args == null || args.isEmpty()) {
				return ApiResponseInfo.failure(ApiErrorCode.PARAM_ERROR);
			}

			// 跳过参数绑定
			ApiMethod apiMethod = m.getMethod().getAnnotation(ApiMethod.class);
			if (apiMethod.skipBP()) {
				return doExecute(m.getMethod(), m.getInstance(), new Object[] { args });
			}

			//绑定参数，执行
			Object[] paramValues = parameterBinder.getParamValues(pts, args);
			return doExecute(m.getMethod(), m.getInstance(), paramValues);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return ApiResponseInfo.failure(ApiErrorCode.SYSTEM_ERROR);
	}

	private ApiResponseInfo doExecute(Method method, Object instance, Object[] args){
		
		try{
			Object data = method.invoke(instance, args);
			return ApiResponseInfo.result(data);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		
		return ApiResponseInfo.failure(ApiErrorCode.SYSTEM_ERROR);
	}

	@PostConstruct
	public void init() {
		logger.info("started ...");
	}

	@PreDestroy
	public void destroy() {
		logger.info("closed ...");
	}
}
