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
			System.out.println("����run��");
			while (isRunning == true) {
			}
			System.out.println("�̱߳�ֹͣ�ˣ�");
		}

	}
	
	/**
	 * �߳�һֱ�����˳�.
	 * ������RunThread�߳�ʱ������private boolean isRunning=true,�����ڹ�����ջ���̵߳�
	 * ˽�ж�ջ�С�JVM��ĳЩʱ��(������Ϊ-serverģʽʱ)Ϊ���߳����е�Ч�ʣ��߳�һֱ��˽�ж�ջ��ȡ��
	 * isRunning��ֵ��true.������thread.setRunning(false);��Ȼ��ִ�У����µ�ȴ�ǹ�����ջ�е�
	 * isRuuning����ֵFalse,����һֱ������ѭ����״̬��
	 * ���������ʵ����˽�ж�ջ�е�ֵ�͹�����ջ�е�ֵ��ͬ����ɵġ���������������Ҫʹ��volatile�ؼ��֡�����
	 * Ҫ�����þ��ǵ��̷߳���isRunning�������ʱ��ǿ���Դӹ�����ջ�н���ȡֵ.
	 * ��ӡ���:
	 * ����run��
	 �Ѿ���ֵΪfalse


	 * @param args
	 */
	public static void main(String[] args) {
		try {
			RunThread thread = new RunThread();
			thread.start();
			Thread.sleep(1000);
			thread.setRunning(false);
			System.out.println("�Ѿ���ֵΪfalse");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
