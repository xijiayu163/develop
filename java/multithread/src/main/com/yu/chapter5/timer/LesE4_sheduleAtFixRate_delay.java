package com.yu.chapter5.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesE4_sheduleAtFixRate_delay {

	private static Timer timer = new Timer();
	private static int runCount = 0;

	static public class MyTask1 extends TimerTask {
		@Override
		public void run() {
			try {
				System.out.println("1 begin �����ˣ�ʱ��Ϊ��" + new Date());
				Thread.sleep(5000);
				System.out.println("1   end �����ˣ�ʱ��Ϊ��" + new Date());
				runCount++;
				if (runCount == 5) {
					timer.cancel();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * scheduleAtFixedRate ���ִ�������ʱ�䱻��ʱ����ô��һ�������ִ��ʱ������һ������"����"��ʱ��Ϊ�ο�������
	 * ��schedule����һ��
	 * 
	 * �ַ���1ʱ�䣺2014-10-12 15:33:00 ��ǰʱ�䣺2017-4-22 18:48:22
1 begin �����ˣ�ʱ��Ϊ��Sat Apr 22 18:48:22 CST 2017
1   end �����ˣ�ʱ��Ϊ��Sat Apr 22 18:48:27 CST 2017
1 begin �����ˣ�ʱ��Ϊ��Sat Apr 22 18:48:27 CST 2017
1   end �����ˣ�ʱ��Ϊ��Sat Apr 22 18:48:32 CST 2017
1 begin �����ˣ�ʱ��Ϊ��Sat Apr 22 18:48:32 CST 2017
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyTask1 task1 = new MyTask1();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString1 = "2014-10-12 15:33:00";
			Date dateRef1 = sdf1.parse(dateString1);
			System.out.println("�ַ���1ʱ�䣺" + dateRef1.toLocaleString() + " ��ǰʱ�䣺"
					+ new Date().toLocaleString());
			timer.scheduleAtFixedRate(task1, dateRef1, 2000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
