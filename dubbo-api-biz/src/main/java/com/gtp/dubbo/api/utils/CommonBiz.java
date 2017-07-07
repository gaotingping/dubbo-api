package com.gtp.dubbo.api.utils;

public class CommonBiz {

	public static boolean isEmpty(String p) {
		
		if(p==null || "".equals(p)){
			return true;
		}
		
		return false;
	}

}
