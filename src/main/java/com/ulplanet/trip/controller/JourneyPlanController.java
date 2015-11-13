package com.ulplanet.trip.controller;

import com.ulplanet.trip.service.JourneyPlanService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by makun on 2015/9/22.
 */
@RestController
@RequestMapping(value = "/journeyPlan")
public class JourneyPlanController {

    @Resource
    private JourneyPlanService journeyPlanService;

    @RequestMapping(value = "/findList", method = RequestMethod.GET)
    public Map<String,Object> findList(){
        return journeyPlanService.findList();
    }

    @RequestMapping(value = "/getInfo",method = RequestMethod.POST)
    public Map<String,Object> getInfo(@RequestParam(value = "type")int type,@RequestParam(value = "infoId")String infoId,
                                      @RequestParam("longitude") double longitude,
                                        @RequestParam("latitude") double latitude){
        return journeyPlanService.getInfo(type,infoId,longitude,latitude);
    }
}
