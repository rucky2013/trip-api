package com.ulplanet.trip.service;

import java.util.Map;

public interface InfoService {

    Map<String, Object> getEmergency();

    Map<String, Object> getCar();

    Map<String, Object> getInit();

    Map<String, Object> getWeather(double longitude, double latitude);

}
