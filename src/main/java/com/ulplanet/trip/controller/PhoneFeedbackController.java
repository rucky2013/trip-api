package com.ulplanet.trip.controller;

import com.ulplanet.trip.service.PhoneFeedbackService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by makun on 2015/12/15.
 */
@RestController
@RequestMapping(value = "/feedback")
public class PhoneFeedbackController {
    @Resource
    private PhoneFeedbackService phoneFeedbackService;

    @RequestMapping(value = "/getPhoneFunction", method = RequestMethod.GET)
         public Map<String, Object> getPhoneFunction() {
        return this.phoneFeedbackService.getPhoneFunctions();
    }

    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public Map<String, Object> feedback(@RequestParam("json")String json,@RequestParam("userCode")String userCode) {
        return this.phoneFeedbackService.savePhoneFeedback(json,userCode);
    }

}
