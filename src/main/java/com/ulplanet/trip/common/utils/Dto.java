package com.ulplanet.trip.common.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * Map接口的扩展.
 * @author zhangxd
 * @date 2015年7月21日
 *
 */
public interface Dto extends Map<String, Object> {
	
	/**
	 * 通过键，返回一个字符串的值.
	 * @param key
	 * @return
	 */
	String getString(String key);
	
	/**
	 * 通过键，返回一个数组类型的对象.
	 * @param key
	 * @return
	 */
	<E> List<E> getList(String key);
	
	/**
	 * 通过键，返回一个整数值.
	 * @param key
	 * @return
	 */
	int getInt(String key);
	
	/**
	 * 通过键，返回一个long值.
	 * @param key
	 * @return
	 */
	long getLong(String key);
	
	/**
	 * 通过键，返回一个浮点型数值.
	 * @param key
	 * @return
	 */
	double getDouble(String key);
	
	/**
	 * 通过键，返回一个BigDecimal的值.
	 * @param key
	 * @return
	 */
	BigDecimal getDecimal(String key);
	
	/**
	 * 通过键，返回一个日期型的值.
	 * @param key
	 * @return
	 */
	Date getDate(String key);
	
}