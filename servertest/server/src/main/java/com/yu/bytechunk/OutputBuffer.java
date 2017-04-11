package com.yu.bytechunk;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class OutputBuffer implements ByteChunk.ByteOutputChannel {
	private ByteChunk fileBuffer;
	FileOutputStream fileOutputStream;

	public OutputBuffer() {
		fileBuffer = new ByteChunk();
		fileBuffer.setByteOutputChannel(this);
		fileBuffer.allocate(3, 7);
		try {
			fileOutputStream = new FileOutputStream("d:\\hello.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void realWriteBytes(byte cbuf[], int off, int len) throws IOException {
		fileOutputStream.write(cbuf, off, len);
	}

	public void flush() throws IOException {
		fileBuffer.flushBuffer();
	}

	public int dowrite(byte[] bytes) throws IOException {
		for (int i = 0; i < bytes.length; i++)
			fileBuffer.append(bytes[i]);
		return bytes.length;
	}

	/**
	 * 输出测试类OutputBuffer，此类使用字节块提供的缓冲机制对d盘的hello.txt文件进行写入操作，为更好说明缓冲区工作原理，
	 * 把字节块的缓冲区初始大小设为3最大为7，我们要把八个字节码写到hello.txt文件，主要看加粗的三行代码，
	 * 执行dowrite方法时因为长度为8，已经超过了缓冲区最大值，所以进行了一次真实写入操作，接着让程序睡眠十秒，
	 * 期间你打开hello.txt文件只能看到7个字节数组，它们为1到7（使用十六进制打开）。
	 * 十秒过后，由于执行了flush刷新操作才把剩下的一个字节写入文件。
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		OutputBuffer outputBuffer = new OutputBuffer();
		byte[] bytes = { 1, 2, 3, 4, 5, 6, 7, 8 };
		try {
			outputBuffer.dowrite(bytes);
			Thread.currentThread().sleep(10 * 1000);
			outputBuffer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
