package com.imti.framework.component.security.server.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import com.imti.framework.component.security.server.constant.SecurityMessageConstant;
import com.imti.framework.component.security.server.service.GeneratorSignCodeService;

public class GeneratorSignCodeServiceImpl implements GeneratorSignCodeService {
	
	public String getSignCode(String filePath, String privateKeyFile, String storepass, String keyAlias, String keypass) {
		/*
		 * 1、得到文件的MD5校验码
		 * 2、根据私钥文件、证书参数生成私钥
		 * 3、私钥对MD5校验码进行签名
		 */
		File file=new File(filePath);
		if(!file.exists()){
			return null;
		}
		String plainString = getMd5CheckOutCode(filePath);
		String signCode=null;
		PrivateKey privateKey=null;
		privateKey=(PrivateKey)getPrivateKey(privateKeyFile, storepass, keyAlias, keypass);
		if(privateKey==null){//私钥加载错误
			throw new NullPointerException(SecurityMessageConstant.VERIFY_CODE_PUBLICKEY_NOT_FOUNT_ERROR);
		}
		byte[] plainText =null;
		plainText =plainString.getBytes();
		try{
			//使用私?_签名
			Signature sig=Signature.getInstance("SHA1WithRSA");
			sig.initSign(privateKey);//PrivateKey
			sig.update(plainText);
			byte[] signature=sig.sign();
			signCode=byte2hex (signature,"");
		}catch(Throwable tr){
			tr.printStackTrace();
		}
		return signCode;
	}
	
	private PrivateKey getPrivateKey(String privateKeyFile, String storepass, String keyAlias, String keypass) {
		try {
			FileInputStream in = new FileInputStream(privateKeyFile);
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(in, storepass.toCharArray());
			PrivateKey privateKey = (PrivateKey) ks.getKey(keyAlias, keypass.toCharArray());
			return privateKey;
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getMd5CheckOutCode(String filePath) {
		File file=new File(filePath);
		if(!file.exists()){
			return null;
		}
		String retVal = "";
		byte[] pass_bytes;
		try {
			pass_bytes = getFileContentMessageDigest(file, "MD5");
			retVal = byte2hex(pass_bytes, "");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	private String byte2hex(byte[] b, String splitChar) {
		if (b == null) {
			return "";
		}
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
			if (n < b.length - 1) {
				hs = hs + splitChar;
			}
		}
		return hs;
	}
	private byte[] getFileContentMessageDigest(File file,String algorithm) throws NoSuchAlgorithmException {
		if (!file.exists()) {
			return null;
		}
		MessageDigest alg = MessageDigest.getInstance(algorithm);
		try {
			// 添加要进行计算摘要的信息
			FileInputStream fis = new FileInputStream(file);
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = fis.read(b)) > 0) {
				alg.update(b, 0, len);
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 计算出摘要
		return alg.digest();
	}
}
