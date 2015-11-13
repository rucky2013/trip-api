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
 * 行程接口测试
 * Created by zhangxd on 15/9/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class JourneyControllerTest extends AbstractContextControllerTests {

    @Test
    public void adviceTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/journey/advice")
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

    @Test
    public void listTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/journey/list")
                .header(Constants.HEADER_IMEI, "zhangxd")
                .header(Constants.HEADER_TOKEN, "471c4e984315b5aa181bfd7af4c83e0a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("type", "1")
                .param("stype", "")
                .param("order", "")
                .param("longitude", "116.451114")
                .param("latitude", "39.940012"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.data").isArray())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void foodTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/journey/food")
                .header(Constants.HEADER_IMEI, "zhangxd")
                .header(Constants.HEADER_TOKEN, "471c4e984315b5aa181bfd7af4c83e0a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "721174E6-9AC1-4C25-91BF-E74818A0192B")
                .param("longitude", "116.451114")
                .param("latitude", "39.940012"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.score").isNotEmpty())
                .andExpect(jsonPath("$.data.distance").isNotEmpty())
                .andExpect(jsonPath("$.data.name").isNotEmpty())
                .andExpect(jsonPath("$.data.address").exists())
                .andExpect(jsonPath("$.data.description").exists())
                .andExpect(jsonPath("$.data.price").exists())
                .andExpect(jsonPath("$.data.worktime").exists())
                .andExpect(jsonPath("$.data.phone").exists())
                .andExpect(jsonPath("$.data.car").exists())
                .andExpect(jsonPath("$.data.longitude").exists())
                .andExpect(jsonPath("$.data.latitude").exists())
                .andExpect(jsonPath("$.data.mapurl").exists())
                .andExpect(jsonPath("$.data.urllist").isArray())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void scenicTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/journey/scenic")
                .header(Constants.HEADER_IMEI, "zhangxd")
                .header(Constants.HEADER_TOKEN, "471c4e984315b5aa181bfd7af4c83e0a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "915F3373-1350-4115-B9F6-D80D99E0533D")
                .param("longitude", "116.451114")
                .param("latitude", "39.940012"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.score").isNotEmpty())
                .andExpect(jsonPath("$.data.distance").isNotEmpty())
                .andExpect(jsonPath("$.data.name").isNotEmpty())
                .andExpect(jsonPath("$.data.address").exists())
                .andExpect(jsonPath("$.data.description").exists())
                .andExpect(jsonPath("$.data.price").exists())
                .andExpect(jsonPath("$.data.hours").exists())
                .andExpect(jsonPath("$.data.tips").exists())
                .andExpect(jsonPath("$.data.car").exists())
                .andExpect(jsonPath("$.data.longitude").exists())
                .andExpect(jsonPath("$.data.latitude").exists())
                .andExpect(jsonPath("$.data.mapurl").exists())
                .andExpect(jsonPath("$.data.urllist").isArray())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void shopTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/journey/shop")
                .header(Constants.HEADER_IMEI, "zhangxd")
                .header(Constants.HEADER_TOKEN, "471c4e984315b5aa181bfd7af4c83e0a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "8A334425-4806-4B2D-AEE8-7812547FD5A2")
                .param("longitude", "116.451114")
                .param("latitude", "39.940012"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.score").isNotEmpty())
                .andExpect(jsonPath("$.data.distance").isNotEmpty())
                .andExpect(jsonPath("$.data.name").isNotEmpty())
                .andExpect(jsonPath("$.data.address").exists())
                .andExpect(jsonPath("$.data.description").exists())
                .andExpect(jsonPath("$.data.worktime").exists())
                .andExpect(jsonPath("$.data.longitude").exists())
                .andExpect(jsonPath("$.data.latitude").exists())
                .andExpect(jsonPath("$.data.mapurl").exists())
                .andExpect(jsonPath("$.data.urllist").isArray())
                .andExpect(jsonPath("$.data.advicelist").isArray())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}