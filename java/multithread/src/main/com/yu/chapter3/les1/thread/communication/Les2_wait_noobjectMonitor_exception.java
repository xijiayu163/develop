package com.yu.chapter3.les1.thread.communication;

public class Les2_wait_noobjectMonitor_exception {
	
	/**
	 * ���г��� java.lang.IllegalMonitorStateException�쳣
	 * ԭ����û�С��������������Ҳ����û��ͬ������
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String newString = new String("");
			newString.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}