package com.gtp.vo;

import java.io.Serializable;

import com.gtp.dubbo.api.annotation.ApiDescribe;

@ApiDescribe("地址")
public class AddressVO2 implements Serializable{

	private static final long serialVersionUID = -4914847236601624922L;

	@ApiDescribe("主键")
	public int id;
	
	@ApiDescribe("地址全名")
	public String name;
	
	@ApiDescribe("地址3")
	public AddressVO3 address3;
	
	@ApiDescribe("baseVO构成循环引用")
	public BaseVO baseVO;
}
