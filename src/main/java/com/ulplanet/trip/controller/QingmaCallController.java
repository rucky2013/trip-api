package com.ulplanet.trip.controller;

import com.ulplanet.trip.bean.QingmaRecord;
import com.ulplanet.trip.service.QingmaRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by makun on 2016/1/16.
 */
@RestController
@RequestMapping(value = "/qingmayun")
public class QingmaCallController{


    @Resource
    private QingmaRecordService qingmaRecordService;
    /**
     * 呼叫发起请求
     * @return
     */
    @RequestMapping(value = "/callAuth",method = RequestMethod.POST)
    public String callAuth(){
        return "{\"respCode\": \"00000\"}";
    }

    /**
     * 呼叫建立请求
     */
    @RequestMapping(value = "/callEstablish",method = RequestMethod.POST)
    public String callEstablish(){
        return "{\"respCode\": \"00000\"}";
    }

    @RequestMapping(value = "/hangup",method = RequestMethod.POST)
    public Map<String,Object> hangup(QingmaRecord qingmaRecord){
        return qingmaRecordService.saveRecord(qingmaRecord);
    }




}
