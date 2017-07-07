package com.gtp.vo;

import java.io.Serializable;

import com.gtp.dubbo.api.annotation.ApiDescribe;

@ApiDescribe("地址")
public class AddressVO implements Serializable{

	private static final long serialVersionUID = -4914847236601624922L;

	@ApiDescribe("主键")
	public int id;
	
	@ApiDescribe("地址全名")
	public String name;
	
	@ApiDescribe("地址2")
	public AddressVO2 address2;
}
