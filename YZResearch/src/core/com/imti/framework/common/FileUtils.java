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
 * �ļ�����
 *	
 * @author ��������009-8-31����09:52:07
 * @version ftpmanage 1.0
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
	
	/**
	 * ���ļ�·����ȡ�ļ�����չ
	 * 
	 * @param path
	 * @return {@link String} �ļ���׺
	 */
	public static String getFileSuffix(String path) {
		int indexOf = path.lastIndexOf(".");
		return path.substring(indexOf+1);
	}
	
	/**
	 * ���ļ�·���л�ȡ��Ŀ¼·
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
	 * ��ȡ�ļ�·���е��ļ�
	 * 
	 * @param filePath
	 * @return �ļ�
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
	 * ��ȡ�ļ�·���е��ļ�����������չ
	 * 
	 * @param filePath
	 * @return �ļ�
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
	 * ��ԴĿ¼�е��ļ����Ƶ�Ŀ��Ŀ¼��
	 * 
	 * @param srcDir	ԴĿ
	 * @param destDir	Ŀ��Ŀ¼
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
	 * ��ȡ�ļ���������
	 * 
	 * @param filePath
	 * @return �����ݼ�
	 */
	public static List readLines(String filePath) {
		try {
			return org.apache.commons.io.FileUtils.readLines(new File(filePath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * ����Ŀ¼
	 * 
	 * @param dir
	 */
	public static void mkdirs(String dir) {
		File file = new File(dir);
		file.mkdirs();
	}
	
	/**
	 * ���Ѵ��ڵ��ļ����Ƶ���һ��·����
	 * 
	 * @param source	ԭ�ļ�·
	 * @param dest		Ŀ¼�ļ�·��
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
	 * ���Ѵ��ڵ��ļ����Ƶ���һ��·����������ԭ�����ļ�ɾ�����൱�ڼ���
	 * 
	 * @param source	ԭ�ļ�·
	 * @param dest		Ŀ¼�ļ�·��
	 */
	public static void cutFile(String source, String dest) {
		copyFile(source, dest);
		deleteFile(source);
	}
	
	/**
	 * ɾ��ָ��·������
	 * 
	 * @param path	��ɾ���ļ���·��
	 */
	public static void deleteFile(String path) {
		try {
				org.apache.commons.io.FileUtils.forceDelete(new File(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * ɾ��Ŀ¼
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
	 * ��������д�뵽ָ�����ļ��У�����ļ������ڣ����ᴴ���µ��ļ�
	 * 
	 * @param path		Ŀ��·��
	 * @param stream	����
	 * @return {@link File}	����д�����ļ�����
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
