package com.sishuok.mvc.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-12-26
 * <p>Version: 1.0
 */

//XML风格
@RunWith(SpringJUnit4ClassRunner.class)
//测试环境使用，用来表示测试环境使用的ApplicationContext将是WebApplicationContext类型的
//value指定web应用的根
@WebAppConfiguration(value = "src/main/webapp")  
//指定容器层次，即spring-config.xml是父容器，而spring-mvc.xml是子容器
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = "classpath:spring-config.xml"),
        @ContextConfiguration(name = "child", locations = "classpath:spring-mvc.xml")
})

//注解风格
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration(value = "src/main/webapp")
//@ContextHierarchy({
//        @ContextConfiguration(name = "parent", classes = AppConfig.class),
//        @ContextConfiguration(name = "child", classes = MvcConfig.class)
//})
public class UserControllerWebAppContextSetupTest {

	//注入web环境的ApplicationContext容器
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
    	//创建一个MockMvc进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testView() throws Exception {
    	/*1、mockMvc.perform执行一个请求；
    	2、MockMvcRequestBuilders.get("/user/1")构造一个请求
    	3、ResultActions.andExpect添加执行完成后的断言
    	4、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情，比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
    	5、ResultActions.andReturn表示执行完成后返回相应的结果。*/
        mockMvc
                .perform(get("/user/1"))	//执行请求  
                .andExpect(view().name("user/view")) //验证viewName	
                .andExpect(forwardedUrl("/WEB-INF/jsp/user/view.jsp")) //验证视图渲染时forward到的jsp 
                .andExpect(model().attributeExists("user"))	//验证存储模型数据  
                .andExpect(content().string(Matchers.contains("你好")))
                .andExpect(status().isOk())//验证状态码  
                .andDo(print());  //输出MvcResult到控制台  
        
    }


}
