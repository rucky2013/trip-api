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
 * 信息接口测试
 * Created by zhangxd on 15/9/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class InfoControllerTest extends AbstractContextControllerTests {

    @Test
    public void getEmergencyTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/info/emergency")
                .header(Constants.HEADER_IMEI, "zhangxd")
                .header(Constants.HEADER_TOKEN, "471c4e984315b5aa181bfd7af4c83e0a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.embassy_city").exists())
                .andExpect(jsonPath("$.data.embassy_call").exists())
                .andExpect(jsonPath("$.data.police").exists())
                .andExpect(jsonPath("$.data.road_emerg").exists())
                .andExpect(jsonPath("$.data.unionpay_call").exists())
                .andExpect(jsonPath("$.data.city").exists())
                .andExpect(jsonPath("$.data.embassy_addr").exists())
                .andExpect(jsonPath("$.data.fire").exists())
                .andExpect(jsonPath("$.data.emergency").exists())
                .andExpect(jsonPath("$.data.embassy_time").exists())
                .andExpect(jsonPath("$.data.ambulance").exists())
                .andExpect(jsonPath("$.data.sea_emerg").exists())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getCarTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/info/car")
                .header(Constants.HEADER_IMEI, "zhangxd")
                .header(Constants.HEADER_TOKEN, "471c4e984315b5aa181bfd7af4c83e0a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.taxi").exists())
                .andExpect(jsonPath("$.data.order").exists())
                .andExpect(jsonPath("$.data.car").exists())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getInitTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/info/init")
                .header(Constants.HEADER_IMEI, "zhangxd")
                .header(Constants.HEADER_TOKEN, "471c4e984315b5aa181bfd7af4c83e0a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.flight").isArray())
                .andExpect(jsonPath("$.data.passport").exists())
                .andExpect(jsonPath("$.data.hotels").isArray())
                .andExpect(jsonPath("$.data.tourists").isArray())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getWeatherTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/info/weather")
                .header(Constants.HEADER_IMEI, "zhangxd")
                .header(Constants.HEADER_TOKEN, "471c4e984315b5aa181bfd7af4c83e0a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("longitude", "116.451114")
                .param("latitude", "39.940012"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.temp").isNotEmpty())
                .andExpect(jsonPath("$.data.maxtemp").isNotEmpty())
                .andExpect(jsonPath("$.data.description").isNotEmpty())
                .andExpect(jsonPath("$.data.mintemp").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
