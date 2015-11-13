package com.ulplanet.trip.dao;

import com.ulplanet.trip.bean.Apk;
import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;

import java.util.List;

/**
 * Created by Administrator on 2015/9/17.
 */
@MyBatisDao
public interface ApkDao extends CrudDao<Apk>{
    List<Apk> findByParam(Apk apk);
}
