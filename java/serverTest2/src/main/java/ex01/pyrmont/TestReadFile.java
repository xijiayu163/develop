package ex01.pyrmont;

import java.io.File;
import java.io.FileInputStream;

public class TestReadFile {
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		 byte[] bytes = new byte[BUFFER_SIZE];
		    FileInputStream fis = null;
		    try {
		      File file = new File("F:\\github\\develop\\java\\serverStudy\\webroot\\index.html");
		      if (file.exists()) {
		        fis = new FileInputStream(file);
		        int ch = fis.read(bytes, 0, BUFFER_SIZE);
		        System.out.print(new String(bytes, 0, ch));
		        while (ch!=-1) {
		          ch = fis.read(bytes, 0, BUFFER_SIZE);
		        }
		      }
		    }catch(Exception ex){
		    	System.out.print(ex.getMessage());
		    }
	}
	
//	public static void main(String [] args) {  
//        try {  
//            //FileOutputStream out = new FileOutputStream("hello.txt");  
//              
//            //out.write("love_snooker".getBytes());  
//              
//            //out.close();  
//              
//            File file = new File("F:\\github\\develop\\java\\serverStudy\\webroot\\index.html");  
//            FileInputStream in = new FileInputStream(file);  
//              
//            byte[] buf = new byte[1024];  
//            int len = in.read(buf);  
//              
//            System.out.println(buf);  
//            System.out.println(new String(buf, 0, len));  
//              
//            in.close();  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
//    }  
}
