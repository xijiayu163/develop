package com.yu.chapter7.supplement.Les4.simpleDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Les1_simpleDateFormat_error {

	public static class MyThread extends Thread {

		private SimpleDateFormat sdf;
		private String dateString;

		public MyThread(SimpleDateFormat sdf, String dateString) {
			super();
			this.sdf = sdf;
			this.dateString = dateString;
		}

		@Override
		public void run() {
			try {
				Date dateRef = sdf.parse(dateString);
				String newDateString = sdf.format(dateRef).toString();
				if (!newDateString.equals(dateString)) {
					System.out.println("ThreadName=" + this.getName()
							+ "报错了 日期字符串：" + dateString + " 转换成的日期为："
							+ newDateString);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

	}
	
	/**
	 * 
	 * hreadName=Thread-0报错了 日期字符串：2000-01-01 转换成的日期为：2000-01-02
Exception in thread "Thread-6" Exception in thread "Thread-9" java.lang.NumberFormatException: For input string: ""
	at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
	at java.lang.Long.parseLong(Long.java:601)
	at java.lang.Long.parseLong(Long.java:631)
ThreadName=Thread-7报错了 日期字符串：2000-01-08 转换成的日期为：2021-11-25
ThreadName=Thread-8报错了 日期字符串：2000-01-09 转换成的日期为：8000-11-25
ThreadName=Thread-4报错了 日期字符串：2000-01-05 转换成的日期为：2000-01-04
	 * @param args
	 */
	public static void main(String[] args) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String[] dateStringArray = new String[] { "2000-01-01", "2000-01-02",
				"2000-01-03", "2000-01-04", "2000-01-05", "2000-01-06",
				"2000-01-07", "2000-01-08", "2000-01-09", "2000-01-10" };

		MyThread[] threadArray = new MyThread[10];
		for (int i = 0; i < 10; i++) {
			threadArray[i] = new MyThread(sdf, dateStringArray[i]);
		}
		for (int i = 0; i < 10; i++) {
			threadArray[i].start();
		}

	}
}
