package com.yu.zip;

import java.io.File;

import org.apache.tools.ant.Project;    
import org.apache.tools.ant.taskdefs.Zip;    
import org.apache.tools.ant.types.FileSet;  

/**
 * 利用ant压缩
 * pom配置
 * <dependency>
			<groupId>ant</groupId>
			<artifactId>ant</artifactId>
			<version>1.6.5</version>
			<scope>provided</scope>
		</dependency>
 * @author yuxijia
 *
 */
public class ZipCompressorByAnt {    
    
    private File zipFile;    
    
    /** 
     * 压缩文件构造函数 
     * @param pathName 最终压缩生成的压缩文件：目录+压缩文件名.zip 
     */  
    public ZipCompressorByAnt(String finalFile) {    
        zipFile = new File(finalFile);    
    }    
        
    /** 
     * 执行压缩操作 
     * @param srcPathName 需要被压缩的文件/文件夹 
     */  
    public void compressExe(String srcPathName) {    
        File srcdir = new File(srcPathName);    
        if (!srcdir.exists()){  
            throw new RuntimeException(srcPathName + "不存在！");    
        }   
            
        Project prj = new Project();    
        Zip zip = new Zip();    
        zip.setProject(prj);    
        zip.setDestFile(zipFile);    
        FileSet fileSet = new FileSet();    
        fileSet.setProject(prj);    
        fileSet.setDir(srcdir);    
        //fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");    
        //fileSet.setExcludes(...); //排除哪些文件或文件夹    
        zip.addFileset(fileSet);    
        zip.execute();    
    }    
    
    public static void main(String[] args) {  
        ZipCompressorByAnt zca = new ZipCompressorByAnt("E:\\orders.zip");  
        zca.compressExe("E:\\orderhtml");  
    }  
}   