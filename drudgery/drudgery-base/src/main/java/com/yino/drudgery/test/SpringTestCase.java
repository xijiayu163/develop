package com.yino.drudgery.test;

import java.io.FileNotFoundException;

import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import junit.framework.TestCase;

//指定bean注入的配置文件
//@ContextHierarchy({
//  @ContextConfiguration(locations = "classpath:spring-context.xml"),
//  //@ContextConfiguration(name = "child", locations = "classpath:spring-mvc.xml")
//})
//@RunWith(SpringJUnit4ClassRunner.class)


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:spring-context.xml"
})
public abstract class SpringTestCase extends TestCase{
	static {
//        try {
//			Log4jConfigurer.initLogging("classpath:log4j.properties");
//			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
//			context.start();
			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
    }	
}
