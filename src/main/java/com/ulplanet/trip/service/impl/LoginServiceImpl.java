package com.ulplanet.trip.service.impl;

import com.google.gson.Gson;
import com.ulplanet.trip.api.location.GeocodeService;
import com.ulplanet.trip.bean.User;
import com.ulplanet.trip.bean.VersionTag;
import com.ulplanet.trip.common.utils.*;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.UserDao;
import com.ulplanet.trip.dao.VersionTagDao;
import com.ulplanet.trip.service.LoginService;
import com.ulplanet.trip.util.LoginException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    UserDao userdao;

    @Autowired
    VersionTagDao versionTagDao;

    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> login(HttpServletRequest request, String userid,
                                     String userpwd, double longitude, double latitude, String cphone) {
        Map<String, Object> result = new HashMap<>();

        try {
            result.put(Constants.RETURN_FIELD_DATA,
                    this.doLogin(request, userid, userpwd, longitude, latitude, cphone));
        } catch (LoginException e) {
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_FAILURE);
            result.put(Constants.RETURN_FIELD_MESSAGE, e.getMessage());
            return result;
        }

        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        return result;
    }

    private Map<String, Object> doLogin(HttpServletRequest request, String userid,
                                        String userpwd, double longitude, double latitude, String cphone) {

        String imei = request.getHeader(Constants.HEADER_IMEI);
        if (StringHelper.isEmpty(imei)) {
            throw new LoginException("设备无效。");
        }

        User user = this.userdao.findUser(userid);

        if (user == null) {
            throw new LoginException("用户名不存在。");
        }

        if (StringHelper.isEmpty(userpwd) ||
                !StringHelper.equals(userpwd, user.getPhone())) {
            throw new LoginException("密码无效。");
        }

        int endDate = NumberHelper.toInt(new SimpleDateFormat("yyyyMMdd").format(user.getEndDate()), 0);
        if (endDate < DateHelper.getBjDate()) {
            throw new LoginException("该行程已结束。");
        }

        if (StringHelper.isNotEmpty(cphone) && !cphone.equals(user.getCphone())) {
            user.setCphone(cphone);
            userdao.updateCPhone(user);
            versionTagDao.update(new VersionTag(user.getGroup(), Constants.VERSION_TAG_USERLIST));
        }


        String country = "中国";
        String city = "北京";
        GeocodeService.Geocode geocode = GeocodeService.get(longitude, latitude);
        if (geocode == null
                || StringHelper.isEmpty(geocode.getCountry())) {
            logger.error("google geocode error. user:" + userid + ",lng:" + longitude + ",lat:" + latitude);
            Map<String, Object> journeyCountryMap = userdao.findJourneyCountry(user.getGroup());
            if (journeyCountryMap != null) {
                country = Objects.toString(journeyCountryMap.get("country"), country);
                city = Objects.toString(journeyCountryMap.get("city"), city);
            }
        } else {
            country = geocode.getCountry();
            city = Objects.toString(geocode.getCity(), "");
        }

        user.setCurrentCountry(country);
        user.setCurrentCity(city);
        user.setLastUpdate(new Date().getTime());
        user.setPhoto(StringUtils.isBlank(user.getPhoto()) ? "" : user.getPhoto());
        String token = TokenUtils.getToken(imei);
        JedisUtils.set(token, new Gson().toJson(user), 60 * 60 * 24 * 10);
        JedisUtils.set(user.getId(), token, 60 * 60 * 24 * 10);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("imtoken", user.getImToken());
        data.put("group", user.getGroup());
        data.put("gender", user.getGender());
        data.put("photo", user.getPhoto());
        data.put("name", user.getName());
        data.put("phone", user.getPhone());
        data.put("cphone", user.getCphone());
        data.put("userid", user.getId());
        data.put("passport", user.getPassport());
        data.put("type", user.getType());
        data.put("identityCard", user.getIdentityCard());
        data.put("weChat", user.getWeChat());
        data.put("qq", user.getQq());
        data.put("birth", DateHelper.formatDate(user.getBirth(), "yyyy/MM/dd"));
        data.put("email", user.getEmail());
        data.put("birthPlace", user.getBirthPlace());
        data.put("positionFlag", user.getPositionFlag());
        data.put("telFunction", user.getTelFunction());

        return data;
    }

}
