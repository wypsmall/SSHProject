package com.imti.framework.component.security.server;


public class SecurityServerTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//证书相关参数
		String storepass = "tds_dsiss_0913";
		String keyAlias = "dsiss4crv";
		String keypass = "crv#2123#tds";
		//私钥文件
		String privateKeyFile = "F:\\sys_crv.ks";
		//被加密、签名文件
		String filePath = "F:/tel.txt";
		String signCode = ImtiSecMgr.getSignCode(filePath, privateKeyFile, storepass, keyAlias, keypass);
		System.out.println("签名：" + signCode);
		System.out.println("加密字符串：" + ImtiSecMgr.getFileMd5Code("F:/tel.txt"));
	}
}
