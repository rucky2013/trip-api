package com.ulplanet.trip.controller;

import com.ulplanet.trip.service.LoginService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public Map<String, Object> doLogin(HttpServletRequest request,
                                       @RequestParam("userid") String userid,
                                       @RequestParam("userpwd") String userpwd,
                                       @RequestParam("longitude") double longitude,
                                       @RequestParam("latitude") double latitude) {
        return loginService.login(request, userid, userpwd, longitude, latitude);
    }

}

