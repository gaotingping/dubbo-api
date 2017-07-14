package com.gtp.dubbo.api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//md5
public class Md5Utils {
	
	private static final Logger logger = LoggerFactory.getLogger(Md5Utils.class);
	
	/**
	 * #FIXME 大文件的时候，算md5页费劲
	 * 文件md5
	 * 
	 * @param inFile
	 * @return
	 */
	public static String md5File(String inFile) {
		FileInputStream fis = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(inFile);
			byte[] buffer = new byte[4096];// 4kb
			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				md5.update(buffer, 0, length);
			}
			return bytes2Hex(md5.digest());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return null;
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				logger.error(ex.getMessage(),ex);
			}
		}
	}
	
	public static String bytes2Hex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for(byte b:bytes){
			int i = b & 0xFF;
			if(i<=0xF){
				sb.append("0");
			}
			sb.append(Integer.toHexString(i));
		}
		return sb.toString();
	}
	
	public static String md5(String txt){
		return null;
	}
}
