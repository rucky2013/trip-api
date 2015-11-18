package com.ulplanet.trip.service;

import java.util.Map;

/**
 * Created by Administrator on 2015/9/22.
 */
public interface JourneyPlanService {
    Map<String,Object> findList(String tag);
    Map<String,Object> getInfo(int type, String info, double longitude, double latitude);
}
