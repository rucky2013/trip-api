package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.api.location.GeocodeService;
import com.ulplanet.trip.bean.User;
import com.ulplanet.trip.common.utils.JedisUtils;
import com.ulplanet.trip.common.utils.StringHelper;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.UserDao;
import com.ulplanet.trip.service.PositionService;
import com.ulplanet.trip.util.LocalContext;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("positionService")
public class PositionServiceImpl implements PositionService {

    @Autowired
    UserDao userDao;

    @Override
    public Map<String, Object> putPoint(HttpServletRequest request, double longitude,
                                        double latitude) {
        String groupid = LocalContext.getGroupId();
        String userid = LocalContext.getUserId();
        Map<String, Object> userMap = JedisUtils.getObjectMap(groupid);
        if (userMap == null) {
            userMap = new HashMap<>();
        }


        this.updateCountryAndCity(request, longitude, latitude);

        Map<String, Object> pointMap = new HashMap<>();
        pointMap.put("longitude", longitude);
        pointMap.put("latitude", latitude);
        userMap.put(userid, pointMap);

        JedisUtils.setObjectMap(groupid, userMap, 60 * 60 * 24 * 10);

        Map<String, String> geocodeMap = new HashMap<>();
        geocodeMap.put("country", LocalContext.getCountry());
        geocodeMap.put("city", LocalContext.getCity());

        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, geocodeMap);
        return result;
    }

    private void updateCountryAndCity(HttpServletRequest request, double longitude, double latitude) {
        User user = LocalContext.getUser();
        long lastUpdate = user.getLastUpdate();
        long now = new Date().getTime();
        //每半小时更新城市
        if ((now - lastUpdate) < 1000 * 60 * 30) {
            return;
        }
        GeocodeService.Geocode geocode = GeocodeService.get(longitude, latitude);
        if (geocode != null) {
            String country = geocode.getCountry();
            String city = geocode.getCity();
            if (StringHelper.isNotEmpty(country) && StringHelper.isNotEmpty(city)) {
                String token = request.getHeader(Constants.HEADER_TOKEN);
                user.setCurrentCountry(country);
                user.setCurrentCity(city);
                user.setLastUpdate(now);
                JedisUtils.set(token, new Gson().toJson(user), 60 * 60 * 24 * 10);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getPoint() {
        String groupid = LocalContext.getGroupId();
        Map<String, Object> userMap = JedisUtils.getObjectMap(groupid);
        List<Map<String, Object>> datas = new ArrayList<>();

            List<Map<String, Object>> userList = this.userDao.findUsers(groupid);
        Map<String, Map<String, Object>> userKeyMap = new HashMap<>();
        for (Map<String, Object> map : userList) {
            String userid = Objects.toString(map.get("id"), "");
            userKeyMap.put(userid, map);
        }

        if (userMap != null) {
            for (Map.Entry<String, Object> userMapEntry : userMap.entrySet()) {
                String userid = userMapEntry.getKey();

                if (LocalContext.getUserId().equals(userid)) {
                    continue;
                }
                Map<String, Object> userDataMap = userKeyMap.get(userid);
//                if("0".equals(String.valueOf(userDataMap.get("position_flag"))) && "1".equals(type))continue;
                if (userDataMap != null) {
                    Map<String, Object> pointMap = (Map<String, Object>) userMapEntry.getValue();
                    pointMap.put("userid", userid);
                    pointMap.put("name", userDataMap.get("name"));
                    pointMap.put("type", userDataMap.get("type"));
                    pointMap.put("gender", userDataMap.get("gender"));
                    pointMap.put("positionFlag", userDataMap.get("position_flag"));
                    pointMap.put("photo", userDataMap.get("photo")); 
                    datas.add(pointMap);
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, datas);
        return result;
    }
}
