package com.ulplanet.trip.controller;

import com.ulplanet.trip.service.JourneyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/journey")
public class JourneyController {

    @Resource
    JourneyService journeyService;

    @RequestMapping(value = "/advice", method = RequestMethod.GET)
    public Map<String, Object> advice() {
        return this.journeyService.findAdvice();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map<String, Object> list(@RequestParam(value = "type") int type,
                                    @RequestParam(value = "stype", required = false) String stype,
                                    @RequestParam(value = "order", required = false) String order,
                                    @RequestParam(value = "longitude") double longitude,
                                    @RequestParam(value = "latitude") double latitude) {
        return this.journeyService.findJourneyList(type, stype, order, longitude, latitude);
    }

    @RequestMapping(value = "/food", method = RequestMethod.GET)
    public Map<String, Object> food(@RequestParam("id") String id,
                                    @RequestParam("longitude") double longitude,
                                    @RequestParam("latitude") double latitude) {
        return this.journeyService.findFood(id, longitude, latitude);
    }

    @RequestMapping(value = "/scenic", method = RequestMethod.GET)
    public Map<String, Object> scenic(@RequestParam("id") String id,
                                      @RequestParam("longitude") double longitude,
                                      @RequestParam("latitude") double latitude) {
        return this.journeyService.findScenic(id, longitude, latitude);
    }

    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    public Map<String, Object> shop(@RequestParam("id") String id,
                                    @RequestParam("longitude") double longitude,
                                    @RequestParam("latitude") double latitude) {
        return this.journeyService.findShop(id, longitude, latitude);
    }

}
