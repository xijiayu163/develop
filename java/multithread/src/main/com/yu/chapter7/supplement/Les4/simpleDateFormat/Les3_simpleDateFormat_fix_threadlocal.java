package com.yu.chapter7.supplement.Les4.simpleDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Les3_simpleDateFormat_fix_threadlocal {

	public static class DateTools {

		private static ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>();

		public static SimpleDateFormat getSimpleDateFormat(String datePattern) {
			SimpleDateFormat sdf = null;
			sdf = tl.get();
			if (sdf == null) {
				sdf = new SimpleDateFormat(datePattern);
				tl.set(sdf);
			}
			return sdf;
		}

	}
	
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
				Date dateRef = DateTools.getSimpleDateFormat("yyyy-MM-dd").parse(
						dateString);
				String newDateString = DateTools.getSimpleDateFormat("yyyy-MM-dd")
						.format(dateRef).toString();
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
