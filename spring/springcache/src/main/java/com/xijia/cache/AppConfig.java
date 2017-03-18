package com.xijia.cache;

import java.util.Random;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

/**
 * 参考资料 http://jinnianshilongnian.iteye.com/blog/2001040/
 * http://docs.spring.io/spring/docs/3.2.8.RELEASE/spring-framework-reference/html/cache.html
 * @author xijia
 *
 */
@Repository
public class AppConfig {
	
	/**
	 * 指定key为id
	 * @param id
	 * @param nothingImportant
	 * @return
	 */
	@Cacheable(value="mycache",key="#id")
	public String getString(String id,boolean nothingImportant){
		String result = String.valueOf(new Random().nextInt(100));
		return result;
	}
	
	@Cacheable("mycache")
	public String getString1(String id){
		String result = String.valueOf(new Random().nextInt(100));
		return result;
	}
	
	@Cacheable(value="mycache")
	public String getString2(String id){
		String result = String.valueOf(new Random().nextInt(100));
		return result;
	}
	
	@Cacheable(value="mycache",key="#id",condition="#id.length()<5")
	public String testConditionKey(String id){
		String result = String.valueOf(new Random().nextInt(100));
		return result;
	}
	
	@Cacheable(value="mycache",key="#id",unless="#result.length()<5")
	public String testUnless(String id){
		String result = String.valueOf(new Random().nextInt(100));
		return result;
	}
	
	@CachePut(value = "mycache", key = "#user.id")  
	public User save(User user) {  
		return user;
	}  
	
	@Cacheable(value = "mycache", key = "#id")  
	public User getUser(Long id) {   
		System.out.println("get new user");
	    return new User();  
	}
	
	@CacheEvict(value = "mycache", key = "#user.id") //移除指定key的数据  
	public User delete(User user) {  
	    return user;  
	}  
	
	@CacheEvict(value = "mycache", allEntries = true) //移除所有数据  
	public void deleteAll() {  
	}  
	
	@Caching(  
	        evict = {  
	                @CacheEvict(value = "user", key = "#user.id"),  
	                @CacheEvict(value = "user", key = "#user.username"),  
	                @CacheEvict(value = "user", key = "#user.email")  
	        }  
	)  
	public User delete11(User user) {
		return user;
	}

	/**
	 * 组合
	 * @param user
	 * @return
	 */
	@Caching(
			put = {  
            @CachePut(value = "user", key = "#user.id"),  
            @CachePut(value = "user", key = "#user.username"),  
            @CachePut(value = "user", key = "#user.email")  
			}
	)
	public User saveUser(User user) {  
		return user;
	}
	
	
	@UserSaveCache
	public User saveUser_customAnnotation(User user){
		return user;
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
		
		User user = new User();
		user.setId(1L);
		user.setUsername("dsfs");
		app.save(user);
		User user2 = app.getUser(1L);
		print("user:"+user2.getUsername());
		app.delete(user2);
		User user3 = app.getUser(1L);
		print("user name :"+user3.getUsername());
	
	}
	
	public static void print(String str){
		System.out.println(str);
	}
}