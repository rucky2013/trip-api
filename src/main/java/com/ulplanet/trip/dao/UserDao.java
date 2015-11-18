package com.ulplanet.trip.dao;

import com.ulplanet.trip.bean.User;
import com.ulplanet.trip.common.persistence.BaseDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface UserDao extends BaseDao {

    List<Map<String, Object>> findUsers(String groupid);

    User findUser(String code);

    List<User> findUserByParam(User user);

    int update(User user);

    int updateCPhone(User user);

    Map<String, Object> findJourneyCountry(String groupid);

}
