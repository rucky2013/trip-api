package com.ulplanet.trip.service;

import com.ulplanet.trip.bean.User;

import java.util.List;
import java.util.Map;


public interface UserService {

    Map<String, Object> findUsers();

    List<User> findUserByParam(User user);

    //TODO 上传头像
}
