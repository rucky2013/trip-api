package com.ulplanet.trip.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public interface PositionService {

    Map<String, Object> savePoint(HttpServletRequest request, double longitude, double latitude, Long time);

    Map<String, Object> getPoint(HttpServletRequest request, double longitude, double latitude, Long time);
}
