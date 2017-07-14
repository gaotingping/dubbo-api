package com.gtp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gtp.dubbo.api.common.AppConfig;
import com.gtp.dubbo.api.core.ApiInit;
import com.gtp.dubbo.api.core.ApiManager;
import com.gtp.dubbo.api.params.ParameterBinder;
import com.gtp.dubbo.api.params.support.DefaultParameterBinder;

/**
 * api文档
 * 
 * @author gaotingping@cyberzone.cn
 */
@Controller
@RequestMapping("/doc")
public class ApiDocController {
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private ParameterBinder parameterBinder;
	
	@RequestMapping(value = "/menu",produces = {"text/html; charset=UTF-8;charset=UTF-8" })
	@ResponseBody
	public String menuInfo() {
		return "OK";
	}
	
	@RequestMapping(value = "/method",produces = {"text/html; charset=UTF-8;charset=UTF-8" })
	@ResponseBody
	public String methodInfo() {
		
		JSONObject tmp=new JSONObject();
		tmp.put("ok", "123");
		
		JSONObject m=new JSONObject();
		
		m.put("app", "应用标识");
		m.put("service", "服务标识");
		m.put("method", "方法标识");
		m.put("request", tmp);
		m.put("response", tmp);
		
		//关闭重复对象引用检测
		return JSON.toJSONString(m,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	@RequestMapping(value = "/all",produces = {"text/html; charset=UTF-8;charset=UTF-8" })
	@ResponseBody
	public String showAll() {
		
		try {
			if (parameterBinder == null) {
				parameterBinder = new DefaultParameterBinder();
			}
			//刷新
			ApiInit.refresh(parameterBinder, appConfig);
			
			//刷出
			System.out.println(ApiManager.getPool());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "OK";
	}
}