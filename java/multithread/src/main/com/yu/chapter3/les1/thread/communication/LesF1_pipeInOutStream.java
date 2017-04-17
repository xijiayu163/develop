package com.yu.chapter3.les1.thread.communication;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 通过管道进行线程间通信，字节流
 * @author xijia
 *
 */
public class LesF1_pipeInOutStream {

	public static class WriteData {

		public void writeMethod(PipedOutputStream out) {
			try {
				System.out.println("write :");
				for (int i = 0; i < 300; i++) {
					String outData = "" + (i + 1);
					out.write(outData.getBytes());
					System.out.print(outData);
				}
				System.out.println();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class ReadData {

		public void readMethod(PipedInputStream input) {
			try {
				System.out.println("read  :");
				byte[] byteArray = new byte[20];
				//无数据写入时会被阻塞
				int readLength = input.read(byteArray);
				while (readLength != -1) {
					String newData = new String(byteArray, 0, readLength);
					System.out.print(newData);
					readLength = input.read(byteArray);
				}
				System.out.println();
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class ThreadRead extends Thread {

		private ReadData read;
		private PipedInputStream input;

		public ThreadRead(ReadData read, PipedInputStream input) {
			super();
			this.read = read;
			this.input = input;
		}

		@Override
		public void run() {
			read.readMethod(input);
		}
	}
	
	public static class ThreadWrite extends Thread {

		private WriteData write;
		private PipedOutputStream out;

		public ThreadWrite(WriteData write, PipedOutputStream out) {
			super();
			this.write = write;
			this.out = out;
		}

		@Override
		public void run() {
			write.writeMethod(out);
		}

	}
	
	/**
	 * 两个管道流成功进行了数据的传输
	 * 但在此实验中，首先是读取new ThreadRead(inputStream)启动，由于当时没有数据被写入
	 * 所以线程阻塞在int length=in.read(byteArray)代码中，直到有数据写入，才继续向下运行
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			WriteData writeData = new WriteData();
			ReadData readData = new ReadData();

			PipedInputStream inputStream = new PipedInputStream();
			PipedOutputStream outputStream = new PipedOutputStream();

			// inputStream.connect(outputStream);
			outputStream.connect(inputStream);

			ThreadRead threadRead = new ThreadRead(readData, inputStream);
			threadRead.start();

			Thread.sleep(2000);

			ThreadWrite threadWrite = new ThreadWrite(writeData, outputStream);
			threadWrite.start();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
