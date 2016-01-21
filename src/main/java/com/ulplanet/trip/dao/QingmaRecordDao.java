package com.ulplanet.trip.dao;

import com.ulplanet.trip.bean.QingmaRecord;
import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;

/**
 * Created by makun on 2016/1/16.
 */
@MyBatisDao
public interface QingmaRecordDao extends CrudDao<QingmaRecord> {
    String getPwd(String clientNumber);
}
