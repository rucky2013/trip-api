package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.bean.PhoneFeedback;
import com.ulplanet.trip.common.utils.IdGen;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.PhoneFeedbackDao;
import com.ulplanet.trip.service.PhoneFeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by makun on 2015/12/15.
 */
@Service
public class PhoneFeedbackServiceImpl implements PhoneFeedbackService {
    @Resource
    private PhoneFeedbackDao phoneFeedbackDao;
    @Override
    public Map<String, Object> getPhoneFunctions() {
        List<PhoneFeedback> list = phoneFeedbackDao.getPhoneFunction();
        Map<String,Object> map = new HashMap<>();
        map.put(Constants.RETURN_FIELD_STATUS,Constants.STATUS_SUCCESS);
        map.put(Constants.RETURN_FIELD_DATA,list);
        return map;
    }

    @Override
    public Map<String, Object> savePhoneFeedback(List<PhoneFeedback> list) {
        Map<String,Object> map = new HashMap<>();
        for(PhoneFeedback phoneFeedback : list) {
            phoneFeedback.setId(IdGen.uuid());
            phoneFeedback.setCreateDate(new Date());
        }
        phoneFeedbackDao.inserts(list);
        map.put(Constants.RETURN_FIELD_STATUS,Constants.STATUS_SUCCESS);
        return null;
    }
}
