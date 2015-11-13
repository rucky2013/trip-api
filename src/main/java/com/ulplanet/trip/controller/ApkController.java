package com.ulplanet.trip.controller;

import com.ulplanet.trip.service.ApkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by makun on 2015/9/17.
 */
@Controller
@RequestMapping(value = "/apk")
public class ApkController {
    @Resource
    private ApkService apkService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> list(@RequestParam(value = "name",required = false) String name,
                                    @RequestParam(value = "packageName",required = false) String packageName) {
        return this.apkService.findByParams(name,packageName);
    }
}
