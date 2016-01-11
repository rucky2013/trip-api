package com.ulplanet.trip.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ulplanet.trip.bean.PhoneFeedback;
import com.ulplanet.trip.common.utils.IdGen;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.PhoneFeedbackDao;
import com.ulplanet.trip.service.PhoneFeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
    public Map<String, Object> getPhoneFunctions(String code) {
        List<PhoneFeedback> list = phoneFeedbackDao.getPhoneFunction(code);
        Map<String,Object> map = new HashMap<>();
        map.put(Constants.RETURN_FIELD_STATUS,Constants.STATUS_SUCCESS);
        map.put(Constants.RETURN_FIELD_DATA,list);
        return map;
    }

    @Override
    public Map<String, Object> savePhoneFeedback(String json,String userCode) {
        List<PhoneFeedback> list;
        Map<String,Object> map = new HashMap<>();
        try {
            json = URLDecoder.decode(json,"UTF-8");
            list = JSON.parseArray(json,PhoneFeedback.class);

            for(PhoneFeedback phoneFeedback : list) {
                phoneFeedback.setUserCode(userCode);
                phoneFeedback.setCreateDate(new Date());
            }
            phoneFeedbackDao.inserts(list);
            map.put(Constants.RETURN_FIELD_STATUS,Constants.STATUS_SUCCESS);
            return map;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put(Constants.RETURN_FIELD_STATUS,Constants.STATUS_FAILURE);
        return map;

    }
}
