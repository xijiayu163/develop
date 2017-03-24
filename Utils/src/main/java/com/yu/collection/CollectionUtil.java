package com.yu.collection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.util.CollectionUtils;

import com.yu.date.DateUtil;


/**
 * 集合工具类
 * @author yuxijia
 *
 */
public class CollectionUtil {

	/**
	 * 获取List集合的第一个元素
	 * 
	 * @param list
	 * @return 如果集合为null，则返回null；否则返回指定的泛型对象
	 */
	public static <E>  E getFirst(List<E> list){
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		else{
			return list.get(0);
		}
	}
	
	/**
	 * 从集合中查找第一个指定属性为指定值的元素
	 *
	 * @author yuxijia
	 * @date 2017年1月10日 
	 * @since 3.9.0
	 *
	 * @param list 集合
	 * @param propertyName 属性名
	 * @param value 待匹配的属性值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, E> T getFirstMatch(List<T> list,String propertyName,E value){
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		String firstWord = propertyName.substring(0,1).toUpperCase();
		String methodName = "get"+firstWord+propertyName.substring(1,propertyName.length());
		T t = list.get(0);
		Method declaredMethod;
		try {
			declaredMethod = t.getClass().getDeclaredMethod(methodName);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} 
		for(T t1:list){
			E objValue;
			try {
				objValue = (E)(declaredMethod.invoke(t1, new Object[]{}));
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			} 
			if(value.equals(objValue)){
				return t1;
			}
		}
		return null;
	}
	
	/**
	 * 从集合中查找所有指定属性为指定值的子集
	 *
	 * @author yuxijia
	 * @date 2017年1月10日 
	 * @since 3.9.0
	 *
	 * @param list 集合
	 * @param propertyName 属性名
	 * @param value 待匹配的属性值
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@SuppressWarnings("unchecked")
	public static <T, E> List<T> getAllMatch(List<T> list,String propertyName,E value){
		List<T> matchList = new ArrayList<T>();
		if(CollectionUtils.isEmpty(list)){
			return matchList;
		}
		String firstWord = propertyName.substring(0,1).toUpperCase();
		String methodName = "get"+firstWord+propertyName.substring(1,propertyName.length());
		T t = list.get(0);
		Method declaredMethod;
		try {
			declaredMethod = t.getClass().getDeclaredMethod(methodName);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		for(T t1:list){
			E objValue;
			try {
				objValue = (E)(declaredMethod.invoke(t1, new Object[]{}));
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			} 
			if(value.equals(objValue)){
				matchList.add(t1);
			}
		}
		return matchList;
	}	
	
	/**
	 * 从集合中查找指定属性值组成的集合
	 *
	 * @author yuxijia
	 * @date 2017年1月10日 
	 * @since 3.9.0
	 *
	 * @param list	集合
	 * @param propertyName 属性名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T,V> List<V> getSingleValueList(List<T> list,String propertyName,Class<V> clazz){
		List<V> matchList = new ArrayList<V>();
		if(CollectionUtils.isEmpty(list)){
			return matchList;
		}
		String firstWord = propertyName.substring(0,1).toUpperCase();
		String methodName = "get"+firstWord+propertyName.substring(1,propertyName.length());
		T t = list.get(0);
		Method declaredMethod;
		try {
			declaredMethod = t.getClass().getDeclaredMethod(methodName);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} 
		for(T t1:list){
			V objValue;
			try {
				objValue = (V)(declaredMethod.invoke(t1, new Object[]{}));
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			} 
			matchList.add(objValue);
		}
		return matchList;
	}

	public static void main(String[] args){	
		List<Student> students = new ArrayList<Student>();
		for(int i=0;i<10;i++){
			Student stu = new Student();
			stu.setName("name"+i);
			stu.setBirthday(new Date());
			stu.getBirthday().setMonth(new Random().nextInt(12));
			students.add(stu);
		}
		Collections.sort(students, new Comparator<Student>() {
			public int compare(Student o1, Student o2) {
				return o1.getBirthday().compareTo(o2.getBirthday());
			}
		});
		
		for(int i=0;i<students.size();i++){
			System.out.println(DateUtil.format2(students.get(i).getBirthday()));
		}
	}
}
