package com.yu.chapter3.les1.thread.communication;

public class LesG_intersect_backup {

	public static class DBTools {

		volatile private boolean prevIsA = false;

		synchronized public void backupA() {
			try {
				while (prevIsA == true) {
					wait();
				}
				for (int i = 0; i < 2; i++) {
					System.out.println("★★★★★");
				}
				prevIsA = true;
				notifyAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		synchronized public void backupB() {
			try {
				while (prevIsA == false) {
					wait();
				}
				for (int i = 0; i < 2; i++) {
					System.out.println("☆☆☆☆☆");
				}
				prevIsA = false;
				notifyAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class BackupA extends Thread {
		private DBTools dbtools;
		public BackupA(DBTools dbtools) {
			super();
			this.dbtools = dbtools;
		}

		@Override
		public void run() {
			dbtools.backupA();
		}
	}

	public static class BackupB extends Thread {
		private DBTools dbtools;
		public BackupB(DBTools dbtools) {
			super();
			this.dbtools = dbtools;
		}

		@Override
		public void run() {
			dbtools.backupB();
		}
	}
	
	/**
	 * 
	 * 20个线程，10个线程备份A，10个线程备份B，交替执行
	 * 
	 * ☆☆☆☆☆
☆☆☆☆☆
★★★★★
★★★★★
☆☆☆☆☆
☆☆☆☆☆
★★★★★
★★★★★
☆☆☆☆☆
☆☆☆☆☆
	 * @param args
	 */
	public static void main(String[] args) {
		DBTools dbtools = new DBTools();
		for (int i = 0; i < 20; i++) {
			BackupB output = new BackupB(dbtools);
			output.start();
			BackupA input = new BackupA(dbtools);
			input.start();
		}
	}

}
