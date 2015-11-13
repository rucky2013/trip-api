package com.ulplanet.trip;

import com.ulplanet.trip.filter.RequestFilter;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * 单元测试基类
 * Created by zhangxd on 15/9/1.
 */
@WebAppConfiguration
@ContextConfiguration(value = {"classpath*:spring-mvc.xml",
        "classpath*:spring-context.xml",
        "classpath*:spring-context-filemanager.xml",
        "classpath*:spring-context-jedis.xml"})
public class AbstractContextControllerTests {

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        Filter filter = new RequestFilter();
        FilterConfig config = new FilterConfig() {
            @Override
            public String getFilterName() {
                return "requestFilter";
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public String getInitParameter(String name) {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("excludingPattern", "/login; /druid/; /files/; \\.jpg$; \\.gif$; \\.png$; \\.ico$; \\.js$; \\.css$; \\.html$;");
                return parameters.get(name);
            }

            @Override
            public Enumeration<String> getInitParameterNames() {
                return null;
            }
        };
        filter.init(config);
        this.mockMvc = webAppContextSetup(this.wac).addFilter(filter).build();
    }

}
