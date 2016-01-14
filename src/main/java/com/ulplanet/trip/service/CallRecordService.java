package com.ulplanet.trip.service;

import com.ulplanet.trip.bean.CallRecord;

import java.util.Map;

/**
 * Created by makun on 2016/1/14.
 */
public interface CallRecordService {
    Map<String,Object> addRecord(CallRecord callRecord);
}
