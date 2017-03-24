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
 * ���Ϲ�����
 * @author yuxijia
 *
 */
public class CollectionUtil {

	/**
	 * ��ȡList���ϵĵ�һ��Ԫ��
	 * 
	 * @param list
	 * @return �������Ϊnull���򷵻�null�����򷵻�ָ���ķ��Ͷ���
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
	 * �Ӽ����в��ҵ�һ��ָ������Ϊָ��ֵ��Ԫ��
	 *
	 * @author yuxijia
	 * @date 2017��1��10�� 
	 * @since 3.9.0
	 *
	 * @param list ����
	 * @param propertyName ������
	 * @param value ��ƥ�������ֵ
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
	 * �Ӽ����в�������ָ������Ϊָ��ֵ���Ӽ�
	 *
	 * @author yuxijia
	 * @date 2017��1��10�� 
	 * @since 3.9.0
	 *
	 * @param list ����
	 * @param propertyName ������
	 * @param value ��ƥ�������ֵ
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
	 * �Ӽ����в���ָ������ֵ��ɵļ���
	 *
	 * @author yuxijia
	 * @date 2017��1��10�� 
	 * @since 3.9.0
	 *
	 * @param list	����
	 * @param propertyName ������
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
