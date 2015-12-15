package com.ulplanet.trip.dao;

import com.ulplanet.trip.bean.Evaluate;
import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;

import java.util.List;

/**
 * Created by makun on 2015/12/14.
 */
@MyBatisDao
public interface EvaluateDao extends CrudDao<Evaluate> {
    List<Evaluate> findByUser(Evaluate evaluate);
}
