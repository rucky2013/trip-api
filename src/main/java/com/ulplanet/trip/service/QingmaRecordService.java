package com.ulplanet.trip.service;

import com.ulplanet.trip.bean.QingmaRecord;

import java.util.Map;

/**
 * Created by makun on 2016/1/16.
 */
public interface QingmaRecordService {
    Map<String,Object> saveRecord(QingmaRecord qingmaRecord);
    String getPwd(QingmaRecord qingmaRecord);
}
