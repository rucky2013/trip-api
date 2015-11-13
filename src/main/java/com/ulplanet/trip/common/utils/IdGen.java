package com.ulplanet.trip.common.utils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 
 * 封装各种生成唯一性ID算法的工具类.
 * @author zhangxd
 * @date 2015年7月12日
 *
 */
public class IdGen {

	private static SecureRandom random = new SecureRandom();

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
	
	/**
	 * 生成一个随机码,长度36.
	 * @return
	 * 
	 */
	public static String randomString() {
		return randomStringByUUID();
	}
	
	/**
	 * 生成一个随机码,长度36,全大写.
	 * @return
	 * 
	 */
	public static String randomStringUpper() {
		return randomString().toUpperCase();
	}
	
	/**
	 * 使用java5的UUID类,返回一个随机码,长度36.
	 * 
	 * @return 一个随机码,形如:5ec24ed3-ff1a-41c1-8d23-a37af006bbb3
	 * 
	 */
	private static String randomStringByUUID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}
	
	public static void main(String[] args) {
		System.out.println(IdGen.randomStringUpper());
		System.out.println(IdGen.randomStringUpper().length());
//		for (int i=0; i<1000; i++){
//			System.out.println(IdGen.randomLong() + "  " + IdGen.randomBase62(5));
//		}
	}

}
