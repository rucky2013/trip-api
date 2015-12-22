package com.ulplanet.trip.api.weather.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ulplanet.trip.api.weather.bean.WeatherInfo;
import com.ulplanet.trip.bean.ResultInfo;
import com.ulplanet.trip.util.HttpUtils;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class WeatherGetter {

    private static final Logger logger = LoggerFactory.getLogger(WeatherGetter.class);

    private static final String API_URL_NOW = "http://api.openweathermap.org/data/2.5/weather";

    private static final String API_URL_DAY = "http://api.openweathermap.org/data/2.5/forecast/daily";

    public static WeatherInfo get(double lon, double lat) {

        Map<String, String> params = new HashMap<>();
        params.put("lat", lat + "");
        params.put("lon", lon + "");
        params.put("units", "metric");
        params.put("lang", "zh_cn");
        params.put("APPID", "6fd477c2b646f5d60d2cc6203c092b57");

        try {
            /**请求即时天气接口**/
            ResultInfo resultInfo = HttpUtils.httpGet(params, API_URL_NOW);
            if(resultInfo.getStatus()!=200) {
                logger.error("请求即时天气接口失败:" + resultInfo.getStatus() + ";经度:" + lon + ";纬度:" + lat);
                return null;
            }
            String message = resultInfo.getMessage();
            WeatherInfo info = new Gson().fromJson(message, WeatherInfo.class);
            if (info.getCod() != 200) {
                logger.error("请求即时天气接口错误:" + info.getMessage() + ";经度:" + lon + ";纬度:" + lat);
                return null;
            }
            /**请求每天天气接口**/
            params.put("cnt","1");
            resultInfo = HttpUtils.httpGet(params, API_URL_DAY);
            if(resultInfo.getStatus()!=200) {
                logger.error("请求每天天气接口失败:" + resultInfo.getStatus() + ";经度:" + lon + ";纬度:" + lat);
                return null;
            }
            message = resultInfo.getMessage();
            JSONObject jsonObject = JSON.parseObject(message);
            if(jsonObject.getInteger("cod") != 200){
                logger.error("请求每天天气接口错误:" + info.getMessage() + ";经度:" + lon + ";纬度:" + lat);
                return null;
            }
            JSONArray array = jsonObject.getJSONArray("list");
            if(array.size()>0){
                jsonObject = array.getJSONObject(0).getJSONObject("temp");
                info.getMain().setTemp_max(jsonObject.getString("max"));
                info.getMain().setTemp_min(jsonObject.getString("min"));
            }else{
                logger.error("请求每天天气接口错误:" + info.getMessage() + ";经度:" + lon + ";纬度:" + lat);
                return null;
            }
            return info;
        } catch (Exception e) {
            logger.error("PrintStackTrace:", e);
            return null;
        }

    }

}