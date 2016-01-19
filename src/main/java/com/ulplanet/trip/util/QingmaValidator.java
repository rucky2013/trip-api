package com.ulplanet.trip.util;


import com.ulplanet.trip.common.utils.DateHelper;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by makun on 2016/1/19.
 */
public class QingmaValidator {
    private final static String ACCOUNT_SID = "d61d307dc4e2436cb5f3e7412f566f81";
    private final static String AUTH_TOKEN = "c5d4a6be89ab439a92d363c896c6caad";
    private final static long TIME_DIFFERENCE = 600000;

    public static String validator(String timestamp,String sig){
        Long time;
        try {
            time = DateHelper.parseDate(timestamp, "yyyyMMddHHmmss").getTime();
            long now = new Date().getTime();
            if(now - time > TIME_DIFFERENCE){
                return "00005";
            }
            String key = ACCOUNT_SID + AUTH_TOKEN + timestamp;
            String _sig = DigestUtils.md5Hex(key);
            if(!_sig.equals(sig)){
                return "00006";
            }
            return "00000";
        } catch (ParseException e) {
            e.printStackTrace();
            return "00000";
        }

    }
    public static void main(String[] args) throws ParseException {
        String timestamp = "20160119163610";
        long time = DateHelper.parseDate(timestamp, "yyyyMMddHHmmss").getTime();
        String key = ACCOUNT_SID + AUTH_TOKEN + timestamp;
        System.out.print(DigestUtils.md5Hex(key));
    }
}
