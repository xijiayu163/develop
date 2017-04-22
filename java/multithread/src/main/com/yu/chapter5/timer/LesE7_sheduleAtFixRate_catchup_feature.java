package com.yu.chapter5.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesE7_sheduleAtFixRate_catchup_feature {
	private static Timer timer = new Timer();

	static public class MyTask1 extends TimerTask {
		@Override
		public void run() {
				System.out.println("1 begin �����ˣ�ʱ��Ϊ��" + new Date());
				System.out.println("1   end �����ˣ�ʱ��Ϊ��" + new Date());
		}
	}

	
	/**
	 * scheduleAtFixedRate ����׷��ִ����
	 * 
	 * 1 begin �����ˣ�ʱ��Ϊ��Sat Apr 22 21:23:42 CST 2017
1   end �����ˣ�ʱ��Ϊ��Sat Apr 22 21:23:42 CST 2017
1 begin �����ˣ�ʱ��Ϊ��Sat Apr 22 21:23:42 CST 2017
1   end �����ˣ�ʱ��Ϊ��Sat Apr 22 21:23:42 CST 2017
1 begin �����ˣ�ʱ��Ϊ��Sat Apr 22 21:23:42 CST 2017
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyTask1 task1 = new MyTask1();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString1 = "2014-10-12 15:45:50";
			Date dateRef1 = sdf1.parse(dateString1);
			System.out.println("�ַ���1ʱ�䣺" + dateRef1.toLocaleString() + " ��ǰʱ�䣺"
					+ new Date().toLocaleString());
			timer.scheduleAtFixedRate(task1, dateRef1, 5000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
