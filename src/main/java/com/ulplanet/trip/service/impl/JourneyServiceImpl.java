package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.common.persistence.Parameter;
import com.ulplanet.trip.common.utils.FileManager;
import com.ulplanet.trip.common.utils.NumberHelper;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.InfoDao;
import com.ulplanet.trip.dao.JourneyDao;
import com.ulplanet.trip.service.JourneyService;
import com.ulplanet.trip.util.LocalContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("journeyService")
public class JourneyServiceImpl implements JourneyService {

    @Autowired
    private JourneyDao journeyDao;
    @Autowired
    private InfoDao infoDao;

    @Override
    public Map<String, Object> findAdvice() {
        Parameter parameter = new Parameter(new Object[][]{
                {"country", LocalContext.getCountry()}
        });

        List<Map<String, Object>> adviceList = this.journeyDao.findAdvice(parameter);

        List<Map<String, Object>> datas = new ArrayList<>();
        for (Map<String, Object> advice : adviceList) {
            Map<String, Object> data = new HashMap<>();
            data.put("score", (int) NumberHelper.toDouble(Objects.toString(advice.get("score"))));
            data.put("id", advice.get("id"));
            data.put("name", advice.get("name"));
            data.put("type", advice.get("type"));
            String path = Objects.toString(advice.get("path"));
            data.put("url", FileManager.getFileUrlByRealpath(path));
            datas.add(data);
        }

        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, datas);
        return result;
    }

    @Override
    public Map<String, Object> findJourneyList(int type,
                                               String stype, String order, double longitude, double latitude) {

        List<Map<String, Object>> journeyList;
        Parameter parameter = new Parameter(new Object[][]{
                {"order", order}, {"stype", stype},
                {"longitude", longitude}, {"latitude", latitude},
                {"country", LocalContext.getCountry()}
        });
        switch (type) {
            case Constants.JOURNEY_FOOD:
                journeyList = this.journeyDao.findFoodList(parameter);
                break;
            case Constants.JOURNEY_SCENIC:
                journeyList = this.journeyDao.findScenicList(parameter);
                break;
            case Constants.JOURNEY_SHOP:
                journeyList = this.journeyDao.findShopList(parameter);
                break;
            default:
                journeyList = new ArrayList<>();
        }

        List<Map<String, Object>> datas = new ArrayList<>();
        for (Map<String, Object> journey : journeyList) {

            Map<String, Object> data = new HashMap<>();
            data.put("id", journey.get("id"));
            data.put("name", journey.get("name"));
            data.put("score", journey.get("score"));

            int distance = (int) NumberHelper.toDouble(Objects.toString(journey.get("distance"), "0.0"));
            data.put("distance", distance);
            data.put("url", FileManager.getFileUrlByRealpath(Objects.toString(journey.get("path"), "")));
            datas.add(data);

        }

        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, datas);
        return result;
    }

    @Override
    public Map<String, Object> findFood(String id, double longitude,
                                        double latitude) {
        Parameter parameter = new Parameter(new Object[][]{
                {"id", id}, {"longitude", longitude}, {"latitude", latitude}
        });
        Map<String, Object> food = this.journeyDao.findFood(parameter);

        if (food != null) {
            List<Map<String, Object>> foodFiles = this.journeyDao.findFoodFiles(id);
            List<String> foodPathList = new ArrayList<>();
            String mapFilePath = "";
            for (Map<String, Object> foodFile : foodFiles) {
                String url = FileManager.getFileUrlByRealpath(Objects.toString(foodFile.get("path")));
                String type = Objects.toString(foodFile.get("type"));
                if (Constants.FOODFILE_TYPE_MAP.equals(type)) {
                    mapFilePath = url;
                } else if (Constants.FOODFILE_TYPE_LIST.equals(type)) {
                    foodPathList.add(url);
                }
            }

            food.put("car", this.findCarPhone());
            food.put("mapurl", mapFilePath);
            food.put("urllist", foodPathList);
        }

        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, food);
        return result;
    }

    @Override
    public Map<String, Object> findScenic(String id, double longitude,
                                          double latitude) {
        Parameter parameter = new Parameter(new Object[][]{
                {"id", id}, {"longitude", longitude}, {"latitude", latitude}
        });
        Map<String, Object> scenic = this.journeyDao.findScenic(parameter);

        if (scenic != null) {
            List<Map<String, Object>> scenicFiles = this.journeyDao.findScenicFiles(id);
            List<String> scenicPathList = new ArrayList<>();
            String mapFilePath = "";
            for (Map<String, Object> scenicFile : scenicFiles) {
                String url = FileManager.getFileUrlByRealpath(Objects.toString(scenicFile.get("path")));
                String type = Objects.toString(scenicFile.get("type"));
                if (Constants.SCENICFILE_TYPE_MAP.equals(type)) {
                    mapFilePath = url;
                } else if (Constants.SCENICFILE_TYPE_LIST.equals(type)) {
                    scenicPathList.add(url);
                }
            }

            scenic.put("car", this.findCarPhone());
            scenic.put("mapurl", mapFilePath);
            scenic.put("urllist", scenicPathList);
        }

        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, scenic);
        return result;
    }

    @Override
    public Map<String, Object> findShop(String id, double longitude,
                                        double latitude) {
        Parameter parameter = new Parameter(new Object[][]{
                {"id", id}, {"longitude", longitude}, {"latitude", latitude}
        });
        Map<String, Object> shop = this.journeyDao.findShop(parameter);

        if (shop != null) {
            List<Map<String, Object>> shopFiles = this.journeyDao.findShopFiles(id);
            List<String> shopPathList = new ArrayList<>();
            List<Map<String, Object>> adviceList = new ArrayList<>();
            String mapFilePath = "";
            for (Map<String, Object> shopFile : shopFiles) {
                String url = FileManager.getFileUrlByRealpath(Objects.toString(shopFile.get("path")));
                String type = Objects.toString(shopFile.get("type"));
                String description = Objects.toString(shopFile.get("description"));
                if (Constants.SHOPFILE_TYPE_MAP.equals(type)) {
                    mapFilePath = url;
                } else if (Constants.SHOPFILE_TYPE_ADVICE.equals(type)) {
                    Map<String, Object> adviceMap = new HashMap<>();
                    adviceMap.put("url", url);
                    adviceMap.put("description", description);
                    adviceList.add(adviceMap);
                } else if (Constants.SHOPFILE_TYPE_LIST.equals(type)) {
                    shopPathList.add(url);
                }
            }

            shop.put("car", this.findCarPhone());
            shop.put("mapurl", mapFilePath);
            shop.put("urllist", shopPathList);
            shop.put("advicelist", adviceList);
        }

        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, shop);
        return result;
    }

    private String findCarPhone() {

        String country = LocalContext.getCountry();
        Parameter parameter = new Parameter(new Object[][]{
                {"country", country}
        });

        return Objects.toString(this.infoDao.findCarPhone(parameter), "");
    }

}
