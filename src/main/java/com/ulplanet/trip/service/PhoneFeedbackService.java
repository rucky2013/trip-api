package com.ulplanet.trip.service;


import com.ulplanet.trip.bean.PhoneFeedback;

import java.util.List;
import java.util.Map;

/**
 * Created by makun on 2015/12/15.
 */
public interface PhoneFeedbackService {
    Map<String,Object> getPhoneFunctions();
    Map<String,Object> savePhoneFeedback(List<PhoneFeedback> phoneFeedback);
}
