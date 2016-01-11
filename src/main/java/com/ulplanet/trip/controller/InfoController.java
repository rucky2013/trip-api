package com.ulplanet.trip.controller;

import com.ulplanet.trip.service.InfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/info")
public class InfoController {

    @Resource
    InfoService infoService;

    @RequestMapping(value = "/emergency", method = RequestMethod.GET)
    public Map<String, Object> getEmergency() {
        return this.infoService.getEmergency();
    }

    @RequestMapping(value = "/car", method = RequestMethod.GET)
    public Map<String, Object> getCar() {
        return this.infoService.getCar();
    }

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public Map<String, Object> getInit() {
        return this.infoService.getInit();
    }

    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    public Map<String, Object> getWeather(@RequestParam("longitude") double longitude,
                                          @RequestParam("latitude") double latitude) {
        return this.infoService.getWeather(longitude, latitude);
    }

    @RequestMapping(value = "/chatGroup", method = RequestMethod.GET)
    public Map<String, Object> getChatGroup(@RequestParam(value = "tag", required = false) String tag) {
        return this.infoService.getChatGroup(tag);
    }
}
