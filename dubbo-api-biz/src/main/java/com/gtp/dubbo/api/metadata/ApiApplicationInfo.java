package com.gtp.dubbo.api.metadata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//参数封装
public class ApiApplicationInfo implements Serializable{

	private static final long serialVersionUID = 3985254444285973525L;

	private String name; /*名称*/
	
	private String protocol;/*协议*/
	
	private String address;/*地址*/
	
	private List<Class<?>> services;/*服务列表*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Class<?>> getServices() {
		return services;
	}

	public void setServices(List<Class<?>> services) {
		this.services = services;
	}

	public void addService(Class<?> c) {
		if(services==null){
			services=new ArrayList<>();
		}
		services.add(c);
	}
}
