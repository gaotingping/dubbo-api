package com.gtp.service;

import com.gtp.dto.UserDTO;
import com.gtp.dubbo.api.annotation.ApiMethod;
import com.gtp.dubbo.api.annotation.ApiParam;
import com.gtp.dubbo.api.annotation.ApiService;
import com.gtp.vo.AddressVO;
import com.gtp.vo.UserInfoVO;

@ApiService(value="test1",desc="dubbo服务")
public interface IService {
	
	@ApiMethod(value="001",desc="测试方法")
	public UserInfoVO test1(@ApiParam(value="id",desc="基本类型") String id,
							@ApiParam(value="b",desc="自定义bean") UserDTO userDTO);
	
	@ApiMethod(value="002",desc="测试方法")
	public UserInfoVO test2(@ApiParam(value="id",desc="基本类型") String id,
							@ApiParam(value="b",desc="自定义bean") UserDTO userDTO);
	
	@ApiMethod(value="003",desc="测试方法")
	public AddressVO test3(@ApiParam(value="id",desc="基本类型") String id);
}
