package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.bean.User;
import com.ulplanet.trip.bean.VersionTag;
import com.ulplanet.trip.common.utils.FileManager;
import com.ulplanet.trip.common.utils.IdGen;
import com.ulplanet.trip.common.utils.StringHelper;
import com.ulplanet.trip.common.utils.fservice.FileIndex;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.UserDao;
import com.ulplanet.trip.dao.VersionTagDao;
import com.ulplanet.trip.service.UserService;
import com.ulplanet.trip.util.LocalContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    VersionTagDao versionTagDao;

    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> findUsers(String tag) {

        List<Map<String, Object>> userList = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();

        String groupid = LocalContext.getGroupId();

        VersionTag currentVersionTag = versionTagDao.get(new VersionTag(groupid, Constants.VERSION_TAG_USERLIST));

        String currentTag;
        if (currentVersionTag == null) {
            currentTag = IdGen.uuid();
            versionTagDao.insert(new VersionTag(groupid, Constants.VERSION_TAG_USERLIST, currentTag));
        } else {
            currentTag = currentVersionTag.getTag();
        }

        if (StringHelper.isEmpty(tag) || !tag.equals(currentTag)) {
            for (Map<String, Object> userMap : this.userDao.findUsers(groupid)) {
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("gender", Objects.toString(userMap.get("gender"), "1"));
                resultMap.put("name", userMap.get("name"));
                resultMap.put("id", userMap.get("id"));
                resultMap.put("type", Objects.toString(userMap.get("type"), "1"));
                resultMap.put("cphone", Objects.toString(userMap.get("cphone")));
                resultMap.put("photo", Objects.toString(userMap.get("photo")));
                userList.add(resultMap);
            }
        }

        result.put("tag", currentTag);
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
        user.setUserId(LocalContext.getUser().getUserId());
        int i = userDao.update(user);
        if(user.getPositionFlag()!=null) {
            user.setCode(LocalContext.getUser().getCode());
            i = userDao.updateGroupUser(user);
        }
        Map<String, Object> result = new HashMap<>();
        if(i > 0){
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
            result.put(Constants.RETURN_FIELD_DATA, new HashMap<>());
        }else{
            result.put(Constants.RETURN_FIELD_MESSAGE, "更新失败");
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_FAILURE);
        }
        return result;
    }

    @Override
    public Map<String, Object> upload(MultipartFile file) {
        User user = new User();
        FileIndex ufi = new FileIndex();
        ufi.setmUpfile(file);
        ufi.setTruename(file.getOriginalFilename());
        ufi.setMcode("user");
        ufi = FileManager.save(ufi);
        user.setUserId(LocalContext.getUser().getUserId());
        user.setPhoto(FileManager.getFileUrlByRealpath(ufi.getPath()));
        int i = userDao.update(user);
        Map<String, Object> result = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        data.put("photo",user.getPhoto());
        if(i > 0){
            versionTagDao.update(new VersionTag(LocalContext.getUser().getGroup(),1));
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
            result.put(Constants.RETURN_FIELD_DATA, data);
        }else{
            result.put(Constants.RETURN_FIELD_MESSAGE, "上传失败");
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_FAILURE);
        }
        return result;
    }


}
