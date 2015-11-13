package com.ulplanet.trip.controller;

import com.ulplanet.trip.service.GuideService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/guide")
public class GuideController {

    @Resource
    GuideService guideService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map<String, Object> list(@RequestParam(value = "page") int page,
                                    @RequestParam(value = "size") int size) {
        return this.guideService.findList(page, size);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Map<String, Object> food(@RequestParam("id") String id) {
        return this.guideService.findDetail(id);
    }

}
