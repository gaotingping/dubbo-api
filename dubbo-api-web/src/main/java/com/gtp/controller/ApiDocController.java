package com.gtp.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gtp.dubbo.api.common.AppConfig;
import com.gtp.dubbo.api.core.ApiInit;
import com.gtp.dubbo.api.core.ApiManager;
import com.gtp.dubbo.api.metadata.ApiMethodInfo;
import com.gtp.dubbo.api.metadata.ApiParamInfo;
import com.gtp.dubbo.api.params.ParameterBinder;
import com.gtp.dubbo.api.params.support.DefaultParameterBinder;
import com.gtp.dubbo.api.utils.ReflectUtils;

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
		
		JSONArray data=new JSONArray();
		
		Map<String, Map<String, Map<String, ApiMethodInfo>>> map = ApiManager.getPool();
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			
			JSONObject j1=new JSONObject();
			String k1 = it.next();
			
			j1.put("name", k1);
			j1.put("open", true);
			
			Map<String, Map<String, ApiMethodInfo>> service = map.get(k1);
			if(service!=null){
				
				//service
				JSONArray c1=new JSONArray();
				
				Iterator<String> it2 = service.keySet().iterator();
				while(it2.hasNext()){
					
					String k2 = it2.next();
					
					JSONObject j2=new JSONObject();
					j2.put("name", k2);
					
					Map<String, ApiMethodInfo> method = service.get(k2);
					if(method!=null){
						JSONArray c2=new JSONArray();
						Iterator<String> it3 = method.keySet().iterator();
						while(it3.hasNext()){
							JSONObject j3=new JSONObject();
							j3.put("name", it3.next());
							j3.put("isM", true);
							j3.put("app", k1);
							j3.put("service", k2);
							c2.add(j3);
						}
						j2.put("children", c2);
					}
					c1.add(j2);
				}
				j1.put("children", c1);
			}
			data.add(j1);
		}
		return data.toJSONString();
	}
	
	@RequestMapping(value = "/method",produces = {"text/html; charset=UTF-8;charset=UTF-8" })
	@ResponseBody
	public String methodInfo(String app,String service,String method) {
		
		ApiMethodInfo m = ApiManager.get(app, service, method);
				
		JSONObject result=new JSONObject();
		
		result.put("app", app);
		result.put("service", service);
		result.put("method", method);
		result.put("request", getInput(m.getInParams()));
		result.put("response", getOutput(m.getOutParams()));
		
		//关闭重复对象引用检测
		return JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	public String getInput(List<ApiParamInfo> params){
		
		JSONObject args = new JSONObject();
		if(params!=null){
			for(ApiParamInfo p3:params){
				if(p3.getApiParam()!=null){
					if(p3.getIsList()){
						JSONArray tmpData=new JSONArray();
						tmpData.add(ReflectUtils.allFields(p3.getType()));
						args.put(p3.getApiParam().value(),tmpData);
					}else if(ReflectUtils.isBaseType(p3.getType())){
						JSONObject tmpData=new JSONObject();
						tmpData.put("type", p3.getType().getSimpleName());
						tmpData.put("desc", p3.getApiParam().desc());
						args.put(p3.getApiParam().value(),tmpData);
					}else{
						args.put(p3.getApiParam().value(), ReflectUtils.allFields(p3.getType()));
					}
				}
			}
		}
		
		return args.toJSONString();
	}
	
	public String getOutput(ApiParamInfo returnParams){

		if(returnParams.getIsList()){
			JSONObject returnJson = ReflectUtils.allFields(returnParams.getType());
			JSONArray returnData=new JSONArray();
			returnData.add(returnJson);
			return returnData.toJSONString();
		}else{
			JSONObject returnJson = ReflectUtils.allFields(returnParams.getType());
			return returnJson.toJSONString();
		}
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