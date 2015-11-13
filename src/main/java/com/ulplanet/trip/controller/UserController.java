package com.ulplanet.trip.controller;

import com.ulplanet.trip.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map<String, Object> getUsers() {
        return this.userService.findUsers();
    }

}
