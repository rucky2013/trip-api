package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.bean.Apk;
import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.JSONHelper;
import com.ulplanet.trip.common.utils.StringHelper;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.ApkDao;
import com.ulplanet.trip.service.ApkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by makun on 2015/9/17.
 */
@Service("apkService")
public class ApkServiceImpl implements ApkService {
    @Resource
    private ApkDao apkDao;

    @Override
    public Map<String, Object> findList(int page, int size, String searchValue) {
        Page<Apk> uploadAPKPage = new Page<>(page,size);
        Apk uploadAPK = new Apk();
        uploadAPK.setPage(uploadAPKPage);
        List<Apk> list = apkDao.findList(uploadAPK);
        List<Map<String,Object>> datas = JSONHelper.toMapList(list, "id", "name", "version", "url");
        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_TOTAL, uploadAPKPage.getCount());
        result.put(Constants.RETURN_FIELD_DATA, datas);
        return result;
    }

    @Override
    public int saveApk(Apk apk) {
        List<Apk> list = apkDao.findByParam(apk);
        int i = 0;
        if(list.size()>0){
            Apk apk1 = list.get(0);
            apk1.setVersion(apk.getVersion());
            apk1.setName(apk.getName());
            apk1.setUrl(apk.getUrl());
            apk1.setDescription(apk.getDescription());
            apk1.preUpdate();
            i = apkDao.update(apk1);
        }else{
            apk.preInsert();
            i = apkDao.insert(apk);
        }
        return i;
    }

    @Override
    public Map<String, Object> findByParams(String name,String packageName) {
        Apk apk = new Apk();
        if(StringHelper.isNotBlank(name)){
            apk.setName(name);
        }
        if(StringHelper.isNotBlank(packageName)){
            apk.setPackageName(packageName);
        }
        List<Apk> list = apkDao.findByParam(apk);
        List<Map<String,Object>> datas = new ArrayList<>();
        datas = JSONHelper.toMapList(list,"packageName","name","version","url","md5","size");
        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, datas);
        return result;

    }
}
