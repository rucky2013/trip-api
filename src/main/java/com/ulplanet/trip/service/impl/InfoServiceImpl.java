package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.api.weather.bean.Temperature;
import com.ulplanet.trip.api.weather.bean.Weather;
import com.ulplanet.trip.api.weather.bean.WeatherInfo;
import com.ulplanet.trip.api.weather.service.WeatherGetter;
import com.ulplanet.trip.common.persistence.Parameter;
import com.ulplanet.trip.common.utils.IdGen;
import com.ulplanet.trip.common.utils.NumberHelper;
import com.ulplanet.trip.common.utils.StringHelper;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.InfoDao;
import com.ulplanet.trip.dao.VersionTagDao;
import com.ulplanet.trip.service.InfoService;
import com.ulplanet.trip.util.LocalContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("infoService")
public class InfoServiceImpl implements InfoService {

    @Autowired
    private InfoDao infoDao;

    @Autowired
    VersionTagDao versionTagDao;

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

    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> getChatGroup(String tag) {

        String groupId = LocalContext.getGroupId();
        String userId = LocalContext.getUserId();

//        VersionTag currentVersionTag = versionTagDao.get(new VersionTag(userId, Constants.VERSION_TAG_CHATGROUP));
//
        String currentTag = IdGen.uuid();
//        if (currentVersionTag == null) {
//            currentTag = IdGen.uuid();
//            versionTagDao.insert(new VersionTag(userId, Constants.VERSION_TAG_CHATGROUP, currentTag));
//        } else {
//            currentTag = currentVersionTag.getTag();
//        }

        List<Map<String, Object>> groupInfos = new ArrayList<>();

        if (StringHelper.isEmpty(tag) || !tag.equals(currentTag)) {
            Parameter parameter = new Parameter(new Object[][]{
                {"group", groupId},
                {"user", userId}
            });
            groupInfos = this.infoDao.getChatGroup(parameter);
        }


        Map<String, Object> result = new HashMap<>();
        result.put("tag", currentTag);
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, groupInfos);
        return result;
    }

    public Map<String, Object> getLocalPhone() {
        String country = LocalContext.getCountry();
        Parameter parameter = new Parameter(new Object[][]{
                {"country", country}
        });

        List<Map<String, Object>> localPhones = new ArrayList<>();
        List<Map<String, Object>> phones = this.infoDao.getLocalPhone(parameter);
        if (phones != null && phones.size() > 0) {
            localPhones.addAll(phones);
        }
        Map<String, Object> emergencyInfo = this.infoDao.findEmergency(parameter);
        if (emergencyInfo != null) {

            String ambulance = Objects.toString(emergencyInfo.get("ambulance"));
            if (StringHelper.isNotBlank(ambulance)) {
                Map<String, Object> ambulanceMap = new HashMap<>();
                ambulanceMap.put("name", "急救电话");
                ambulanceMap.put("phone", ambulance);
                localPhones.add(ambulanceMap);
            }

            String police = Objects.toString(emergencyInfo.get("police"));
            if (StringHelper.isNotBlank(police)) {
                Map<String, Object> policeMap = new HashMap<>();
                policeMap.put("name", "匪警");
                policeMap.put("phone", police);
                localPhones.add(policeMap);
            }

            String fire = Objects.toString(emergencyInfo.get("fire"));
            if (StringHelper.isNotBlank(fire)) {
                Map<String, Object> fireMap = new HashMap<>();
                fireMap.put("name", "火警");
                fireMap.put("phone", fire);
                localPhones.add(fireMap);
            }

            String seaEmerg = Objects.toString(emergencyInfo.get("sea_emerg"));
            if (StringHelper.isNotBlank(seaEmerg)) {
                Map<String, Object> seaEmergMap = new HashMap<>();
                seaEmergMap.put("name", "海上急救");
                seaEmergMap.put("phone", seaEmerg);
                localPhones.add(seaEmergMap);
            }

            String roadEmerg = Objects.toString(emergencyInfo.get("road_emerg"));
            if (StringHelper.isNotBlank(roadEmerg)) {
                Map<String, Object> roadEmergMap = new HashMap<>();
                roadEmergMap.put("name", "公路抢险");
                roadEmergMap.put("phone", roadEmerg);
                localPhones.add(roadEmergMap);
            }

            String unionpayCall = Objects.toString(emergencyInfo.get("unionpay_call"));
            if (StringHelper.isNotBlank(unionpayCall)) {
                Map<String, Object> unionpayCallMap = new HashMap<>();
                unionpayCallMap.put("name", "银联境外客服");
                unionpayCallMap.put("phone", unionpayCall);
                localPhones.add(unionpayCallMap);
            }

            String embassyCall = Objects.toString(emergencyInfo.get("embassy_call"));
            if (StringHelper.isNotBlank(embassyCall)) {
                Map<String, Object> embassyCallMap = new HashMap<>();
                embassyCallMap.put("name", "大使馆");
                embassyCallMap.put("phone", embassyCall);
                localPhones.add(embassyCallMap);
            }

        }

        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, localPhones);
        return result;
    }
}
