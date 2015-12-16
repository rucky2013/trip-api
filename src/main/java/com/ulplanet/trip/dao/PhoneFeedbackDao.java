package com.ulplanet.trip.dao;

import com.ulplanet.trip.bean.PhoneFeedback;
import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;

import java.util.List;

/**
 * Created by makun on 2015/12/15.
 */
@MyBatisDao
public interface PhoneFeedbackDao extends CrudDao<PhoneFeedback> {
    List<PhoneFeedback> getPhoneFunction();
    int insertPhoneFunction(PhoneFeedback phoneFeedback);
    int updatePhoneFunction(PhoneFeedback phoneFeedback);
    int inserts(List<PhoneFeedback> list);

}
