/**
 * 
 */
package com.imti.framework.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 文件工具
 *	
 * @author 新增日期009-8-31上午09:52:07
 * @version ftpmanage 1.0
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
	
	/**
	 * 从文件路径获取文件的扩展
	 * 
	 * @param path
	 * @return {@link String} 文件后缀
	 */
	public static String getFileSuffix(String path) {
		int indexOf = path.lastIndexOf(".");
		return path.substring(indexOf+1);
	}
	
	/**
	 * 从文件路径中获取的目录路
	 * 
	 * @param path
	 * @return {@link String}
	 */
	public static String getDirectoryPath(String filePath) {
		int pos1 = filePath.lastIndexOf("/");
		int pos2 = filePath.lastIndexOf("\\");
		int pos = pos1 > pos2 ? pos1 : pos2;
		if (pos > -1) {
			return filePath.substring(0, pos + 1);
		}
		return null;
	}
	
	/**
	 * 获取文件路径中的文件
	 * 
	 * @param filePath
	 * @return 文件
	 */
	public static String getFileName(String filePath) {
		int pos1 = filePath.lastIndexOf("/");
		int pos2 = filePath.lastIndexOf("\\");
		int pos = pos1 > pos2 ? pos1 : pos2;
		if (pos > -1) {
			return filePath.substring(pos+1);
		}
		return filePath;
	}
	
	/**
	 * 获取文件路径中的文件名，不含扩展
	 * 
	 * @param filePath
	 * @return 文件
	 */
	public static String getFileNameWithoutSuffix(String filePath) {
		int pos1 = filePath.lastIndexOf("/");
		int pos2 = filePath.lastIndexOf("\\");
		int pos = pos1 > pos2 ? pos1 : pos2;
		if (pos > -1) {
			filePath = filePath.substring(pos+1);
		}
		pos = filePath.lastIndexOf(".");
		if (pos > -1) {
			filePath = filePath.substring(0, pos);
		}
		return filePath;
	}
	
	/**
	 * 将源目录中的文件复制到目标目录中
	 * 
	 * @param srcDir	源目
	 * @param destDir	目标目录
	 */
	public static void copyDirectory(String srcDir, String destDir) {
		try {
			org.apache.commons.io.FileUtils.copyDirectory(new File(srcDir), new File(destDir));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static void writeStringToFile(String filePath, String data) {
		try {
			org.apache.commons.io.FileUtils.writeStringToFile(new File(filePath), data);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * 读取文件的所有行
	 * 
	 * @param filePath
	 * @return 行数据集
	 */
	public static List readLines(String filePath) {
		try {
			return org.apache.commons.io.FileUtils.readLines(new File(filePath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 创建目录
	 * 
	 * @param dir
	 */
	public static void mkdirs(String dir) {
		File file = new File(dir);
		file.mkdirs();
	}
	
	/**
	 * 将已存在的文件复制到另一个路径处
	 * 
	 * @param source	原文件路
	 * @param dest		目录文件路径
	 */
	public static void copyFile(String source, String dest) {
		InputStream stream;
		try {
			stream = new FileInputStream(new File(source));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		writer(dest, stream);
	}
	
	/**
	 * 将已存在的文件复制到另一个路径处，并将原来的文件删除（相当于剪切
	 * 
	 * @param source	原文件路
	 * @param dest		目录文件路径
	 */
	public static void cutFile(String source, String dest) {
		copyFile(source, dest);
		deleteFile(source);
	}
	
	/**
	 * 删除指定路径的文
	 * 
	 * @param path	待删除文件的路径
	 */
	public static void deleteFile(String path) {
		try {
				org.apache.commons.io.FileUtils.forceDelete(new File(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 删除目录
	 * 
	 * @param dir
	 */
	public static void deleteDirectory(String dir) {
		try {
			org.apache.commons.io.FileUtils.deleteDirectory(new File(dir));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 将输入流写入到指定的文件中，如果文件不存在，将会创建新的文件
	 * 
	 * @param path		目标路径
	 * @param stream	输入
	 * @return {@link File}	返回写入后的文件引用
	 */
	public static void writer(String path, InputStream stream) {
		try {
			File file = new File(path);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStream out = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int read = 0;
			while ((read = stream.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			out.close();
			stream.close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	public static void loadDown(String realPath, OutputStream out){
		realPath = realPath.replaceAll("\\\\", "/");
		File file = new File(realPath);
        
        if (!file.exists()) {
            return ;
        }
        try {
        	BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = br.read(buf)) > 0){
            	out.write(buf, 0, len);
            }
            br.close();
            out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		FileUtils.deleteFile("E:/Project/gasp/war/temp/aboss_20100304.txt");
	}
}
