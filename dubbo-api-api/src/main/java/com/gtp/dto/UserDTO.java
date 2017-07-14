package com.gtp.dto;

import java.io.Serializable;

import com.gtp.dubbo.api.annotation.ApiDescribe;

@ApiDescribe("测试DTO")
public class UserDTO implements Serializable{
	
	private static final long serialVersionUID = 7946714801246531977L;

	/**
	 * 我是测试DTO的id
	 */
	@ApiDescribe("DTO的id")
	public int id;
	
	/**
	 * 我是测试DTO的name
	 */
	@ApiDescribe("DTO的name")
	public String name;
}
