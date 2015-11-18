package com.ulplanet.trip.service;

import com.ulplanet.trip.bean.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface UserService {

    Map<String, Object> findUsers(String tag);

    List<User> findUserByParam(User user);

    Map<String, Object> update(User user);

    //TODO 上传头像
    Map<String, Object> upload(MultipartFile file);
}
