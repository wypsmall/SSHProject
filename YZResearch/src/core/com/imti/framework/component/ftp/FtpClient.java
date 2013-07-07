/**
 * 
 */
package com.imti.framework.component.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.imti.framework.common.FileUtils;
import com.imti.framework.common.StringUtils;

/**
 * ftp 工具
 *	
 * @author 曹刚
 * @version 
 */
public class FtpClient {

	private FTPClient ftpClient = new FTPClient();
	public String ROOT_DIR;
	
	private String host;
	private int port;
	private String username;
	private String password;
	private String charset;
	
	public static final String CHARSET_DEFAULT = "GBK";
	
	public String getCharset() {
		if(StringUtils.isEmpty(charset)){
			charset = CHARSET_DEFAULT;
		}
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public FtpClient(String host, int port, String username, String password) {
		this.host = host;
		this.port = port;
		this.password = password;
		this.username = username;
	}
	public FtpClient(String host, int port, String username, String password, String charset) {
		this.host = host;
		this.port = port;
		this.password = password;
		this.username = username;
		this.charset = charset;
	}
	private FTPClient getFTPClient() {
		try {
			if (!ftpClient.isConnected()) {
				ftpClient.connect(this.host, this.port);
				ftpClient.login(this.username, this.password);
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				int replyCode = ftpClient.getReplyCode();
				if (!FTPReply.isPositiveCompletion(replyCode)) {
					ftpClient.disconnect();
					throw new RuntimeException("FTP连接失败");
				}
				ROOT_DIR = ftpClient.printWorkingDirectory();
			}
		} catch (SocketException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return ftpClient;
	}
	
	private void disconnect() throws IOException {
		ftpClient.logout();
		ftpClient.disconnect();
	}
	
	private String encoding(String s) {
		return StringUtils.transcoding(s, getCharset(), "ISO-8859-1");
	}
	
	/**
	 * 更改工作目录
	 * @param dir	目标目录路径
	 * @param isCreate	目录不存在时，是否创
	 * @throws IOException
	 */
	private void changeWorkingDirectory(String dir, boolean isCreate) throws IOException {
		String[] dirs = dir.split("/");
		while(!getFTPClient().printWorkingDirectory().equals(ROOT_DIR) && getFTPClient().changeToParentDirectory()){}
		for (String d : dirs) {
			if (!d.equals("")) {
				d = encoding(d);
				boolean isSuccess = getFTPClient().changeWorkingDirectory(d);
				if (!isSuccess) {
					if (isCreate) {
						getFTPClient().makeDirectory(d);
						getFTPClient().changeWorkingDirectory(d);
					} else {
						throw new RuntimeException("目录不存在：" + dir);
					}
				}
			}
		}
	}
	
	/**
	 * FTP服务器上的文件下
	 * 
	 * @param remotePath	FTP远程文件路径
	 * @param localDir	本地存储目录
	 * @throws IOException
	 */
	public void download(String remotePath, String localDir) throws IOException {
		String dir = FileUtils.getDirectoryPath(remotePath);
		if (StringUtils.isNotBlank(dir)) {
			changeWorkingDirectory(dir, false);
		}
		String fileName = FileUtils.getFileName(remotePath);
		String remoteFileName = encoding(fileName);
		//创建本地目录
		FileUtils.mkdirs(localDir);
		FileOutputStream outStream = new FileOutputStream(new File(localDir + fileName));
		getFTPClient().retrieveFile(remoteFileName, outStream);
		if(outStream != null){
			outStream.close();
		}
		disconnect();
	}
	/**
	 * FTP服务器上的文件下
	 * 
	 * @param srcFile	源文件
	 * @param destFile	目标路径
	 * @throws IOException
	 */
	public void move(String srcFile, String destFile) throws IOException {
		//先创建目标的文件夹
		String destDir = FileUtils.getDirectoryPath(destFile);
		changeWorkingDirectory(destDir, true);
		//到被转移文件的工作目录
		String dir = FileUtils.getDirectoryPath(srcFile);
		changeWorkingDirectory(dir, true);
		//执行迁移
		getFTPClient().rename(encoding(srcFile), encoding(destFile));
		disconnect();
	}
	/**
	 * 下载FTP服务器上的一个目
	 * @param remoteDir	FTP服务器目
	 * @param localDir	本地存储目录
	 * @throws IOException
	 */
	public void downloadDirectory(String remoteDir, String localDir) throws IOException {
		changeWorkingDirectory(remoteDir, false);
		FileUtils.mkdirs(localDir);
		FTPFile[] files = getFTPClient().listFiles();
		for (FTPFile file : files) {
			if (file.isFile()) {
				FileOutputStream outStream =  new FileOutputStream(new File(localDir + file.getName()));
				getFTPClient().retrieveFile(file.getName(), outStream);
				if(outStream != null){
					outStream.close();
				}
			} else if (file.isDirectory() && !file.getName().equals(".") && !file.getName().equals("..")) {
				FileUtils.mkdirs(localDir + file.getName());
				downloadDirectory(remoteDir + file.getName() + "/", localDir + file.getName() + "/");
			}
		}
		disconnect();
	}
	
	/**
	 * 获取指定目录下的文件（或目录）列
	 * 
	 * @param remoteDir
	 * @return 文件（或目录）数
	 * @throws IOException
	 */
	public FTPFile[] listFiles(String remoteDir) throws IOException {
		changeWorkingDirectory(remoteDir, false);
		FTPFile[] files = getFTPClient().listFiles();
		disconnect();
		return files;
	}
	
	/**
	 * 向FTP服务器上传文
	 * @param remoteDir	FTP服务器的存储目录
	 * @param localFile	本地文件路径
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void upload(String remoteDir, String localFile) throws FileNotFoundException, IOException {
		changeWorkingDirectory(remoteDir, true);
		String fileName = FileUtils.getFileName(localFile);
		fileName = encoding(fileName);
		FileInputStream inStream = new FileInputStream(new File(localFile));
		getFTPClient().storeFile(fileName, inStream);
		if(inStream != null){
			inStream.close();
		}
		disconnect();
	}
	
	/**
	 * 删除指定FTP服务器上的指定文
	 * @param remotePath 文件路径。如dir/movies/film.rm
	 * @throws IOException
	 */
	public void delete(String remotePath) throws IOException {
		String dir = FileUtils.getDirectoryPath(remotePath);
		changeWorkingDirectory(dir, false);
		String fileName = FileUtils.getFileName(remotePath);
		getFTPClient().deleteFile(encoding(fileName));
		disconnect();
	}
}
