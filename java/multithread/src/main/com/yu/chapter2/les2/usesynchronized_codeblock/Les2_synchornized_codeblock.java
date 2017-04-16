package com.yu.chapter2.les2.usesynchronized_codeblock;

public class Les2_synchornized_codeblock {

	public static class ObjectService {

		public void serviceMethod() {
			try {
				//持有的是当前对象的锁(即对象锁)
				synchronized (this) {
					System.out.println("begin time=" + System.currentTimeMillis());
					Thread.sleep(2000);
					System.out.println("end    end=" + System.currentTimeMillis());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class ThreadA extends Thread {

		private ObjectService service;

		public ThreadA(ObjectService service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			super.run();
			service.serviceMethod();
		}

	}
	
	public static class ThreadB extends Thread {
		private ObjectService service;

		public ThreadB(ObjectService service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			super.run();
			service.serviceMethod();
		}
	}
	
	/**
	 * begin time=1492311471181
end    end=1492311473182
begin time=1492311473182
end    end=1492311475183
	 * @param args
	 */
	public static void main(String[] args) {
		ObjectService service = new ObjectService();

		ThreadA a = new ThreadA(service);
		a.setName("a");
		a.start();

		ThreadB b = new ThreadB(service);
		b.setName("b");
		b.start();
	}

}
