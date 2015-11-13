package com.ulplanet.trip.util;

import com.ulplanet.trip.bean.User;
import com.ulplanet.trip.common.utils.LocalHelper;

public class LocalContext {

    private static final ThreadLocal<User> user = LocalHelper.registStatic(new ThreadLocal<>());


    public static void setUser(User user) {
        LocalContext.user.set(user);
    }

    public static User getUser() {
        return user.get();
    }

    public static String getGroupId() {
        User user = getUser();
        if (user != null) {
            return user.getGroup();
        }
        return "";
    }

    public static String getUserId() {
        User user = getUser();
        if (user != null) {
            return user.getId();
        }
        return "";
    }

    public static String getCountry() {
        User user = getUser();
        if (user != null) {
            return user.getCurrentCountry();
        }
        return "";
    }

    public static String getCity() {
        User user = getUser();
        if (user != null) {
            return user.getCurrentCity();
        }
        return "";
    }

    public static String getPassport() {
        User user = getUser();
        if (user != null) {
            return user.getPassport();
        }
        return "";
    }
}
