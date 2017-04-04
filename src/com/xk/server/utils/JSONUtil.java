package com.xk.server.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JSONUtil {
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 
	 * 用途：获取集合类型
	 * @date 2016年9月29日
	 * @param collectionClass
	 * @param elementClasses
	 * @return
	 */
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
    } 
	
	public static JavaType getType(Class clazz) {
		JavaType javaType = mapper.getTypeFactory().constructParametricType(
				List.class, new Class[] { clazz });
		return javaType;
	}

	public static Map<String, Object> fromJson(String json) {
		if (json == null) {
			return Collections.emptyMap();
		}

		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		try {
			Map<String, Object> jsonMap =  mapper.readValue(json, Map.class);
			return jsonMap;
		} catch (Exception e) {
		}
		return Collections.emptyMap();
	}

	public static String toJosn(Object obj) {
		//解析器支持解析单引号
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES,true);
		//解析器支持解析结束符
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);
		try {
			String result = mapper.writeValueAsString(obj);
			return result;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static <T> T toBean(String json, JavaType javatype) {
		if (json == null) {
			return null;
		}

		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		try {
			return mapper.readValue(json, javatype);
		} catch (Exception e) {
		}
		return null;
	}

	public static <T> T toBean(String json, Class<T> clazz) {
		if (json == null) {
			return null;
		}

		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}