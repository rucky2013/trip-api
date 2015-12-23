package com.ulplanet.trip.common.utils;

import java.util.Objects;

/**
 * 
 * 得到当前应用的系统路径
 * @author zhangxd
 * @date 2015年7月12日
 *
 */
public class SystemPath {

	public static String getSysPath() {
		String path = Objects.toString(Thread.currentThread().getContextClassLoader().getResource(""));
		String temp = path.replaceFirst("file:/", "").replaceFirst(
				"WEB-INF/classes/", "");
		String separator = System.getProperty("file.separator");
        return temp.replaceAll("/", separator + separator);
	}

	public static String getClassPath() {
		String path = Objects.toString(Thread.currentThread().getContextClassLoader().getResource(""));
		String temp = path.replaceFirst("file:/", "");
		String separator = System.getProperty("file.separator");
        return temp.replaceAll("/", separator + separator);
	}

	public static String getSystempPath() {
		return System.getProperty("java.io.tmpdir");
	}

	public static String getSeparator() {
		return System.getProperty("file.separator");
	}

	public static void main(String[] args) {
		System.out.println(getSysPath());
		System.out.println(System.getProperty("java.io.tmpdir"));
		System.out.println(getSeparator());
		System.out.println(getClassPath());
	}
}
