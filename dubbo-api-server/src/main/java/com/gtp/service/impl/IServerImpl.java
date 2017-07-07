package com.gtp.service.impl;

import com.gtp.dto.UserDTO;
import com.gtp.service.IService;
import com.gtp.vo.UserInfoVO;

public class IServerImpl implements IService{

	@Override
	public UserInfoVO test1(String id, UserDTO userDTO) {
		
		System.out.println("id="+id);
		System.out.println("dto="+userDTO);
		
		UserInfoVO vo=new UserInfoVO();
		
		vo.id=123;
		vo.pid=456;
		vo.name="name";
		
		return vo;
	}
}
