package com.gtp.service.impl;

import com.gtp.dto.UserDTO;
import com.gtp.service.IService2;
import com.gtp.vo.AddressVO;
import com.gtp.vo.UserInfoVO;

public class IServerImpl2 implements IService2{

	@Override
	public UserInfoVO test1(String id, UserDTO userDTO) {
		
		System.out.println("id="+id);
		System.out.println("dto="+userDTO);
		
		UserInfoVO vo=new UserInfoVO();
		
		vo.id=123;
		vo.pid=456;
		vo.name="name1";
		
		return vo;
	}
	
	@Override
	public UserInfoVO test2(String id, UserDTO userDTO) {
		
		System.out.println("id=2="+id);
		System.out.println("dto=2="+userDTO);
		
		UserInfoVO vo=new UserInfoVO();
		
		vo.id=123;
		vo.pid=456;
		vo.name="name2";
		
		return vo;
	}
	
	@Override
	public AddressVO test3(String id) {
		System.out.println("id=3="+id);
		AddressVO v=new AddressVO();
		v.id=1;
		v.name="name3";
		return v;
	}
}
