package com.yu.chapter3.les2.thread.communication;

/**
 * join����Ĵ�����ǰ���У���������
 * @author xijia
 *
 */
public class Les7_join_codeafter_preExecute_trap {
	
	public static class ThreadA extends Thread {
		private ThreadB b;

		public ThreadA(ThreadB b) {
			super();
			this.b = b;
		}

		@Override
		public void run() {
			try {
				synchronized (b) {
					System.out.println("begin A ThreadName="
							+ Thread.currentThread().getName() + "  "
							+ System.currentTimeMillis());
					Thread.sleep(5000);
					System.out.println("  end A ThreadName="
							+ Thread.currentThread().getName() + "  "
							+ System.currentTimeMillis());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class ThreadB extends Thread {
		@Override
		synchronized public void run() {
			try {
				System.out.println("begin B ThreadName="
						+ Thread.currentThread().getName() + "  "
						+ System.currentTimeMillis());
				Thread.sleep(5000);
				System.out.println("  end B ThreadName="
						+ Thread.currentThread().getName() + "  "
						+ System.currentTimeMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * ���ܳ��������������:ԭ��������������ɵ�.
	 * begin A ThreadName=Thread-1  1492466963127
  end A ThreadName=Thread-1  1492466968129
                    main end 1492466968129
begin B ThreadName=Thread-0  1492466968129
  end B ThreadName=Thread-0  1492466973129
  
   begin A ThreadName=Thread-1  1492466963127
  end A ThreadName=Thread-1  1492466968129
begin B ThreadName=Thread-0  1492466968129
  end B ThreadName=Thread-0  1492466973129
   main end 1492466968129
   
      begin A ThreadName=Thread-1  1492466963127
  end A ThreadName=Thread-1  1492466968129
begin B ThreadName=Thread-0  1492466968129
   main end 1492466968129
     end B ThreadName=Thread-0  1492466973129
  
	 * 
	 * join(2000)�󲿷��������еģ�Ҳ������������ThreadB����,Ȼ����ٽ����ͷ�
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ThreadB b = new ThreadB();
			ThreadA a = new ThreadA(b);
			a.start();
			b.start();
			b.join(2000);
			System.out.println("                    main end "
					+ System.currentTimeMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �󲿷ֵ�ʱ���ӡ�������:
	 *    main end=1492467190856
begin A ThreadName=Thread-1  1492467190858
  end A ThreadName=Thread-1  1492467195858
begin B ThreadName=Thread-0  1492467195858
  end B ThreadName=Thread-0  1492467200858
  
  main end�󲿷�ʱ���ǵ�һ����ӡ�ġ����������main��join(2000)�󲿷��������еģ�Ҳ����
  ��������ThreadB����,Ȼ����ٽ����ͷ�
	 * 
	 * @param args
	 */
//	public static void main(String[] args) {
//		ThreadB b = new ThreadB();
//		ThreadA a = new ThreadA(b);
//		a.start();
//		b.start();
//		System.out.println("   main end=" + System.currentTimeMillis());
//	}
}
