package com.xijia.cache;

import java.util.Random;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

@Repository
public class AppConfig {
	
	@Cacheable("mycache")
	public String getString(String id){
		String result = String.valueOf(new Random().nextInt(100));
		return result;
	}
	
	
	/**
	 * 1 需要在spring-context.xml中配置cacheManager,定义名为mycache的bean
	 * 2 加载spring-context.xml文件
	 * @param args
	 */
	public static void main(String [] args){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
		context.start();
		
		AppConfig app = context.getBean(AppConfig.class);
		System.out.println(app.getString("1"));
		System.out.println(app.getString("1"));
		System.out.println(app.getString("1"));
		System.out.println(app.getString("1"));
		System.out.println(app.getString("1"));
	}
}