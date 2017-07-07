package com.gtp.dubbo.api.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gtp.dubbo.api.annotation.ApiDescribe;
import com.gtp.dubbo.api.annotation.ApiParam;
import com.gtp.dubbo.api.metadata.ApiParamInfo;

/**
 * 反射工具
 * 
 * @author gaotingping@cyberzone.cn
 */
public class ReflectUtils {

	public static JSONObject allFields(Class<?> c) {
		JSONObject result = new JSONObject();
		if (isBaseType(c)) {
			ApiDescribe desc = c.getAnnotation(ApiDescribe.class);
			JSONObject tmp1 = new JSONObject();
			tmp1.put("type", c.getSimpleName());
			tmp1.put("fields", null);
			if (desc != null) {
				tmp1.put("desc", desc.value());
			} else {
				tmp1.put("desc", "");
			}
			result.put(c.getSimpleName(), tmp1);
		} else {
			do {
				Field[] fs = c.getDeclaredFields();
				for (Field f : fs) {
					HashMap<String, String> circuleRef = new HashMap<String, String>();
					circuleRef.put(c.getName(), null);
					ApiDescribe desc = f.getAnnotation(ApiDescribe.class);
					if (desc == null) {/*忽略未注解字段*/
						continue;
					}
					if (isBaseType(f.getType())) { /* 基本类型 */
						result.put(f.getName(), desc.value());
						JSONObject tmp2 = new JSONObject();
						tmp2.put("type", f.getType().getSimpleName());
						tmp2.put("fields", null);
						tmp2.put("desc", desc.value());
						result.put(f.getName(), tmp2);
					} else if (f.getType() == List.class) {/* list集合 */
						Type type = f.getGenericType();
						if (type instanceof ParameterizedType) {
							Type[] actualTypes = ((ParameterizedType) type).getActualTypeArguments();
							Class<?> tmpC = (Class<?>) actualTypes[0];
							if (circuleRef.containsKey(tmpC.getName())) { /* 循环引用 */
								JSONObject tmp3 = new JSONObject();
								tmp3.put("type", f.getType().getSimpleName());
								tmp3.put("fields", "$ref_" + tmpC.getName());
								tmp3.put("desc", desc.value());
								result.put(f.getName(), tmp3);
							} else {
								JSONArray data = new JSONArray();
								data.add(innerAllFields(tmpC, circuleRef));
								JSONObject tmp4 = new JSONObject();
								tmp4.put("type", f.getType().getSimpleName());
								tmp4.put("fields", data);
								tmp4.put("desc", desc.value());
								result.put(f.getName(), tmp4);
								circuleRef.remove(tmpC.getName());/* 分支迭代完删除 */
							}
						}
					} else { /* 自定义bean */
						if (circuleRef.containsKey(f.getType().getName())) { /* 循环引用 */
							JSONObject tmp5 = new JSONObject();
							tmp5.put("type", f.getType().getSimpleName());
							tmp5.put("fields", "$ref_" + f.getType().getName());
							tmp5.put("desc", desc.value());
							result.put(f.getName(), tmp5);
						} else {
							JSONObject tmp6 = new JSONObject();
							tmp6.put("type", f.getType().getSimpleName());
							tmp6.put("fields", innerAllFields(f.getType(), circuleRef));
							tmp6.put("desc", desc.value());
							result.put(f.getName(), tmp6);
							circuleRef.remove(f.getType().getName());/* 分支迭代完删除 */
						}
					}
				}
				c = c.getSuperclass();
			} while (c != null && c != Object.class);
		}

		return result;
	}

	private static JSONObject innerAllFields(Class<?> c, HashMap<String, String> circuleRef) {
		JSONObject result = new JSONObject();
		circuleRef.put(c.getName(), null);
		do {
			Field[] fs = c.getDeclaredFields();
			for (Field f : fs) {
				ApiDescribe desc = f.getAnnotation(ApiDescribe.class);
				if (desc == null) {/*忽略未注解字段*/
					continue;
				}
				if (isBaseType(f.getType())) { /* 基本类型 */
					JSONObject tmp1 = new JSONObject();
					tmp1.put("type", f.getType().getSimpleName());
					tmp1.put("fields", null);
					tmp1.put("desc", desc.value());
					result.put(f.getName(), tmp1);
				} else if (f.getType() == List.class) {/* list集合 */
					Type type = f.getGenericType();
					if (type instanceof ParameterizedType) {
						Type[] actualTypes = ((ParameterizedType) type).getActualTypeArguments();
						Class<?> tmpC = (Class<?>) actualTypes[0];
						JSONArray data = new JSONArray();
						data.add(innerAllFields(tmpC, circuleRef));
						JSONObject tmp2 = new JSONObject();
						tmp2.put("type", f.getType().getSimpleName());
						tmp2.put("fields", data);
						tmp2.put("desc", desc.value());
						result.put(f.getName(), tmp2);
						circuleRef.remove(tmpC.getName());/* 分支迭代完删除 */
					}
				} else { /* 自定义bean */
					if (circuleRef.containsKey(f.getType().getName())) { /* 循环引用 */
						JSONObject tmp3 = new JSONObject();
						tmp3.put("type", f.getType().getSimpleName());
						tmp3.put("fields", "$ref_" + f.getType().getName());
						tmp3.put("desc", desc.value());
						result.put(f.getName(), tmp3);
					} else {
						JSONObject tmp4 = new JSONObject();
						tmp4.put("type", f.getType().getSimpleName());
						tmp4.put("fields", innerAllFields(f.getType(), circuleRef));
						tmp4.put("desc", desc.value());
						result.put(f.getName(), tmp4);
					}
					circuleRef.remove(f.getType().getName());/* 分支迭代完删除 */
				}
			}
			c = c.getSuperclass();
		} while (c != null && c != Object.class);

		return result;
	}

	public static boolean isBaseType(Class<?> c) {
		if (c.isPrimitive()) {
			return true;
		} else if (c == String.class) {
			return true;
		} else if (c == Integer.class) {
			return true;
		} else if (c == Long.class) {
			return true;
		} else if (c == Double.class) {
			return true;
		} else if (c == Float.class) {
			return true;
		} else if (c == Boolean.class) {
			return true;
		}else if (c == Short.class) {
			return true;
		}else if (c == Byte.class) {
			return true;
		}else if (c == Character.class) {
			return true;
		}
		return false;
	}

public static List<ApiParamInfo> getParameterInfo(Method m) {
		
		Class<?>[] p1 = m.getParameterTypes();
		if(p1==null || p1.length<1){
			return null;
		}
		
		Type[] p11 = m.getGenericParameterTypes();
		
		//bug fix:有参数但是没有注解,可能是跳过自动注解的
		Annotation[][] p2 = m.getParameterAnnotations();
//		if(p2== null || p2.length<1){
//			return null;
//		}
		
		List<ApiParamInfo> list=new ArrayList<>();
		
		for(int i=0;i<p1.length;i++){
			
			ApiParamInfo tmp=new ApiParamInfo();
			
			Class<?> c = p1[i];
			if(p1[i] == List.class){
				c=getClassByType(p11[i]);
				tmp.setIsList(true);
			}
			
			ApiParam apiParam=null;
			for(Annotation a:p2[i]){
				if(a instanceof ApiParam){
					apiParam=(ApiParam)a;
					break;
				}
			}
			tmp.setType(c);
			tmp.setApiParam(apiParam);
			
			list.add(tmp);
		}
		
		return list;
	}

	private static Class<?> getClassByType(Type t) {
		
		if(t instanceof ParameterizedType){
			Type[] actualTypes = ((ParameterizedType) t).getActualTypeArguments();
			Class<?> tmpC = (Class<?>) actualTypes[0];
			return tmpC;
		}
		
		return null;
	}
}
