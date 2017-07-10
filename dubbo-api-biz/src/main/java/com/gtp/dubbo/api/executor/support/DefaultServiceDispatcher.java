package com.gtp.dubbo.api.executor.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gtp.dubbo.api.annotation.ApiMethod;
import com.gtp.dubbo.api.core.ApiManager;
import com.gtp.dubbo.api.enums.ApiErrorCode;
import com.gtp.dubbo.api.executor.ServiceDispatcher;
import com.gtp.dubbo.api.metadata.ApiMethodInfo;
import com.gtp.dubbo.api.metadata.ApiRequestInfo;
import com.gtp.dubbo.api.metadata.ApiResponseInfo;
import com.gtp.dubbo.api.metadata.ApiParamInfo;
import com.gtp.dubbo.api.params.ParameterBinder;
import com.gtp.dubbo.api.params.support.DefaultParameterBinder;

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

	private ParameterBinder parameterBinder;/* 参数绑定 */

	@Override
	public ApiResponseInfo doService(ApiRequestInfo request) {

		try {

			// 获得方法
			String app = request.getApp();
			String service = request.getService();
			String method = request.getMethod();

			ApiMethodInfo m = ApiManager.get(app, service, method);
			if (m == null) {
				return ApiResponseInfo.failure(ApiErrorCode.METHOD_ERROR);
			}

			// 方法参数为空
			List<ApiParamInfo> pts = m.getInParams();
			if (pts == null || pts.isEmpty()) {
				return doExecute(m.getMethod(), m.getInstance(), null);
			}

			// 方法参数不为空，但是输入为空
			JSONObject args = JSONObject.parseObject(request.getContent());
			if (args == null || args.isEmpty()) {
				return ApiResponseInfo.failure(ApiErrorCode.PARAM_ERROR);
			}

			// 跳过参数绑定
			ApiMethod apiMethod = m.getMethod().getAnnotation(ApiMethod.class);
			if (apiMethod.skipBP()) {
				return doExecute(m.getMethod(), m.getInstance(), new Object[] { args });
			}

			Object[] paramValues = parameterBinder.getParamValues(m.getMethod(), pts, args);
			return doExecute(m.getMethod(), m.getInstance(), paramValues);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return ApiResponseInfo.failure(ApiErrorCode.SYSTEM_ERROR);
	}

	private ApiResponseInfo doExecute(Method method, Object instance, Object[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
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
		if (parameterBinder == null) {
			parameterBinder = new DefaultParameterBinder();
		}
	}

	@PreDestroy
	public void destroy() {
		System.out.println("在这里销毁容器");
	}

	public ParameterBinder getParameterBinder() {
		return parameterBinder;
	}

	public void setParameterBinder(ParameterBinder parameterBinder) {
		this.parameterBinder = parameterBinder;
	}
}
