package com.imti.framework.component.security.server.service;



public interface GeneratorSignCodeService {
	/**
	 * ͨ��˽Կ����ָ���ļ�����MD5���ܺ��ٽ���ǩ��
	 * @param filePath
	 * @param privateKeyFile
	 * @param storepass
	 * @param keyAlias
	 * @param keypass
	 * @return
	 */
	public String getSignCode(String filePath, String privateKeyFile, String storepass, String keyAlias, String keypass);
	/**
	 * ��ָ���ļ�����MD5����
	 * @param filePath
	 * @return
	 */
	public String getMd5CheckOutCode(String filePath) ;
}
