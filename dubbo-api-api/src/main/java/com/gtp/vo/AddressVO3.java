package com.gtp.vo;

import java.io.Serializable;

import com.gtp.dubbo.api.annotation.ApiDescribe;

@ApiDescribe("地址")
public class AddressVO3 implements Serializable{

	private static final long serialVersionUID = -4914847236601624922L;

	@ApiDescribe("主键")
	public int id;
	
	@ApiDescribe("地址全名")
	public String name;
	
	@ApiDescribe("引用关联1")
	public UserInfoVO user;
	
	@ApiDescribe("引用关联2")
	public AddressVO2 address2;
	
	@ApiDescribe("baseVO")
	public BaseVO baseVO;
}
