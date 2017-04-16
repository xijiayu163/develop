package com.yu.chapter1.les7.stopthread;

public class LesA1_thread_stop_release_lock_notrecommend extends Thread {

	private SynchronizedObject object;

	public LesA1_thread_stop_release_lock_notrecommend(SynchronizedObject object) {
		super();
		this.object = object;
	}

	@Override
	public void run() {
		object.printString("b", "bb");
	}
	
	public static class SynchronizedObject {

		private String username = "a";
		private String password = "aa";

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		/**
		 * username����ֵΪb֮��ǿ����ֹ,���� password��ֵ���ǳ�ʼֵaa,������������
		 * @param username
		 * @param password
		 */
		synchronized public void printString(String username, String password) {
			try {
				this.username = username;
				Thread.sleep(100000);
				this.password = password;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * ��ӡ��� b aa ��������b bb ��һ��
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SynchronizedObject object = new SynchronizedObject();
			LesA1_thread_stop_release_lock_notrecommend thread = new LesA1_thread_stop_release_lock_notrecommend(object);
			thread.start();
			Thread.sleep(500);
			thread.stop();
			System.out.println(object.getUsername() + " "
					+ object.getPassword());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
