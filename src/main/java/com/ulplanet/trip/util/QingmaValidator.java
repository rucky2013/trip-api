package com.ulplanet.trip.util;


import com.ulplanet.trip.bean.QingmaRecord;
import com.ulplanet.trip.common.utils.DateHelper;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by makun on 2016/1/19.
 */
public class QingmaValidator {
    private final static long TIME_DIFFERENCE = 600000;

    public static String validator(QingmaRecord qingmaRecord){
        Long time;
        try {
            time = DateHelper.parseDate(qingmaRecord.getTimestamp(), "yyyyMMddHHmmss").getTime();
            long now = new Date().getTime();
            if(now - time > TIME_DIFFERENCE){
                return "00005";
            }
            String key = qingmaRecord.getClientNumber() + qingmaRecord.getClientPwd() + qingmaRecord.getTimestamp();
            String _sig = DigestUtils.md5Hex(key);
            if(!_sig.equals(qingmaRecord.getSig())){
                return "00006";
            }
            return "00000";
        } catch (ParseException e) {
            e.printStackTrace();
            return "00000";
        }

    }
    public static void main(String[] args) throws ParseException {
    }
}
