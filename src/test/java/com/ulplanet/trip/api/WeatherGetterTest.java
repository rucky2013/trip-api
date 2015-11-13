package com.ulplanet.trip.api;

import com.alibaba.fastjson.JSON;
import com.ulplanet.trip.api.weather.bean.WeatherInfo;
import com.ulplanet.trip.api.weather.service.WeatherGetter;

/**
 * Created by Administrator on 2015/9/16.
 */
public class WeatherGetterTest {
    public static void main(String [] args){
        WeatherInfo info = WeatherGetter.get(149.1191925, -35.3257854);
        if(info!=null){
            System.out.print(JSON.toJSONString(info));
        }
    }
}
