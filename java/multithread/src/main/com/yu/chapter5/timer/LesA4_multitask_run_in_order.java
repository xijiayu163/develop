package com.yu.chapter5.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesA4_multitask_run_in_order {

	private static Timer timer = new Timer();

	static public class MyTask1 extends TimerTask {
		@Override
		public void run() {
			try {
				System.out.println("1 begin �����ˣ�ʱ��Ϊ��" + new Date());
				Thread.sleep(20000);
				System.out.println("1   end �����ˣ�ʱ��Ϊ��" + new Date());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static public class MyTask2 extends TimerTask {
		@Override
		public void run() {
			System.out.println("2 begin �����ˣ�ʱ��Ϊ��" + new Date());
			System.out.println("�����ˣ�ʱ��Ϊ��" + new Date());
			System.out.println("2   end �����ˣ�ʱ��Ϊ��" + new Date());
		}
	}

	/**
	 * TimerTask���Զ��еķ�ʽһ��һ����˳��ִ�У�����ִ�е�ʱ���п��ܺ�Ԥ�ڵ�ʱ�䲻һ�£�
	 * ��Ϊǰ��������п������ĵ�ʱ��ϳ����������������е�ʱ��Ҳ�ᱻ�ӳ١�
	 * 
	 * ���task��˳��ִ��
	 * ����task1��Ҫ��ʱ20��ִ��������task1��ʼ��ʱ����17:55:12,��ô��ҪӰ��task2�ļƻ�
	 * ִ�е�ʱ�䣬task2�Դ�ʱ��Ϊ��׼������ӳ�20�룬task2��17:55:32ִ������ ��
	 * 
	 * �ַ���1ʱ�䣺2014-10-12 11:33:00 ��ǰʱ�䣺2017-4-22 17:55:12
�ַ���2ʱ�䣺2014-10-12 11:33:10 ��ǰʱ�䣺2017-4-22 17:55:12
1 begin �����ˣ�ʱ��Ϊ��Sat Apr 22 17:55:12 CST 2017
1   end �����ˣ�ʱ��Ϊ��Sat Apr 22 17:55:32 CST 2017
2 begin �����ˣ�ʱ��Ϊ��Sat Apr 22 17:55:32 CST 2017
�����ˣ�ʱ��Ϊ��Sat Apr 22 17:55:32 CST 2017
2   end �����ˣ�ʱ��Ϊ��Sat Apr 22 17:55:32 CST 2017
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyTask1 task1 = new MyTask1();
			MyTask2 task2 = new MyTask2();

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String dateString1 = "2014-10-12 11:33:00";
			String dateString2 = "2014-10-12 11:33:10";

			Date dateRef1 = sdf1.parse(dateString1);
			Date dateRef2 = sdf2.parse(dateString2);

			System.out.println("�ַ���1ʱ�䣺" + dateRef1.toLocaleString() + " ��ǰʱ�䣺"
					+ new Date().toLocaleString());
			System.out.println("�ַ���2ʱ�䣺" + dateRef2.toLocaleString() + " ��ǰʱ�䣺"
					+ new Date().toLocaleString());

			timer.schedule(task1, dateRef1);
			timer.schedule(task2, dateRef2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
