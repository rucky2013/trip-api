package com.ulplanet.trip.dao;

import com.ulplanet.trip.common.persistence.BaseDao;
import com.ulplanet.trip.common.persistence.Parameter;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface InfoDao extends BaseDao {

    Map<String, Object> findEmergency(Parameter parameter);

    List<Map<String, Object>> findCar(Parameter parameter);

    List<Map<String, Object>> findTourists(Parameter parameter);

    String findCarPhone(Parameter parameter);

    List<Map<String, Object>> getChatGroup(String groupId);

    List<Map<String, Object>> getLocalPhone(Parameter parameter);

}
