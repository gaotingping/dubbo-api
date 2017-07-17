package com.gtp.dubbo.api.utils;

public class CommonBiz {

	public static boolean isEmpty(String p) {
		
		if(p==null || "".equals(p)){
			return true;
		}
		
		return false;
	}

	public static boolean isEmpty(String ... ps) {
		
		if(ps==null){
			return true;
		}
		
		for(String p:ps){
			if(isEmpty(p)){
				return true;
			}
		}
		
		return false;
	}

}
