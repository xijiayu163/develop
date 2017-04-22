package com.yu.chapter6.singleton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;


public class Les3_2_lazy_staticInnerClass_error {

	public static class MyObject implements Serializable {

		private static final long serialVersionUID = 888L;

		// 内部类方式
		private static class MyObjectHandler {
			private static final MyObject myObject = new MyObject();
		}

		private MyObject() {
		}

		public static MyObject getInstance() {
			return MyObjectHandler.myObject;
		}
	}
	
	/**
	 * 
	 * 静态内部类方式实现多线程单例模式
	 * 缺点:如果遇到序列化对象时，使用默认的方式运行得到的结果还是多例的
	 * 解决办法椒使用readResolve
	 * 
	 * 865113938
245257410

	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyObject myObject = MyObject.getInstance();
			FileOutputStream fosRef = new FileOutputStream(new File(
					"myObjectFile.txt"));
			ObjectOutputStream oosRef = new ObjectOutputStream(fosRef);
			oosRef.writeObject(myObject);
			oosRef.close();
			fosRef.close();
			System.out.println(myObject.hashCode());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			FileInputStream fisRef = new FileInputStream(new File(
					"myObjectFile.txt"));
			ObjectInputStream iosRef = new ObjectInputStream(fisRef);
			MyObject myObject = (MyObject) iosRef.readObject();
			iosRef.close();
			fisRef.close();
			System.out.println(myObject.hashCode());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
