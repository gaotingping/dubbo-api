package com.gtp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * api文档
 * 
 * @author gaotingping@cyberzone.cn
 */
@Controller
@RequestMapping("/doc")
public class ApiDocController {
	
	//apiMethodInit
	//apiMenuInit
	
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
}