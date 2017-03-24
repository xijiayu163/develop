package com.yu.file;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class FileUtil {
	/**
	 * 文件转换成字节流
	 *
	 * @author yuxijia
	 * @date 2017年1月3日
	 * @since 3.9.0
	 *
	 * @param filePath
	 * @return
	 */
	public static byte[] File2byte(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * 字节流保存成文件
	 *
	 * @author yuxijia
	 * @date 2017年1月3日
	 * @since 3.9.0
	 *
	 * @param buf
	 * @param filePath
	 * @param fileName
	 */
	public static void byte2File(byte[] buf, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {
				dir.mkdirs();
			}
			file = new File(filePath + File.separator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            String 如 c:/fqf
	 * @return boolean
	 */
	public static void newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新建目录，深层目录
	 * 
	 * @param folderPath
	 */
	public static void newFolderDeep(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 * 清空指定的目录并删除该目录
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean deleteDir(String dir) {
		File file = new File(dir);
		return deleteDir(file);
	}

	/**
	 * 拷贝文件
	 * fileCopy("D:\\apache-tomcat-7.0.61_83.zip","D:\\喜好配置\\apache-tomcat-7.0.61_83.zip");
	 * @param src
	 * @param des
	 */
	private static void fileCopy(String src, String des) {
		BufferedReader br = null;
		PrintStream ps = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(src)));
			ps = new PrintStream(new FileOutputStream(des));
			String s = null;
			while ((s = br.readLine()) != null) {
				ps.println(s);
				ps.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (br != null)
					br.close();
				if (ps != null)
					ps.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 文件夹拷贝(文件内含有文件和文件夹) 
	 * @param src
	 * @param des
	 */
	private static void dirCopy(String src, String des) {  
        File file1=new File(src);  
        File[] fs=file1.listFiles();  
        File file2=new File(des);  
        if(!file2.exists()){  
            file2.mkdirs();  
        }  
        for (File f : fs) {  
            if(f.isFile()){  
                fileCopy(f.getPath(),des+"\\"+f.getName()); //调用文件拷贝的方法  
            }else if(f.isDirectory()){  
            	dirCopy(f.getPath(),des+"\\"+f.getName());  
            }  
        }  
    }  

	public static void main(String[] args) {
		dirCopy("D:\\喜好配置","D:\\xxx\\喜好配置");
//		fileCopy("D:\\apache-tomcat-7.0.61_83.zip","D:\\喜好配置\\apache-tomcat-7.0.61_83.zip");
	}
}
