package com.gtp.dubbo.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gtp.dubbo.api.executor.ServiceDispatcher;
import com.gtp.dubbo.api.metadata.ApiRequestInfo;
import com.gtp.dubbo.api.metadata.ApiResponseInfo;

@Controller
public class DispatcherController {

	@Autowired
	private ServiceDispatcher dispatcher;/* 服务转发器 */
	
	@RequestMapping(method = RequestMethod.POST, value = "/services",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String doService(@RequestBody String body,HttpServletRequest request,HttpServletResponse response) {
		
		// 允许跨域
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		try {
			// step1:参数解析
			ApiRequestInfo in = JSON.parseObject(body,ApiRequestInfo.class);

			// step2:执行
			ApiResponseInfo out = dispatcher.doService(in);

			// step3:结果序列化
			return  JSONObject.toJSONString(out,
					 	SerializerFeature.WriteMapNullValue,
						SerializerFeature.WriteNullNumberAsZero,
						SerializerFeature.WriteNullListAsEmpty,
						SerializerFeature.WriteNullStringAsEmpty,
						SerializerFeature.WriteNullBooleanAsFalse);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public ServiceDispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(ServiceDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
}
