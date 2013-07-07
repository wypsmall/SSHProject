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
		 * 1���õ��ļ���MD5У����
		 * 2������˽Կ�ļ���֤���������˽Կ
		 * 3��˽Կ��MD5У�������ǩ��
		 */
		File file=new File(filePath);
		if(!file.exists()){
			return null;
		}
		String plainString = getMd5CheckOutCode(filePath);
		String signCode=null;
		PrivateKey privateKey=null;
		privateKey=(PrivateKey)getPrivateKey(privateKeyFile, storepass, keyAlias, keypass);
		if(privateKey==null){//˽Կ���ش���
			throw new NullPointerException(SecurityMessageConstant.VERIFY_CODE_PUBLICKEY_NOT_FOUNT_ERROR);
		}
		byte[] plainText =null;
		plainText =plainString.getBytes();
		try{
			//ʹ��˽?_ǩ��
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
			// ���Ҫ���м���ժҪ����Ϣ
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
		// �����ժҪ
		return alg.digest();
	}
}
