package com.ulplanet.trip.util;

public interface LoginConstants {

    LoginException USER_NOT_EXISTS = new LoginException("用户名不存在。");

    LoginException USER_INVALID_PASSWORD = new LoginException("密码无效。");

    LoginException USER_INVALID_DEVICE = new LoginException("设备无效。");

    LoginException LOGIN_ERROR = new LoginException("登录异常。。");

    void m1();
}
