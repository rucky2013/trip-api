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
 * 位置接口测试
 * Created by zhangxd on 15/9/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class PositionControllerTest extends AbstractContextControllerTests {

    @Test
    public void putPointTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/position/put")
                .header(Constants.HEADER_IMEI, "zhangxd")
                .header(Constants.HEADER_TOKEN, "471c4e984315b5aa181bfd7af4c83e0a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("longitude", "116.451114")
                .param("latitude", "39.940012"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.data.country").isNotEmpty())
                .andExpect(jsonPath("$.data.city").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void getPointTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/position/get")
                .header(Constants.HEADER_IMEI, "zhangxd")
                .header(Constants.HEADER_TOKEN, "471c4e984315b5aa181bfd7af4c83e0a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.data").isArray())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

}
