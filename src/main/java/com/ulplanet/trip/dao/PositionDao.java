package com.ulplanet.trip.dao;

import com.ulplanet.trip.common.persistence.BaseDao;
import com.ulplanet.trip.common.persistence.Parameter;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface PositionDao extends BaseDao {

    void savePosition(Parameter parameter);

}
