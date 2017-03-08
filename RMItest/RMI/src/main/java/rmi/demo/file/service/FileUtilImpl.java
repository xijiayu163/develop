package rmi.demo.file.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi.demo.file.facade.IFileUtil;

public class FileUtilImpl extends UnicastRemoteObject implements IFileUtil {
	protected FileUtilImpl() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 7594622080290821912L;

	public byte[] downloadFile(String fileName) throws RemoteException {
		File file = new File(fileName);
		byte buffer[] = new byte[(int) file.length()];
		int size = buffer.length;
		System.out.println("download file size = " + size + "b");
		if (size > 1024 * 1024 * 10) {// 限制文件大小不能超过10M，文件太大可能导制内存溢出！
			throw new RemoteException("Error:<The File is too big!>");
		}
		try {
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName));
			input.read(buffer, 0, buffer.length);
			input.close();
			System.out.println("Info:<downloadFile() hed execute successful!>");
			return buffer;

		} catch (Exception e) {
			System.out.println("FileUtilImpl: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String argv[]) {
		try {
			IFileUtil file = new FileUtilImpl();
			// LocateRegistry.createRegistry(1099);
			// //加上此程序，就可以不要在控制台上开启RMI的注册程序，1099是RMI服务监视的默认端口
			Naming.rebind("rmi://127.0.0.1:1011/FileUtilServer", file);
			System.out.print("Ready");
		} catch (Exception e) {
			System.out.println("FileUtilServer: " + e.getMessage());
			e.printStackTrace();
		}
	}
}