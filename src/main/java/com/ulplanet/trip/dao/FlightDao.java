package com.ulplanet.trip.dao;

import com.ulplanet.trip.bean.Flight;
import com.ulplanet.trip.bean.InfoBo;
import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by makun on 2015/10/28.
 */
@MyBatisDao
public interface FlightDao extends CrudDao<Flight> {
    List<InfoBo> findListByCityIds(@Param(value = "ids") String[] ids);
}
