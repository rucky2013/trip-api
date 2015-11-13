package com.ulplanet.trip.dao;

import com.ulplanet.trip.common.persistence.BaseDao;
import com.ulplanet.trip.common.persistence.Parameter;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface GuideDao extends BaseDao {

    int findCount(Parameter parameter);

    List<Map<String, Object>> findList(Parameter parameter);

    Map<String, Object> findGuide(Parameter parameter);

    List<Map<String, Object>> findGuideFiles(String guideid);

}
