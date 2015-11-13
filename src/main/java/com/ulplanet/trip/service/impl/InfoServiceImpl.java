package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.api.weather.bean.Temperature;
import com.ulplanet.trip.api.weather.bean.Weather;
import com.ulplanet.trip.api.weather.bean.WeatherInfo;
import com.ulplanet.trip.api.weather.service.WeatherGetter;
import com.ulplanet.trip.common.persistence.Parameter;
import com.ulplanet.trip.common.utils.NumberHelper;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.InfoDao;
import com.ulplanet.trip.service.InfoService;
import com.ulplanet.trip.util.LocalContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("infoService")
public class InfoServiceImpl implements InfoService {

    @Autowired
    private InfoDao infoDao;

    @Override
    public Map<String, Object> getEmergency() {

        String country = LocalContext.getCountry();
        Parameter parameter = new Parameter(new Object[][]{
                {"country", country}
        });

        Map<String, Object> emergencyInfo = this.infoDao.findEmergency(parameter);
        if (emergencyInfo != null) {

            emergencyInfo.put("city", LocalContext.getCity());

            String group = LocalContext.getGroupId();
            Parameter groupParameter = new Parameter(new Object[][]{
                    {"group", group}
            });

            List<Map<String, Object>> touristList = this.infoDao.findTourists(groupParameter);
            String emergency = "";
            if (touristList != null && touristList.size() > 0) {
                emergency = Objects.toString(touristList.get(0).get("phone"));
            }

            emergencyInfo.put("emergency", emergency);
        } else {
            emergencyInfo = new HashMap<>();
        }

        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, emergencyInfo);
        return result;
    }

    @Override
    public Map<String, Object> getCar() {

        Map<String, String> keyMap = new HashMap<>();
        keyMap.put("1", "taxi");
        keyMap.put("2", "order");
        keyMap.put("3", "car");

        String country = LocalContext.getCountry();
        Parameter parameter = new Parameter(new Object[][]{
                {"country", country}
        });

        List<Map<String, Object>> carInfoList = this.infoDao.findCar(parameter);
        Map<String, Object> carInfo = new HashMap<>();

        for (Map<String, Object> carInfoMap : carInfoList) {
            String type = Objects.toString(carInfoMap.get("type"));
            carInfo.put(keyMap.get(type), carInfoMap.get("phone"));
        }

        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, carInfo);
        return result;
    }

    @Override
    public Map<String, Object> getInit() {
        Map<String, Object> initInfo = new HashMap<>();

        String group = LocalContext.getGroupId();
        Parameter parameter = new Parameter(new Object[][]{
                {"group", group}
        });

        List<Map<String, Object>> touristList = this.infoDao.findTourists(parameter);
        initInfo.put("tourists", touristList);

        initInfo.put("passport", LocalContext.getPassport());

        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, initInfo);
        return result;
    }

    @Override
    public Map<String, Object> getWeather(double longitude, double latitude) {
        Map<String, Object> data = new HashMap<>();

        WeatherInfo weatherInfo = WeatherGetter.get(longitude, latitude);
        if (weatherInfo != null
                && weatherInfo.getMain() != null
                && weatherInfo.getWeather() != null) {
            Temperature tempInfo = weatherInfo.getMain();
            Weather weather = weatherInfo.getWeather().get(0);
            data.put("temp", (int) NumberHelper.toDouble(tempInfo.getTemp()));
            data.put("maxtemp", (int) NumberHelper.toDouble(tempInfo.getTemp_max()));
            data.put("mintemp", (int) NumberHelper.toDouble(tempInfo.getTemp_min()));
            data.put("description", weather.getDescription());
        }

        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, data);
        return result;
    }

}
