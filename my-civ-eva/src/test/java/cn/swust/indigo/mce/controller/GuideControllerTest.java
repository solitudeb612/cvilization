package cn.swust.indigo.mce.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
class GuideControllerTest {
    @Autowired GuideController controller;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }
    @Test
    @WithMockUser(username = "admin")
    void getById() {
        MvcResult mvcResult = null;
        try {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("guideId","2");
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/guide/get")
                    .params(map)
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(MockMvcResultMatchers.status().isOk()) //等同于Assert.assertEquals(200,status);
                    //.andExpect(MockMvcResultMatchers.content().string("hello lvgang")) //等同于Assert.assertEquals("hello lvgang",content);
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            int status = mvcResult.getResponse().getStatus(); //得到返回代码
            String content = mvcResult.getResponse().getContentAsString(); //得到返回结果
            System.out.println(content);
            Assertions.assertEquals(200, status); //断⾔，判断返回代码是否正确
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}