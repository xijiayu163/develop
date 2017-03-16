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
	 * 1 ��Ҫ��spring-context.xml������cacheManager,������Ϊmycache��bean
	 * 2 ����spring-context.xml�ļ�
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