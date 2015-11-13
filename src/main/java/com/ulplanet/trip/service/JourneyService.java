package com.ulplanet.trip.service;

import java.util.Map;


public interface JourneyService {

    Map<String, Object> findAdvice();

    Map<String, Object> findJourneyList(int type, String stype, String order,
                                        double longitude, double latitude);

    Map<String, Object> findFood(String id, double longitude, double latitude);

    Map<String, Object> findScenic(String id, double longitude, double latitude);

    Map<String, Object> findShop(String id, double longitude, double latitude);

}
