package com.ulplanet.trip.dao;

import com.ulplanet.trip.bean.CallRecord;
import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;

/**
 * Created by makun on 2016/1/14.
 */
@MyBatisDao
public interface CallRecordDao extends CrudDao<CallRecord> {

}
