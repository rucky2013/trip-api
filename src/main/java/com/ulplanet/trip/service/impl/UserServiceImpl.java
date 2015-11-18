package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.bean.User;
import com.ulplanet.trip.common.utils.FileManager;
import com.ulplanet.trip.common.utils.fservice.FileIndex;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.UserDao;
import com.ulplanet.trip.service.UserService;
import com.ulplanet.trip.util.LocalContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public Map<String, Object> findUsers(String tag) {

        String groupid = LocalContext.getGroupId();

        List<Map<String, Object>> userList = this.userDao.findUsers(groupid);
        for (Map<String, Object> userMap : userList) {
            if (userMap.get("photo") != null) {
                userMap.put("photo", FileManager.getFileUrlByRealpath(String.valueOf(userMap.get("photo")))); //TODO 头像
            }
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

    @Override
    public Map<String, Object> update(User user) {
        user.setId(LocalContext.getUser().getUserId());
        user.setUpdateBy(user.getId());
        user.setUpdateDate(new Date());
        int i = userDao.update(user);
        Map<String, Object> result = new HashMap<>();
        if(i > 0){
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        }else{
            result.put(Constants.RETURN_FIELD_MESSAGE, "更新失败");
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_FAILURE);
        }
        return result;
    }

    @Override
    public Map<String, Object> upload(MultipartFile file) {
        User user = LocalContext.getUser();
        user.setId(user.getUserId());
        FileIndex ufi = new FileIndex();
        ufi.setmUpfile(file);
        ufi.setTruename(file.getOriginalFilename());
        ufi.setMcode("user");
        ufi = FileManager.save(ufi);
        user.setPhoto(ufi.getPath());
        int i = userDao.update(user);
        Map<String, Object> result = new HashMap<>();
        if(i > 0){
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        }else{
            result.put(Constants.RETURN_FIELD_MESSAGE, "上传失败");
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_FAILURE);
        }
        return result;
    }


}
