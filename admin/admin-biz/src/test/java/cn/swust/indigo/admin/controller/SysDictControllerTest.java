package cn.swust.indigo.admin.controller;//package com.pig4cloud.pigx.admin.controller;

import cn.swust.indigo.admin.AdminApplicationTest;
import cn.swust.indigo.admin.controller.mock.MockAdminUser;
import cn.swust.indigo.admin.entity.po.SysDict;
import cn.swust.indigo.common.core.util.R;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/**
 * 测试SysdictController
 */
@SuppressWarnings("ALL")
@SpringBootTest(classes = AdminApplicationTest.class)
public class SysDictControllerTest {
    @Autowired
    SysDictController controller;
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

    /**
     * 查询字典项的内容
     */
    @Test
    @WithMockUser(username = "admin")
    public void getByIdTest() {
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/dict/1")
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

    //	@WithMockUser(username = "admin", authorities = {"sys_dict_edit"})
//  @WithUserDetails(value = "admin", userDetailsServiceBeanName = "LoginUserDetailsServiceImpl")
    @Test
    @MockAdminUser
    public void updateDictTest() {
         PasswordEncoder ENCODER = new BCryptPasswordEncoder();
        String psw = ENCODER.encode("88888888");
        System.out.println(psw);
        SysDict sysDict = new SysDict();
        sysDict.setDictId(3);
        sysDict.setType("cstestUpdate");
        sysDict.setDescription("我的测试");
        sysDict.setRemarks("cstest");
        sysDict.setSystem("1");
        String requestJson = JSON.toJSONString(sysDict);
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/admin/dict")
                    .content(requestJson)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
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

    @Test
    public void getByIdTest1() {
        R result = controller.getById(1);
        System.out.printf(result.toString());
    }


}