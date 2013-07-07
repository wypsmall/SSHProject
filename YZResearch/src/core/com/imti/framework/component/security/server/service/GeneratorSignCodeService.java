package com.imti.framework.component.security.server.service;



public interface GeneratorSignCodeService {
	/**
	 * 通过私钥，对指定文件进行MD5加密后，再进行签名
	 * @param filePath
	 * @param privateKeyFile
	 * @param storepass
	 * @param keyAlias
	 * @param keypass
	 * @return
	 */
	public String getSignCode(String filePath, String privateKeyFile, String storepass, String keyAlias, String keypass);
	/**
	 * 对指定文件进行MD5加密
	 * @param filePath
	 * @return
	 */
	public String getMd5CheckOutCode(String filePath) ;
}
