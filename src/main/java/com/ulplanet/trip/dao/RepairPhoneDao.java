package com.ulplanet.trip.dao;


import com.ulplanet.trip.bean.RepairPhone;
import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;

import java.util.List;

@MyBatisDao
public interface  RepairPhoneDao extends CrudDao<RepairPhone> {
	List<RepairPhone> findListByParams(RepairPhone repairPhone);


}
