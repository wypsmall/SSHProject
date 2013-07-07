package com.imti.framework.component.sertificate.window.service.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.imti.framework.component.sertificate.bean.CertificateVO;
import com.imti.framework.component.sertificate.common.SertificateAssert;
import com.imti.framework.component.sertificate.common.ShellMgr;
import com.imti.framework.component.sertificate.window.service.WindowCertificateService;

public class WindowCertificateServiceImpl implements WindowCertificateService {

	/**����ִ���ļ�������
	 * 	set SYS_ID=cmis
		set JAVA_HOME=D:\IBMjava\IBMjava
		set KEY_ALIAS=dsiss4%SYS_ID%
		set KEY_PASS=%SYS_ID%#2123#tds
		set KEY_STORE=%SYS_ID%.ks
		set STORE_PASS=tds_dsiss_0913
		set CSR_FILE=%SYS_ID%.csr
		set CER_FILE=%SYS_ID%.cer
		set FILE_OUT_HOME=%cd%
		echo ------start----- >  %SYS_ID%.param.txt
		echo storepass="%STORE_PASS%" >> %SYS_ID%.param.txt
		echo keyAlias="%KEY_ALIAS%"  >> %SYS_ID%.param.txt
		echo keypass="%KEY_PASS%"    >> %SYS_ID%.param.txt
		echo systemId="%SYS_ID%"    >> %SYS_ID%.param.txt
		echo ------end----- >>  %SYS_ID%.param.txt
		rem --����key��,
		%JAVA_HOME%/bin/keytool -genkey -alias %KEY_ALIAS% -keyalg RSA -keysize 1024  -validity 3650 -keypass %KEY_PASS% -keystore %FILE_OUT_HOME%/%KEY_STORE% -storepass %STORE_PASS% -provider org.bouncycastle.jce.provider.BouncyCastleProvider
		rem --�о�ks�ļ�����
		%JAVA_HOME%/bin/keytool -list -keystore %FILE_OUT_HOME%/%KEY_STORE% -storepass %STORE_PASS%
		rem --��������֤�������ļ����������������ǩ������ks�ļ��е�����
		%JAVA_HOME%/bin/keytool -certreq -keyalg RSA -alias %KEY_ALIAS% -file %FILE_OUT_HOME%/%CSR_FILE% -keypass %KEY_PASS% -keystore %FILE_OUT_HOME%/%KEY_STORE% -storepass %STORE_PASS% -provider org.bouncycastle.jce.provider.BouncyCastleProvider
		rem --��������֤�飨��ks�ļ��е�����
		%JAVA_HOME%/bin/keytool -export   -keyalg RSA -alias %KEY_ALIAS% -file %FILE_OUT_HOME%/%CER_FILE% -keypass %KEY_PASS% -keystore %FILE_OUT_HOME%/%KEY_STORE% -storepass %STORE_PASS% -provider org.bouncycastle.jce.provider.BouncyCastleProvider
	 * @param dir
	 * @param writeFileString
	 * @param vo
	 * @throws Exception
	 */
	private void createExecBatFile(String dir,StringBuffer writeFileString, CertificateVO vo) {
		writeFileString.append("set SYS_ID=" + vo.getSystemId()).append("\r\n").
		append("set JAVA_HOME=" + vo.getJavaHome()).append("\r\n").
		append("set KEY_ALIAS=" + vo.getKeyAlias()).append("\r\n").
		append("set KEY_PASS=" + vo.getKeyPass()).append("\r\n").
		append("set KEY_STORE=" + vo.getKeyStoreFile()).append("\r\n").
		append("set STORE_PASS=" + vo.getStorePass()).append("\r\n").
		append("set CSR_FILE=" + vo.getCsrFile()).append("\r\n").
		append("set CER_FILE=" + vo.getCerFile()).append("\r\n").
		append("set FILE_OUT_HOME="+dir+"").append("\n").
		append("echo ------start----- >  "+dir+"/%SYS_ID%.param.txt").append("\r\n").
		append("echo storepass=\"%STORE_PASS%\" >> "+dir+"/%SYS_ID%.param.txt").append("\r\n").
		append("echo keyAlias=\"%KEY_ALIAS%\"  >> "+dir+"/%SYS_ID%.param.txt").append("\r\n").
		append("echo keypass=\"%KEY_PASS%\"    >> "+dir+"/%SYS_ID%.param.txt").append("\r\n").
		append("echo systemId=\"%SYS_ID%\"    >> "+dir+"/%SYS_ID%.param.txt").append("\r\n").
		append("echo ------end----- >>  "+dir+"/%SYS_ID%.param.txt").append("\r\n").
		//�������������˱�������GBK��UTF8�Թ����У��ɴ಻Ҫע��
//		append("rem --����key��,").append("\r\n").
		append("%JAVA_HOME%/bin/keytool -genkey -alias %KEY_ALIAS% -keyalg RSA -keysize 1024  -validity 3650 -keypass %KEY_PASS% -keystore %FILE_OUT_HOME%/%KEY_STORE% -storepass %STORE_PASS% -provider org.bouncycastle.jce.provider.BouncyCastleProvider").append("\r\n").
//		append("rem --�о�ks�ļ�����").append("\r\n").
		append("%JAVA_HOME%/bin/keytool -list -keystore %FILE_OUT_HOME%/%KEY_STORE% -storepass %STORE_PASS%").append("\r\n").
//		append("rem --��������֤�������ļ����������������ǩ������ks�ļ��е�����").append("\r\n").
		append("%JAVA_HOME%/bin/keytool -certreq -keyalg RSA -alias %KEY_ALIAS% -file %FILE_OUT_HOME%/%CSR_FILE% -keypass %KEY_PASS% -keystore %FILE_OUT_HOME%/%KEY_STORE% -storepass %STORE_PASS% -provider org.bouncycastle.jce.provider.BouncyCastleProvider").append("\r\n").
//		append("rem --��������֤�飨��ks�ļ��е�����").append("\r\n").
		append("%JAVA_HOME%/bin/keytool -export   -keyalg RSA -alias %KEY_ALIAS% -file %FILE_OUT_HOME%/%CER_FILE% -keypass %KEY_PASS% -keystore %FILE_OUT_HOME%/%KEY_STORE% -storepass %STORE_PASS% -provider org.bouncycastle.jce.provider.BouncyCastleProvider").append("\r\n").
		append("\r\n").append("\r\n").append("\r\n");
	}
	
	private void createParamFile(StringBuffer paramString, CertificateVO vo){
		paramString.append(vo.getFirstName()).append("\n").
					append(vo.getUnitName()).append("\n").
					append(vo.getOrgName()).append("\n").
					append(vo.getArea()).append("\n").
					append(vo.getProvince()).append("\n").
					append(vo.getCountry()).append("\n").
					append("y").append("\n");
	}
	/**
	 * ��̬����run.bat�ļ�
	 * @param runFile run.bat�ļ�
	 * @throws IOException 
	 */
	private void createRunBatFile(String dir,String runFile, String systemId, String paramFilaPath) throws IOException {
		String key_exec_bat=dir+"/"+systemId + ".key.exec.bat";
		StringBuffer writeFileString = new StringBuffer();
 		writeFileString.append(key_exec_bat+" < " + paramFilaPath);
		File file = new File(runFile);
		FileUtils.writeStringToFile(file, writeFileString.toString(),"GBK"); 
	}

	public void generateCertificate(CertificateVO vo, String realPath) {
		SertificateAssert.isNotBlank(vo, "CertificateVO����Ϊ��");
		SertificateAssert.isNotBlank(vo.getSystemId(), "����ϵͳ��ʶ����Ϊ��");
		buildSertificateParams(vo);
		
		try {
			//����ִ���ļ�
			StringBuffer execBatFileString = new StringBuffer("");
			createExecBatFile(realPath, execBatFileString, vo);
			File file = new File(realPath + vo.getSystemId() + ".key.exec.bat");
			FileUtils.writeStringToFile(file, execBatFileString.toString(),"GBK");
			
			//���������ļ����ҽ������ļ��ŵ�Ŀ���ַ
			String paramFilaPath = realPath + vo.getSystemId() + "_default.key.input.txt";
			File paramFile = new File(paramFilaPath);
			StringBuffer writeParamFileString = new StringBuffer("");
			createParamFile(writeParamFileString, vo);
			FileUtils.writeStringToFile(paramFile, writeParamFileString.toString(),"GBK");
			
			//�ڵ�ǰĿ¼����run.bat��ʱ�ļ�
			String runFile = realPath + "run.bat";
			createRunBatFile(realPath,runFile, vo.getSystemId(), paramFilaPath);
			
			//ִ��run.bat�ļ�����֤��
			ShellMgr.exec(runFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void buildSertificateParams(CertificateVO vo) {
		if(StringUtils.isEmpty(vo.getKeyAlias())){//KEY_ALIASĬ��ֵ
			vo.setKeyAlias(vo.getRandom() + "_" + "%SYS_ID%");
		}else{
			vo.setKeyAlias(vo.getKeyAlias() + "%SYS_ID%");
		}
		
		if(StringUtils.isEmpty(vo.getKeyPass())){//KEY_PASSĬ��ֵ
			vo.setKeyPass("%SYS_ID%" + "_" + vo.getRandom());
		}else{
			vo.setKeyPass("%SYS_ID%" + vo.getKeyPass());
		}
		
		if(StringUtils.isEmpty(vo.getStorePass())){//STORE_PATH
			vo.setStorePass("tds_" + vo.getRandom());
		}else{
			vo.setStorePass(vo.getStorePass() + "_" + vo.getRandom());
		}
		
		
		if(StringUtils.isEmpty(vo.getKeyStoreFile())){//KEY_STOREĬ��ֵ
			vo.setKeyStoreFile("%SYS_ID%.ks");
		}else{
			if(vo.getKeyStoreFile().indexOf(".ks") == -1){
				vo.setKeyStoreFile(vo.getKeyStoreFile() + ".ks");
			}
		}
		
		if(StringUtils.isEmpty(vo.getCsrFile())){//csr�ļ� Ĭ���ļ���
			vo.setCsrFile("%SYS_ID%.csr");
		}else{
			if(vo.getCsrFile().indexOf(".csr") == -1){
				vo.setCsrFile(vo.getCsrFile() + ".csr");
			}
		}
		
		if(StringUtils.isEmpty(vo.getCerFile())){//cer�ļ� Ĭ���ļ���
			vo.setCerFile("%SYS_ID%.cer");
		}else{
			if(vo.getCerFile().indexOf(".cer") == -1){
				vo.setCerFile(vo.getCerFile() + ".cer");
			}
		}
	}
}
