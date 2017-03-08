package rmi.demo.file.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.rmi.Naming;
import rmi.demo.file.facade.IFileUtil;

public class FileUtilClient {
	
	//运行流程
	//1 >rmic rmi.demo.file.service.FileUtilImpl
	//2 >rmiregistry 1011
	//3 >java rmi.demo.file.service.FileUtilImpl
	//4 >D:\RMITest-0.0.1-SNAPSHOT>java rmi.demo.file.client.FileUtilClient 127.0.0.1 D:\\DownloadTest.jpg E:\\test.jpg
	//file.getAbsolutePath() = E:\test.jpg
	
	public static void main(String args[]) {
		if (args.length != 3) {
			System.out.println("第一个参数：RMI服务的IP地址");
			System.out.println("第二个参数：要下载的文件名");
			System.out.println("第三个参数：要文件保存位置");
			System.exit(0);
		}

		try {
			String name = "rmi://127.0.0.1:1011/FileUtilServer";
			IFileUtil fileUtil = (IFileUtil) Naming.lookup(name);
			byte[] filedata = fileUtil.downloadFile(args[1]);
			if (filedata == null) {
				System.out.println("Error：<filedata is null!>");
				System.exit(0);
			}

			File file = new File(args[2]);
			System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file.getAbsolutePath()));
			output.write(filedata, 0, filedata.length);
			output.flush();
			output.close();
			System.out.println("~~~~~End~~~~~");
		} catch (Exception e) {
			System.err.println("FileUtilServer exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
