package com.ulplanet.trip.controller;

import com.ulplanet.trip.bean.User;
import com.ulplanet.trip.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Map<String, Object> update(User user) {
        return this.userService.update(user);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Map<String, Object> upload(@RequestParam MultipartFile file) {
        return this.userService.upload(file);
    }

}
