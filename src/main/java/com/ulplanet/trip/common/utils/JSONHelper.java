package com.ulplanet.trip.common.utils;

import com.alibaba.druid.support.json.JSONUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by makun on 2015/9/18.
 */
public class JSONHelper extends JSONUtils{

    public static List<Map<String,Object>> toMapList(List<?> list){
        List<Map<String,Object>> mapList = new ArrayList<>();
        try {
            for (Object aList : list) {
                Map<String, Object> map = new HashMap<>();
                Method[] methods = aList.getClass().getMethods();
                for (Method m : methods) {
                    if (m.getName().startsWith("get") && !m.getName().contains("Class")) {
                        String name = m.getName();
                        String first = name.substring(3, 4).toLowerCase();
                        name = name.substring(4);
                        map.put(first + name, m.invoke(aList));
                    }
                }
                mapList.add(map);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return mapList;
    }

    public static List<Map<String,Object>> toMapList(List<?> list,String... strs){
        List<Map<String,Object>> mapList = new ArrayList<>();
        try {
            for (Object aList : list) {
                Map<String, Object> map = new HashMap<>();
                Class clazz = aList.getClass();
                for (String name : strs) {
                    PropertyDescriptor pd = new PropertyDescriptor(name, clazz);
                    Method rM = pd.getReadMethod();//获得读方法
                    map.put(name, rM.invoke(aList));
                }
                mapList.add(map);
            }
        } catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return mapList;
    }

}
