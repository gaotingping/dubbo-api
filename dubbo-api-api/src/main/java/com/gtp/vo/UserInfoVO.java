package com.gtp.vo;

import java.util.List;

import com.gtp.dubbo.api.annotation.ApiDescribe;

/**
 * 模拟一般的VO
 * 1.继承
 * 2.循环引用
 * 3.引用其它VO
 * 
 * @author gaotingping@cyberzone.cn
 */
@ApiDescribe("用户信息")
public class UserInfoVO extends BaseVO{

	private static final long serialVersionUID = 5688982073211870236L;

	@ApiDescribe("主键")
	public int id;
	
	@ApiDescribe("用户名")
	public String name;
	
	@ApiDescribe("用户信息自关联")
	public UserInfoVO user;
	
	@ApiDescribe("地址")
	public AddressVO address;
	
	@ApiDescribe("多个地址")
	public List<AddressVO> list;
}
