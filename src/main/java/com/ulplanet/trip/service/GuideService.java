package com.ulplanet.trip.service;

import java.util.Map;


public interface GuideService {

    Map<String, Object> findList(int page, int size);

    Map<String, Object> findDetail(String id);

}
