package com.yu.chapter1.les1.extendthread;

/**
 *线程启动顺序测试，执行start()的顺序不代表线程启动的顺序
 * @author xijia
 *
 */
public class Les4_StartSequenceRandomTest extends Thread {

	private int i;

	public Les4_StartSequenceRandomTest(int i) {
		super();
		this.i = i;
	}

	@Override
	public void run() {
		System.out.println(i);
	}
	
	public static void main(String[] args) {
		Les4_StartSequenceRandomTest t11 = new Les4_StartSequenceRandomTest(1);
		Les4_StartSequenceRandomTest t12 = new Les4_StartSequenceRandomTest(2);
		Les4_StartSequenceRandomTest t13 = new Les4_StartSequenceRandomTest(3);
		Les4_StartSequenceRandomTest t14 = new Les4_StartSequenceRandomTest(4);
		Les4_StartSequenceRandomTest t15 = new Les4_StartSequenceRandomTest(5);
		Les4_StartSequenceRandomTest t16 = new Les4_StartSequenceRandomTest(6);
		Les4_StartSequenceRandomTest t17 = new Les4_StartSequenceRandomTest(7);
		Les4_StartSequenceRandomTest t18 = new Les4_StartSequenceRandomTest(8);
		Les4_StartSequenceRandomTest t19 = new Les4_StartSequenceRandomTest(9);
		Les4_StartSequenceRandomTest t110 = new Les4_StartSequenceRandomTest(10);
		Les4_StartSequenceRandomTest t111 = new Les4_StartSequenceRandomTest(11);
		Les4_StartSequenceRandomTest t112 = new Les4_StartSequenceRandomTest(12);
		Les4_StartSequenceRandomTest t113 = new Les4_StartSequenceRandomTest(13);

		t11.start();
		t12.start();
		t13.start();
		t14.start();
		t15.start();
		t16.start();
		t17.start();
		t18.start();
		t19.start();
		t110.start();
		t111.start();
		t112.start();
		t113.start();

	}
}
