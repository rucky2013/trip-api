package com.ulplanet.trip.controller;

import com.ulplanet.trip.service.StockService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/19.
 */
@Controller
@RequestMapping(value="/stock")
public class StockController {



    @Resource
    private StockService stockService;

    @RequestMapping(value = "/findOrderList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> findOrderList(@RequestParam("publicKey")String publicKey,@RequestParam("sign")String encryptionKey){
        return stockService.findOrderList(publicKey,encryptionKey);
    }

    @RequestMapping(value = "/addPhone",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addPhone(@RequestParam("publicKey")String publicKey,@RequestParam("sign")String encryptionKey
            ,@RequestParam("code")String code,@RequestParam("stockOrderId")String stockOrderId){
        return stockService.savePhoneInfo(code,stockOrderId,publicKey,encryptionKey);
    }

    @RequestMapping(value = "/changeStatus",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> changeStatus(@RequestParam("publicKey")String publicKey,@RequestParam("sign")String encryptionKey
            ,@RequestParam("code")String code,@RequestParam("status")String status){
        return stockService.updatePhoneStatus(code,status,publicKey,encryptionKey);
    }


}
