package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.api.location.GeocodeService;
import com.ulplanet.trip.bean.User;
import com.ulplanet.trip.common.utils.JedisUtils;
import com.ulplanet.trip.common.utils.StringHelper;
import com.ulplanet.trip.common.utils.TokenUtils;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.UserDao;
import com.ulplanet.trip.service.LoginService;
import com.ulplanet.trip.util.LoginConstants;
import com.ulplanet.trip.util.LoginException;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

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
            logger.error("login error user:" + userid + ",lng:" + longitude + ",lat:" + latitude);
            throw LoginConstants.LOGIN_ERROR;
        }

        user.setCurrentCountry(geocode.getCountry());
        user.setCurrentCity(geocode.getCity());
        user.setLastUpdate(new Date().getTime());
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

        return data;
    }

}
