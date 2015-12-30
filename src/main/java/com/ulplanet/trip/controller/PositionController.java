package com.ulplanet.trip.controller;

import com.ulplanet.trip.service.PositionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/position")
public class PositionController {

    @Resource
    PositionService positionService;

    @RequestMapping(value = "/put", method = RequestMethod.POST)
    public Map<String, Object> putPoint(HttpServletRequest request,
                                        @RequestParam("longitude") double longitude,
                                        @RequestParam("latitude") double latitude,
                                        @RequestParam(value = "time", required = false) Long time) {
        return this.positionService.savePoint(request, longitude, latitude, time);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Map<String, Object> getPoint() {
        return this.positionService.getPoint();
    }
}
