package com.imti.framework.component.security.server;


public class SecurityServerTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//֤����ز���
		String storepass = "tds_dsiss_0913";
		String keyAlias = "dsiss4crv";
		String keypass = "crv#2123#tds";
		//˽Կ�ļ�
		String privateKeyFile = "F:\\sys_crv.ks";
		//�����ܡ�ǩ���ļ�
		String filePath = "F:/tel.txt";
		String signCode = ImtiSecMgr.getSignCode(filePath, privateKeyFile, storepass, keyAlias, keypass);
		System.out.println("ǩ����" + signCode);
		System.out.println("�����ַ�����" + ImtiSecMgr.getFileMd5Code("F:/tel.txt"));
	}
}
