package com.ulplanet.trip.base;

import java.io.File;

/**
 * 
 * 提供了基于整个应用系统的上下文信息
 * @author zhangxd
 * @date 2015年7月12日
 *
 */
public class AppContext {

	private static String appPath;
	private static String appName;
	private static String dbType;
    private static String version;
    private static String title;
	
    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        AppContext.version = version;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        AppContext.title = title;
    }

    /**
	 * 获得当前系统运行的基本目录 
	 * 
	 * @return
	 */
	public static String getAppPath() {
		return appPath;
	}

	/**
	 * 获取当前应用程序运行的基准目录(WebApplication时候是当前web项目,javaApplication时候是当前eclipse项目目录).
	 * 
	 * @return
	 */
	public static File getAppFolder() {
		return new File(appPath);
	}

	/**
	 * 设置当前系统运行目录 
	 * 
	 * @param path
	 */
	public static void setAppPath(String path) {
		appPath = path;
	}

	public static String getAppName() {
		return appName;
	}

	public static void setAppName(String appName) {
		AppContext.appName = appName;
	}

	public static String getDbType() {
		return dbType;
	}

	public static void setDbType(String dbTpye) {
		dbType = dbTpye;
	}
}