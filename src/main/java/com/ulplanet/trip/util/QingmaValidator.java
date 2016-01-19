package com.ulplanet.trip.util;

import java.util.Date;

/**
 * Created by makun on 2016/1/19.
 */
public class QingmaValidator {
    private final static String ACCOUNT_SID = "d61d307dc4e2436cb5f3e7412f566f81";
    private final static String AUTH_TOKEN = "c5d4a6be89ab439a92d363c896c6caad";
    private final static long TIME_DIFFERENCE = 600000;

    public static String validator(String timestamp,String sig){
        Long time = Long.parseLong(timestamp);
        long now = new Date().getTime();
        if(now - time > TIME_DIFFERENCE){
            return "00005";
        }
        String key = ACCOUNT_SID + AUTH_TOKEN + timestamp;
        String _sig = SecurityUtils.MD5(key);
        if(!_sig.equals(sig)){
            return "00006";
        }
        return "00000";
    }
    public static void main(String[] args){
        long timestamp = new Date().getTime();
        String key = ACCOUNT_SID + AUTH_TOKEN + timestamp;
        System.out.print(validator(String.valueOf(timestamp),SecurityUtils.MD5(key)));
    }
}
