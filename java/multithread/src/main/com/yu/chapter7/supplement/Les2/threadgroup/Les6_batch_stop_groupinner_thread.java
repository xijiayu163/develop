package com.yu.chapter7.supplement.Les2.threadgroup;

public class Les6_batch_stop_groupinner_thread {

	public static class MyThread extends Thread {

		public MyThread(ThreadGroup group, String name) {
			super(group, name);
		}

		@Override
		public void run() {
			System.out.println("ThreadName=" + Thread.currentThread().getName()
					+ "准备开始死循环了：)");
			while (!this.isInterrupted()) {
			}
			System.out.println("ThreadName=" + Thread.currentThread().getName()
					+ "结束了：)");
		}

	}
	
	/**
	 * 批量停止组内线程
	 * 
	 * ThreadName=线程1准备开始死循环了：)
ThreadName=线程2准备开始死循环了：)
ThreadName=线程3准备开始死循环了：)
ThreadName=线程4准备开始死循环了：)
ThreadName=线程5准备开始死循环了：)
调用了interrupt()方法
ThreadName=线程4结束了：)
ThreadName=线程3结束了：)
ThreadName=线程1结束了：)
ThreadName=线程5结束了：)
ThreadName=线程2结束了：)

	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ThreadGroup group = new ThreadGroup("我的线程组");

			for (int i = 0; i < 5; i++) {
				MyThread thread = new MyThread(group, "线程" + (i + 1));
				thread.start();
			}
			Thread.sleep(5000);
			group.interrupt();
			System.out.println("调用了interrupt()方法");
		} catch (InterruptedException e) {
			System.out.println("停了停了！");
			e.printStackTrace();
		}

	}

}
