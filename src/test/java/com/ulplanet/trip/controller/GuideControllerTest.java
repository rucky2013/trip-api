package com.ulplanet.trip.controller;

import com.ulplanet.trip.AbstractContextControllerTests;
import com.ulplanet.trip.constant.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 导购接口测试
 * Created by zhangxd on 15/9/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class GuideControllerTest extends AbstractContextControllerTests {

    @Test
    public void listTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/guide/list")
                .header(Constants.HEADER_IMEI, "zhangxd")
                .header(Constants.HEADER_TOKEN, "471c4e984315b5aa181bfd7af4c83e0a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.data").isArray())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void detailTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/guide/detail")
                .header(Constants.HEADER_IMEI, "zhangxd")
                .header(Constants.HEADER_TOKEN, "471c4e984315b5aa181bfd7af4c83e0a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "57CCF302-7BB6-4B9A-BB0A-643DF8E1374B"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.description").exists())
                .andExpect(jsonPath("$.data.name").exists())
                .andExpect(jsonPath("$.data.urllist").isArray())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}