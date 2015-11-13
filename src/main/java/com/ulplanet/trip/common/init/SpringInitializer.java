package com.ulplanet.trip.common.init;

import com.ulplanet.trip.base.AppContext;
import com.ulplanet.trip.base.ServletContextHolder;
import com.ulplanet.trip.base.SpringBeanFactoryHodler;
import com.ulplanet.trip.base.SpringContextHolder;
import com.ulplanet.trip.common.config.Global;
import com.ulplanet.trip.common.utils.dict.DictUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * 
 * 初始化系统环境(在Spring加载完后)
 * @author zhangxd
 * @date 2015年7月12日
 *
 */
public class SpringInitializer implements ApplicationContextAware, ServletContextAware, BeanFactoryPostProcessor {

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.setApplicationContext(applicationContext);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		ServletContextHolder.setServletContext(servletContext);
	}
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		SpringBeanFactoryHodler.setBeanFactory(beanFactory);
		//初始化AppContext(*必须放在最前面).
		AppContext.setAppPath(ServletContextHolder.getServletContext().getRealPath("/"));
		AppContext.setDbType(beanFactory.resolveEmbeddedValue("${jdbc.type}"));

        if (Global.TRUE.equals(Global.getConfig("init.dict"))) {
            //系统初始化操作
            DictUtil.loadDict();
        }
	}
}