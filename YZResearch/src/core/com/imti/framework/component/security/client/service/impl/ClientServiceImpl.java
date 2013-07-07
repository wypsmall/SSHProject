package com.imti.framework.component.security.client.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import com.imti.framework.component.security.client.service.ClientService;

public class ClientServiceImpl implements ClientService {

	public boolean verifySignCode(String plainString, String signCode,
			String publicKeyFile) {
		boolean retVal = false;
		PublicKey publicKey = null;
		publicKey = (PublicKey) getPublicKeyFromCer(publicKeyFile);
		if (publicKey == null) {// 公钥加载错误
			return retVal;
		}
		byte[] plainText = null;
		plainText = plainString.getBytes();
		try {
			Signature sig = Signature.getInstance("SHA1WithRSA");
			sig.initVerify(publicKey);// PublicKey
			sig.update(plainText);
			if (sig.verify(hexs2byte(signCode))) {// Signature 通过
				retVal = true;
			} else {// Signature 失败
				retVal = false;
			}
		} catch (Throwable tr) {
			tr.printStackTrace();
			retVal = false;
		}
		return retVal;
	}
	public PublicKey getPublicKeyFromCer(String filePath) {
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			FileInputStream in = new FileInputStream(filePath);
			Certificate c = cf.generateCertificate(in);
			return c.getPublicKey();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	private  byte[] hexs2byte(String hexStr) {
		if (hexStr == null) {
			return null;
		}
		int len = hexStr.length();
		if (len % 2 != 0) {
			// 说明不符合编码格式
			return null;
		}
		byte[] b = new byte[len / 2];
		for (int n = 0; n < hexStr.length(); n = n + 2) {
			String oneHex = hexStr.substring(n, n + 2);
			b[n / 2] = (byte) java.lang.Integer.parseInt(oneHex, 16);
		}
		return b;
	}
	public String getMd5CheckOutCode(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
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
}
