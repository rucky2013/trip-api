package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.bean.CallRecord;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.CallRecordDao;
import com.ulplanet.trip.service.CallRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by makun on 2016/1/14.
 */
@Service("callRecordService")
public class CallRecordServiceImpl implements CallRecordService {

    @Resource
    private CallRecordDao callRecordDao;

    @Override
    public Map<String, Object> addRecord(CallRecord callRecord) {
        Map<String, Object> result = new HashMap<>();
        String msg = callRecord.validator();
        if(msg.length() > 0){
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_FAILURE);
            result.put(Constants.RETURN_FIELD_MESSAGE, msg);
            return result;
        }
        callRecord.preInsert();
        callRecordDao.insert(callRecord);
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        return result;
    }
}
