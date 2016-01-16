package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.bean.QingmaRecord;
import com.ulplanet.trip.common.utils.IdGen;
import com.ulplanet.trip.dao.QingmaRecordDao;
import com.ulplanet.trip.service.QingmaRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by makun on 2016/1/16.
 */
@Service("qingmaRecordService")
public class QingmaRecordServiceImpl implements QingmaRecordService {

    @Resource
    private QingmaRecordDao qingmaRecordDao;

    @Override
    public Map<String, Object> saveRecord(QingmaRecord qingmaRecord) {
        qingmaRecord.setId(IdGen.uuid());
        int i = qingmaRecordDao.insert(qingmaRecord);
        Map<String,Object> map = new HashMap<>();
        if(i > 0) {
            map.put("respCode", "00000");
        }else{
            map.put("respCode", "00001");
        }
        return map;
    }
}
