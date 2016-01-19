package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.bean.QingmaRecord;
import com.ulplanet.trip.common.utils.IdGen;
import com.ulplanet.trip.dao.QingmaRecordDao;
import com.ulplanet.trip.service.QingmaRecordService;
import com.ulplanet.trip.util.QingmaValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by makun on 2016/1/16.
 */
@Service("qingmaRecordService")
public class QingmaRecordServiceImpl implements QingmaRecordService {

    Logger logger = LoggerFactory.getLogger(QingmaRecordServiceImpl.class);
    @Resource
    private QingmaRecordDao qingmaRecordDao;

    @Override
    public Map<String, Object> saveRecord(QingmaRecord qingmaRecord) {
        String respCode = QingmaValidator.validator(qingmaRecord.getTimestamp(), qingmaRecord.getSig());
        logger.error(respCode);
        Map<String,Object> map = new HashMap<>();
        if(!"00000".equals(respCode)){
            map.put("respCode", respCode);
            return map;
        }
        qingmaRecord.setId(IdGen.uuid());
        int i = qingmaRecordDao.insert(qingmaRecord);
        if(i > 0) {
            map.put("respCode", "00000");
        }else{
            map.put("respCode", "00001");
        }
        return map;
    }
}
