package com.gtp.vo;

import java.io.Serializable;

import com.gtp.dubbo.api.annotation.ApiDescribe;

@ApiDescribe("公共父类")
public class BaseVO implements Serializable{
	
	private static final long serialVersionUID = 7641326560234573928L;
	
	@ApiDescribe("我是公共父类的属性pid")
	public int pid;
}
