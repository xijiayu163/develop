package com.yu.chapter5.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesC3_Timer_cancel_attention {
	static int i = 0;

	static public class MyTask extends TimerTask {
		@Override
		public void run() {
			System.out.println("����ִ����" + i);
		}
	}

	/**
	 * Timer���е�cancel()������ʱ��û��������queue��������timerTask���е������������
	 * 
	 * 394
����ִ����429
����ִ����634
����ִ����717
����ִ����725
����ִ����734
����ִ����738
...
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		while (true) {
			try {
				i++;
				Timer timer = new Timer();
				MyTask task = new MyTask();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String dateString = "2014-10-12 09:08:00";
				
				Date dateRef = sdf.parse(dateString);
				timer.schedule(task, dateRef);
				timer.cancel();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
}
