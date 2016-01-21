package com.ulplanet.trip.dao;

import com.ulplanet.trip.bean.InfoBo;
import com.ulplanet.trip.bean.JourneyPlan;
import com.ulplanet.trip.bean.JourneyPlans;
import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@MyBatisDao
public interface  JourneyPlanDao extends CrudDao<JourneyPlan> {
	
    List<JourneyPlans> queryAllPlanByGroup(@Param("group")String group,@Param("code")String code);

    List<InfoBo> findInfoByTableName(@Param(value = "table") String table,
                                     @Param(value = "ids") String[] ids, @Param(value = "infoId") String infoId,
                                     @Param(value = "tableCol") String tableCol);

    Map<String,Date> queryDate(String id);

}
