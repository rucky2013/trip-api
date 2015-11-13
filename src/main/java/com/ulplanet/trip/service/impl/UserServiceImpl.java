package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.bean.User;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.UserDao;
import com.ulplanet.trip.service.UserService;
import com.ulplanet.trip.util.LocalContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public Map<String, Object> findUsers() {

        String groupid = LocalContext.getGroupId();

        List<Map<String, Object>> userList = this.userDao.findUsers(groupid);
        for (Map<String, Object> userMap : userList) {
            userMap.put("photo", userMap.get("photo")); //TODO 头像
        }

        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, userList);
        return result;

    }

    @Override
    public List<User> findUserByParam(User user) {
        return userDao.findUserByParam(user);
    }


}
