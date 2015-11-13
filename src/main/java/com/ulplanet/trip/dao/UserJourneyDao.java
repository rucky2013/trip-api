package com.ulplanet.trip.dao;


import com.ulplanet.trip.bean.UserJourney;
import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;

import java.util.List;
@MyBatisDao
public interface UserJourneyDao extends CrudDao<UserJourney> {
    List<UserJourney> findByParams(UserJourney userJourney);
}
