package com.ulplanet.trip.controller;

import com.ulplanet.trip.bean.CallRecord;
import com.ulplanet.trip.service.CallRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by makun on 2016/1/14.
 */
@Controller
@RequestMapping(value = "/callRecord")
public class CallRecordController {
    @Resource
    private CallRecordService callRecordService;

    @RequestMapping(value = "/addRecord",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addRecord(CallRecord callRecord){
        return callRecordService.addRecord(callRecord);
    }
}
