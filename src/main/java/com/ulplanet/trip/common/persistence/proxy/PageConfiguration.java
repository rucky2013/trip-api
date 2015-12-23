package com.ulplanet.trip.common.persistence.proxy;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

/**
 * 
 * 自定义Mybatis的配置，扩展.
 * @author zhangxd
 * @date 2015年7月12日
 *
 */
public class PageConfiguration extends Configuration {
	
    public PageConfiguration() {
        super();
        this.mapperRegistry = new PaginationMapperRegistry(this);
    }

    @Override
    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    @Override
    public boolean hasMapper(Class<?> type) {
        return mapperRegistry.hasMapper(type);
    }
}
