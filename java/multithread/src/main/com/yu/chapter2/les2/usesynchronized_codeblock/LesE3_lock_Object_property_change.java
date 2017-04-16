package com.yu.chapter2.les2.usesynchronized_codeblock;

public class LesE3_lock_Object_property_change {

	public static class Userinfo {
		private String username;
		private String password;

		public Userinfo() {
			super();
		}

		public Userinfo(String username, String password) {
			super();
			this.username = username;
			this.password = password;
		}

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

	}
	
	public static class Service {

		public void serviceMethodA(Userinfo userinfo) {
			synchronized (userinfo) {
				try {
					System.out.println(Thread.currentThread().getName());
					userinfo.setUsername("abcabcabc");
					Thread.sleep(3000);
					System.out.println("end! time=" + System.currentTimeMillis());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static class ThreadA extends Thread {

		private Service service;
		private Userinfo userinfo;

		public ThreadA(Service service, 
				Userinfo userinfo) {
			super();
			this.service = service;
			this.userinfo = userinfo;
		}

		@Override
		public void run() {
			service.serviceMethodA(userinfo);
		}

	}
	
	public static class ThreadB extends Thread {

		private Service service;
		private Userinfo userinfo;

		public ThreadB(Service service, 
				Userinfo userinfo) {
			super();
			this.service = service;
			this.userinfo = userinfo;
		}

		@Override
		public void run() {
			service.serviceMethodA(userinfo);
		}

	}
	
	
	/**
	 * 锁对象属性胡改变不影响锁的作用
	 * a
end! time=1492323710323
b
end! time=1492323713323
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			Service service = new Service();
			Userinfo userinfo = new Userinfo();

			ThreadA a = new ThreadA(service, userinfo);
			a.setName("a");
			a.start();
			Thread.sleep(50);
			ThreadB b = new ThreadB(service, userinfo);
			b.setName("b");
			b.start();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
