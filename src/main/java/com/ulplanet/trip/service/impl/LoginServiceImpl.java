package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.api.location.GeocodeService;
import com.ulplanet.trip.bean.User;
import com.ulplanet.trip.common.utils.FileManager;
import com.ulplanet.trip.common.utils.JedisUtils;
import com.ulplanet.trip.common.utils.StringHelper;
import com.ulplanet.trip.common.utils.TokenUtils;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.UserDao;
import com.ulplanet.trip.service.LoginService;
import com.ulplanet.trip.util.LoginConstants;
import com.ulplanet.trip.util.LoginException;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserDao userdao;

    @Override
    public Map<String, Object> login(HttpServletRequest request, String userid, String userpwd, double longitude, double latitude) {
        Map<String, Object> result = new HashMap<>();

        try {
            result.put(Constants.RETURN_FIELD_DATA, this.doLogin(request, userid, userpwd, longitude, latitude));
        } catch (LoginException e) {
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_FAILURE);
            result.put(Constants.RETURN_FIELD_MESSAGE, e.getMessage());
            return result;
        }

        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        return result;
    }

    private Map<String, Object> doLogin(HttpServletRequest request, String userid, String userpwd, double longitude, double latitude) {

        String imei = request.getHeader(Constants.HEADER_IMEI);
        if (StringHelper.isEmpty(imei)) {
            throw LoginConstants.USER_INVALID_DEVICE;
        }

        User user = this.userdao.findUser(userid);

        if (user == null) {
            throw LoginConstants.USER_NOT_EXISTS;
        }

        if (StringHelper.isEmpty(userpwd) ||
                !StringHelper.equals(userpwd, user.getPhone())) {
            throw LoginConstants.USER_INVALID_PASSWORD;
        }

        GeocodeService.Geocode geocode = GeocodeService.get(longitude, latitude);
        if (geocode == null
                || StringHelper.isEmpty(geocode.getCountry())) {
            throw LoginConstants.LOGIN_ERROR;
        }

        user.setCurrentCountry(geocode.getCountry());
        user.setCurrentCity(geocode.getCity());
        user.setLastUpdate(new Date().getTime());
        user.setPhoto(StringUtils.isBlank(user.getPhoto()) ? "" : FileManager.getFileUrlByRealpath(user.getPhoto()));
        String token = TokenUtils.getToken(imei);
        JedisUtils.set(token, new Gson().toJson(user), 60 * 60 * 24 * 10);
        JedisUtils.set(user.getId(), token, 60 * 60 * 24 * 10);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("imtoken", user.getImToken());
        data.put("group", user.getGroup());
        data.put("photo", user.getPhoto()); //TODO photo
        data.put("name", user.getName());
        data.put("phone", user.getPhone());
        data.put("userid", user.getId());
        data.put("type", user.getType());
        data.put("weChat", user.getWeChat());
        data.put("qq", user.getQq());
        data.put("birth", user.getBirth());
        data.put("issueDate", user.getIssueDate());
        data.put("expiryDate", user.getExpiryDate());
        data.put("birthPlace", user.getBirthPlace());
        data.put("issuePlace", user.getIssuePlace());

        return data;
    }

}
