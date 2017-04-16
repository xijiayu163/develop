package com.yu.chapter2.les3.usevolatile_visibility;

public class Les1_sync_dead_loop {

	public static class PrintString {

		private boolean isContinuePrint = true;

		public boolean isContinuePrint() {
			return isContinuePrint;
		}

		public void setContinuePrint(boolean isContinuePrint) {
			this.isContinuePrint = isContinuePrint;
		}

		public void printStringMethod() {
			try {
				while (isContinuePrint == true) {
					System.out.println("run printStringMethod threadName="
							+ Thread.currentThread().getName());
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 程序开始运行后，根本停不下来
	 * @param args
	 */
	public static void main(String[] args) {
		PrintString printStringService = new PrintString();
		printStringService.printStringMethod();
		System.out.println("我要停止它！stopThread="
				+ Thread.currentThread().getName());
		printStringService.setContinuePrint(false);
	}

}
