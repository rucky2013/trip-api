package com.ulplanet.trip.service;

import com.ulplanet.trip.bean.Apk;

import java.util.Map;

/**
 * Created by Administrator on 2015/9/17.
 */

public interface ApkService {
    Map<String, Object> findList(int page, int size, String searchValue);
    int saveApk(Apk apk);
    Map<String, Object> findByParams(String name, String packageName);
}
