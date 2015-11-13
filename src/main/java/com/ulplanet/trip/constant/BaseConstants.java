package com.ulplanet.trip.constant;

/**
 * 
 * 基础常量
 * @author zhangxd
 * @date 2015年7月15日
 *
 */
public class BaseConstants {

	/** 用户Token */
	public static final String HEADER_TOKEN 				= "req-token";
	/** 客户端类型 */
	public static final String HEADER_CLIENT_TYPE 			= "req-client-type";
	/** 设备码 */
	public static final String HEADER_IMEI 					= "req-imei";
	/** 请求是否加密 */
	public static final String HEADER_IS_SECURE 			= "req-is-secure";
	
	/** 客户端类型：IPHONE */
	public static final String CLIENT_TYPE_IPHONE			= "1";
	/** 客户端类型：ANDORID */
	public static final String CLIENT_TYPE_ANDROID			= "2";
	
	/** 请求是否加密：已加密 */
	public static final String IS_SECURE_YES				= "1";
	/** 请求是否加密：未加密 */
	public static final String IS_SECURE_NO					= "0";
	
	/** 状态 */
	public static final String RETURN_FIELD_STATUS			= "status";
	/** 错误信息 */
	public static final String RETURN_FIELD_MESSAGE			= "msg";
	/** 当前页码 */
	public static final String RETURN_FIELD_PAGE			= "page";
	/** 每页数据条数 */
	public static final String RETURN_FIELD_SIZE			= "size";
	/** 数据总条数 */
	public static final String RETURN_FIELD_TOTAL			= "total";
	/** 返回数据 */
	public static final String RETURN_FIELD_DATA			= "data";
	
	/** 操作成功 */
	public static final String STATUS_SUCCESS				= "1";
	/** 操作失败 */
	public static final String STATUS_FAILURE				= "2";
	/** 权限过期 */
	public static final String STATUS_INVALID				= "3";
	
}
