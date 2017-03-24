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
	 * �ļ�ת�����ֽ���
	 *
	 * @author yuxijia
	 * @date 2017��1��3��
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
	 * �ֽ���������ļ�
	 *
	 * @author yuxijia
	 * @date 2017��1��3��
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
	 * �½�Ŀ¼
	 * 
	 * @param folderPath
	 *            String �� c:/fqf
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
	 * �½�Ŀ¼�����Ŀ¼
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
	 * �ݹ�ɾ��Ŀ¼�µ������ļ�����Ŀ¼�������ļ�
	 * 
	 * @param dir
	 *            ��Ҫɾ�����ļ�Ŀ¼
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// �ݹ�ɾ��Ŀ¼�е���Ŀ¼��
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// Ŀ¼��ʱΪ�գ�����ɾ��
		return dir.delete();
	}

	/**
	 * ���ָ����Ŀ¼��ɾ����Ŀ¼
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean deleteDir(String dir) {
		File file = new File(dir);
		return deleteDir(file);
	}

	/**
	 * �����ļ�
	 * fileCopy("D:\\apache-tomcat-7.0.61_83.zip","D:\\ϲ������\\apache-tomcat-7.0.61_83.zip");
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
	 * �ļ��п���(�ļ��ں����ļ����ļ���) 
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
                fileCopy(f.getPath(),des+"\\"+f.getName()); //�����ļ������ķ���  
            }else if(f.isDirectory()){  
            	dirCopy(f.getPath(),des+"\\"+f.getName());  
            }  
        }  
    }  

	public static void main(String[] args) {
		dirCopy("D:\\ϲ������","D:\\xxx\\ϲ������");
//		fileCopy("D:\\apache-tomcat-7.0.61_83.zip","D:\\ϲ������\\apache-tomcat-7.0.61_83.zip");
	}
}
