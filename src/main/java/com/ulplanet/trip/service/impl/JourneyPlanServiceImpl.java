package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.bean.*;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.FlightDao;
import com.ulplanet.trip.dao.JourneyPlanDao;
import com.ulplanet.trip.dao.VersionTagDao;
import com.ulplanet.trip.service.JourneyPlanService;
import com.ulplanet.trip.service.JourneyService;
import com.ulplanet.trip.service.UserService;
import com.ulplanet.trip.util.LocalContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by makun on 2015/9/22.
 */
@Service("journeyPlanService")
public class JourneyPlanServiceImpl implements JourneyPlanService {

    @Resource
    private JourneyPlanDao journeyPlanDao;
    @Resource
    private UserService userService;
    @Resource
    private FlightDao flightDao;
    @Resource
    private JourneyService journeyService;
    @Resource
    private VersionTagDao versionTagDao;


    @Override
    public Map<String, Object> findList(String tag) {
        if(tag==null)tag = "";
        VersionTag versionTag = new VersionTag();
        versionTag.setId(LocalContext.getUser().getGroup());
        versionTag.setType(2);
        versionTag = versionTagDao.get(versionTag);
        Map<String, Object> datas = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        if(versionTag==null){
            versionTag = new VersionTag();
        }
        if( tag.equals(versionTag.getTag())){
            datas.put("newRecord",0);
            datas.put("tag",tag);
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
            result.put(Constants.RETURN_FIELD_DATA, datas);
            return  result;
        }
        String group = LocalContext.getUser().getGroup();
        User user = LocalContext.getUser();
        user.setGroup(group);
        List<Map<String,Object>> guides = new ArrayList<>();
        List<JourneyPlans> journeyPlans;
        String passport = user.getPassport();
        user = new User();
        user.setGroup(group);
        user.setType("0");
        journeyPlans = journeyPlanDao.queryAllPlanByGroup(group);
        if (journeyPlans.size()==0){
            datas.put("plans",journeyPlans);
            datas.put("guide",guides);
            datas.put("passport",passport);
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
            result.put(Constants.RETURN_FIELD_DATA, datas);
            return result;
        }
        List<User> users  = userService.findUserByParam(user);
        for(User u : users){
            Map<String,Object> temp = new HashMap<>();
            temp.put("guideName",u.getName());
            temp.put("guidePassport",u.getPassport());
            temp.put("guidePhone",u.getPhone());
            temp.put("guideEmail",u.getEmail());
            guides.add(temp);
        }
        int num = 0;
        List<JourneyPlans> jp1 = new ArrayList<>();
        List<Map<String,Object>> planList = new ArrayList<>();
        String title = "";
        for(JourneyPlans jp : journeyPlans){
            if(num == 0){
                num = jp.getDayNumber();
                title = jp.getDayTitle();
            }
            if(num != 0 && num != jp.getDayNumber()) {
                Map<String,Object> temp = new HashMap<>();
                temp.put("title",title);
                temp.put("day",num);
                temp.put("plan",jp1);
                planList.add(temp);
                jp1 = new ArrayList<>();
                num = jp.getDayNumber();
                title = jp.getDayTitle();
            }
            jp1.add(jp);
        }
        Map<String,Object> temp = new HashMap<>();
        temp.put("title",title);
        temp.put("day",num);
        temp.put("plan",jp1);
        planList.add(temp);
        datas.put("plans", planList);
        datas.put("guide",guides);
        datas.put("passport",passport);
        datas.put("newRecord",1);
        datas.put("tag",versionTag.getTag());
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, datas);
        return  result;
    }

    @Override
    public Map<String, Object> getInfo(int type,String info,double longitude,double latitude) {
        Map<String,Object> result = new HashMap<>();
        if(type==6) {//餐饮
            return journeyService.findFood(info,longitude,latitude);
        }
        if(type==7){//景点
            return journeyService.findScenic(info,longitude,latitude);
        }
        if(type==8){//购物
            return journeyService.findShop(info,longitude,latitude);
        }
        if(type==3){//航班
            Flight flight = flightDao.get(info);
            if(flight!=null) {
                result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
                result.put(Constants.RETURN_FIELD_DATA, flight);
                return result;
            }
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_FAILURE);
            result.put(Constants.RETURN_FIELD_MESSAGE, "信息不存在");
            return result;
        }
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_FAILURE);
        result.put(Constants.RETURN_FIELD_MESSAGE, "信息不存在");
        return result;
    }

}
