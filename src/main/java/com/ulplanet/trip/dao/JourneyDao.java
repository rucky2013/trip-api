package com.ulplanet.trip.dao;

import com.ulplanet.trip.common.persistence.BaseDao;
import com.ulplanet.trip.common.persistence.Parameter;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface JourneyDao extends BaseDao {

    List<Map<String, Object>> findAdvice(Parameter parameter);

    List<Map<String, Object>> findFoodList(Parameter parameter);

    List<Map<String, Object>> findScenicList(Parameter parameter);

    List<Map<String, Object>> findShopList(Parameter parameter);

    Map<String, Object> findFood(Parameter parameter);

    Map<String, Object> findScenic(Parameter parameter);

    Map<String, Object> findShop(Parameter parameter);

    List<Map<String, Object>> findFoodFiles(String foodid);

    List<Map<String, Object>> findScenicFiles(String scenicid);

    List<Map<String, Object>> findShopFiles(String shopid);

}
