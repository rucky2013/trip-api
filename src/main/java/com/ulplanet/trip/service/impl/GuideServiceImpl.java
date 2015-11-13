package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.common.persistence.Parameter;
import com.ulplanet.trip.common.utils.FileManager;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.GuideDao;
import com.ulplanet.trip.service.GuideService;
import com.ulplanet.trip.util.LocalContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("guideService")
public class GuideServiceImpl implements GuideService {

    @Autowired
    private GuideDao guideDao;

    @Override
    public Map<String, Object> findList(int page, int size) {
        Parameter parameter = new Parameter(new Object[][]{
                {"city", LocalContext.getCity()}
        });

        int count = this.guideDao.findCount(parameter);

        int start = (page - 1) * size;
        if (start >= count) {
            start = 0;
        }

        Parameter pagingParameter = new Parameter(new Object[][]{
                {"start", start}, {"limit", size},
                {"city", LocalContext.getCity()}
        });

        List<Map<String, Object>> guideList = this.guideDao.findList(pagingParameter);

        List<Map<String, Object>> datas = new ArrayList<>();
        for (Map<String, Object> guideMap : guideList) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", guideMap.get("id"));
            String path = Objects.toString(guideMap.get("path"));
            data.put("url", FileManager.getFileUrlByRealpath(path));
            datas.add(data);
        }

        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_PAGE, page);
        result.put(Constants.RETURN_FIELD_SIZE, size);
        result.put(Constants.RETURN_FIELD_TOTAL, count);
        result.put(Constants.RETURN_FIELD_DATA, datas);
        return result;
    }

    @Override
    public Map<String, Object> findDetail(String id) {
        Parameter parameter = new Parameter(new Object[][]{
                {"id", id}
        });
        Map<String, Object> guide = this.guideDao.findGuide(parameter);

        if (guide != null) {
            List<Map<String, Object>> guideFiles = this.guideDao.findGuideFiles(id);
            List<String> guidePathList = new ArrayList<>();
            for (Map<String, Object> guideFile : guideFiles) {
                String url = FileManager.getFileUrlByRealpath(Objects.toString(guideFile.get("path")));
                String type = Objects.toString(guideFile.get("type"));
                if (Constants.GUIDEFILE_TYPE_LIST.equals(type)) {
                    guidePathList.add(url);
                }
            }

            guide.put("urllist", guidePathList);
        }

        Map<String, Object> result = new HashMap<>();
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
        result.put(Constants.RETURN_FIELD_DATA, guide);
        return result;
    }

}
