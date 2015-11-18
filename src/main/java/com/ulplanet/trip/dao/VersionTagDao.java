package com.ulplanet.trip.dao;


import com.ulplanet.trip.bean.VersionTag;
import com.ulplanet.trip.common.persistence.BaseDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;

/**
 * Created by makun on 2015/11/17.
 */
@MyBatisDao
public interface VersionTagDao extends BaseDao {
    VersionTag get(VersionTag versionTag);
    int insert(VersionTag versionTag);
    int update(VersionTag versionTag);
}
