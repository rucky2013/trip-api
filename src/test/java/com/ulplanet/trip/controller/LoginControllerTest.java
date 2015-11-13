package com.ulplanet.trip.controller;

import com.ulplanet.trip.AbstractContextControllerTests;
import com.ulplanet.trip.constant.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * 登录测试
 * Created by zhangxd on 15/9/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class LoginControllerTest extends AbstractContextControllerTests {

    @Test
    public void doLoginTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/login/doLogin")
                .header(Constants.HEADER_IMEI, "zhangxd")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("userid", "20150825001")
                .param("userpwd", "18515084455")
                .param("longitude", "116.451114")
                .param("latitude", "39.940012"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"))
                .andExpect(jsonPath("$.data.userid").value("20150825001"))
                .andExpect(jsonPath("$.data.chatid").isNotEmpty())
                .andExpect(jsonPath("$.data.chatname").isNotEmpty())
                .andExpect(jsonPath("$.data.token").isNotEmpty())
                .andExpect(jsonPath("$.data.type").value(anyOf(is("0"), is("1"))))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

}
