package com.ulplanet.trip.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LoginService {

    Map<String, Object> login(HttpServletRequest request, String userid,
                              String userpwd, double longitude, double latitude, String cphone);

}
