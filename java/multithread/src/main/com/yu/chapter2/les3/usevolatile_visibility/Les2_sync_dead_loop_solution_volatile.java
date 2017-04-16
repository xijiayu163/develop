package com.yu.chapter2.les3.usevolatile_visibility;

public class Les2_sync_dead_loop_solution_volatile {
	
	public static class RunThread extends Thread {

//		private boolean isRunning = true;
		
		volatile private boolean isRunning = true;

		public boolean isRunning() {
			return isRunning;
		}

		public void setRunning(boolean isRunning) {
			this.isRunning = isRunning;
		}

		@Override
		public void run() {
			System.out.println("进入run了");
			while (isRunning == true) {
			}
			System.out.println("线程被停止了！");
		}

	}
	
	/**
	 * 线程一直不会退出.
	 * 在启动RunThread线程时，变量private boolean isRunning=true,存在于公共堆栈及线程的
	 * 私有堆栈中。JVM在某些时候(被设置为-server模式时)为了线程运行的效率，线程一直在私有堆栈中取得
	 * isRunning的值是true.而代码thread.setRunning(false);虽然被执行，更新的却是公共堆栈中的
	 * isRuuning变量值False,所以一直就是死循环的状态。
	 * 这个问题其实就是私有堆栈中的值和公共堆栈中的值不同步造成的。解决这样的问题就要使用volatile关键字。它主
	 * 要的作用就是当线程访问isRunning这个变量时，强制性从公共堆栈中进行取值.
	 * 打印结果:
	 * 进入run了
	 已经赋值为false


	 * @param args
	 */
	public static void main(String[] args) {
		try {
			RunThread thread = new RunThread();
			thread.start();
			Thread.sleep(1000);
			thread.setRunning(false);
			System.out.println("已经赋值为false");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
