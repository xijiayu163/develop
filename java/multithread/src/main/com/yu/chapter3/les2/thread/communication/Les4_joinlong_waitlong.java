package com.yu.chapter3.les2.thread.communication;

public class Les4_joinlong_waitlong {

	public static class MyThread extends Thread {

		@Override
		public void run() {
			try {
				System.out.println("begin Timer=" + System.currentTimeMillis());
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 方法join(long)中的参数是设定等待的时间
	 * 运行结果:
	 * begin Timer=1492465426956
	 * end timer=1492465428956
	 * 
	 * 将main中的join(2000)改成sleep(2000),运行的效果还是等待2S
	 * join(long)和sleep(long)的区别来自于2个方法对同步的处理上。
	 * join(long)的功能在内部是使用wait(long)方法来实现的，所以join(long)方法具有释放锁的特点
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyThread threadTest = new MyThread();
			threadTest.start();

			 threadTest.join(2000);// 只等2秒
//			Thread.sleep(2000);

			System.out.println("  end timer=" + System.currentTimeMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
