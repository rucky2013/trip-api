package com.ulplanet.trip.dao;

import com.ulplanet.trip.bean.StockOrder;
import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface  StockOrderDao extends CrudDao<StockOrder> {
	List<StockOrder> findListByParams(StockOrder stockOrder);
	StockOrder getById(@Param("id") String id);

}
