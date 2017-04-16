package com.yu.chapter2.les1.usesynchronized_method;

public class Les9_synchornized_notextend_feature {
	public static class Main {

		synchronized public void serviceMethod() {
			try {
				System.out.println("int main 下一步sleep begin threadName="
						+ Thread.currentThread().getName() + " time="
						+ System.currentTimeMillis());
				Thread.sleep(5000);
				System.out.println("int main 下一步sleep   end threadName="
						+ Thread.currentThread().getName() + " time="
						+ System.currentTimeMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static class Sub extends Main {

//		/**
//		 * 子类的不具有同步性,super调用父类的方法依然具有同步性
//		 * int sub 下一步sleep begin threadName=A time=1492285498123
//int sub 下一步sleep begin threadName=B time=1492285498123
//int sub 下一步sleep   end threadName=B time=1492285503123
//int sub 下一步sleep   end threadName=A time=1492285503123
//int main 下一步sleep begin threadName=B time=1492285503123
//int main 下一步sleep   end threadName=B time=1492285508124
//int main 下一步sleep begin threadName=A time=1492285508124
//int main 下一步sleep   end threadName=A time=1492285513124
//		 */
//		@Override
//		public void serviceMethod() {
//			try {
//				System.out.println("int sub 下一步sleep begin threadName="
//						+ Thread.currentThread().getName() + " time="
//						+ System.currentTimeMillis());
//				Thread.sleep(5000);
//				System.out.println("int sub 下一步sleep   end threadName="
//						+ Thread.currentThread().getName() + " time="
//						+ System.currentTimeMillis());
//				super.serviceMethod();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		/**
		 * 父子都具有同步性
		 * int sub 下一步sleep begin threadName=A time=1492285647616
int sub 下一步sleep   end threadName=A time=1492285652617
int main 下一步sleep begin threadName=A time=1492285652617
int main 下一步sleep   end threadName=A time=1492285657617
int sub 下一步sleep begin threadName=B time=1492285657617
int sub 下一步sleep   end threadName=B time=1492285662617
int main 下一步sleep begin threadName=B time=1492285662617
int main 下一步sleep   end threadName=B time=1492285667618
		 */
		@Override
		synchronized public void serviceMethod() {
			try {
				System.out.println("int sub 下一步sleep begin threadName="
						+ Thread.currentThread().getName() + " time="
						+ System.currentTimeMillis());
				Thread.sleep(5000);
				System.out.println("int sub 下一步sleep   end threadName="
						+ Thread.currentThread().getName() + " time="
						+ System.currentTimeMillis());
				super.serviceMethod();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public static class MyThreadA extends Thread {

		private Sub sub;

		public MyThreadA(Sub sub) {
			super();
			this.sub = sub;
		}

		@Override
		public void run() {
			sub.serviceMethod();
		}

	}
	
	public static class MyThreadB extends Thread {

		private Sub sub;

		public MyThreadB(Sub sub) {
			super();
			this.sub = sub;
		}

		@Override
		public void run() {
			sub.serviceMethod();
		}
	}
	
	/**
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Sub subRef = new Sub();

		MyThreadA a = new MyThreadA(subRef);
		a.setName("A");
		a.start();

		MyThreadB b = new MyThreadB(subRef);
		b.setName("B");
		b.start();
	}

}
