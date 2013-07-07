package com.imti.framework.component.sertificate.linux.service.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.imti.framework.component.sertificate.bean.CertificateVO;
import com.imti.framework.component.sertificate.common.SertificateAssert;
import com.imti.framework.component.sertificate.common.ShellMgr;
import com.imti.framework.component.sertificate.linux.service.LinuxCertificateService;

public class LinuxCertificateServiceImpl implements LinuxCertificateService {

	/**创建执行文件的内容
	 * 	SYS_ID=sys_crv
		JAVA_HOME=$JAVA_HOME
		KEY_ALIAS=dsiss4$SYS_ID
		KEY_PASS=$SYS_ID
		KEY_STORE=$SYS_ID.ks
		STORE_PASS=tds_dsiss_0913
		CSR_FILE=$SYS_ID.csr
		CER_FILE=$SYS_ID.cer
		certifycate_dir='ddddd/dddd/dddd/ddddd/dddd/ddddd'
		
		echo ------start----- >  $SYS_ID.param.txt
		echo storepass="$STORE_PASS" >> $SYS_ID.param.txt
		echo keyAlias="$KEY_ALIAS"  >> $SYS_ID.param.txt
		echo keypass="$KEY_PASS"    >> $SYS_ID.param.txt
		echo systemId="$SYS_ID"    >> $SYS_ID.param.txt
		echo ------end----- >>  $SYS_ID.param.txt
		
		FILE_OUT_HOME=$certifycate_dir/ks
		mkdir $FILE_OUT_HOME
		$JAVA_HOME/bin/keytool -genkey -alias $KEY_ALIAS -keyalg RSA -keysize 1024  -validity 3650 -keypass $KEY_PASS -keystore $FILE_OUT_HOME/$KEY_STORE -storepass $STORE_PASS -provider org.bouncycastle.jce.provider.BouncyCastleProvider
		
		$JAVA_HOME/bin/keytool -list -keystore $FILE_OUT_HOME/$KEY_STORE -storepass $STORE_PASS
		
		FILE_OUT_HOME=$certifycate_dir/csr
		mkdir $FILE_OUT_HOME
		$JAVA_HOME/bin/keytool -certreq -keyalg RSA -alias $KEY_ALIAS -file $FILE_OUT_HOME/$CSR_FILE -keypass $KEY_PASS -keystore $FILE_OUT_HOME/$KEY_STORE -storepass $STORE_PASS -provider org.bouncycastle.jce.provider.BouncyCastleProvider
		
		FILE_OUT_HOME=$certifycate_dir/cer
		mkdir $FILE_OUT_HOME
		$JAVA_HOME/bin/keytool -export   -keyalg RSA -alias $KEY_ALIAS -file $FILE_OUT_HOME/$CER_FILE -keypass $KEY_PASS -keystore $FILE_OUT_HOME/$KEY_STORE -storepass $STORE_PASS -provider org.bouncycastle.jce.provider.BouncyCastleProvider
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
		append("echo ------end----- >>  "+dir+"/%SYS_ID%.param.txt").append("\r\n")
		;
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
	 * 动态创建run.bat文件
	 * @param runFile run.bat文件
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
		SertificateAssert.isNotBlank(vo, "CertificateVO不能为空");
		SertificateAssert.isNotBlank(vo.getSystemId(), "接入系统标识不能为空");
		buildSertificateParams(vo);
		
		try {
			//创建执行文件
			StringBuffer execBatFileString = new StringBuffer("");
			createExecBatFile(realPath, execBatFileString, vo);
			File file = new File(realPath + vo.getSystemId() + ".key.exec.bat");
			FileUtils.writeStringToFile(file, execBatFileString.toString(),"GBK");
			
			//创建参数文件并且将参数文件放到目标地址
			String paramFilaPath = realPath + vo.getSystemId() + "_default.key.input.txt";
			File paramFile = new File(paramFilaPath);
			StringBuffer writeParamFileString = new StringBuffer("");
			createParamFile(writeParamFileString, vo);
			FileUtils.writeStringToFile(paramFile, writeParamFileString.toString(),"GBK");
			
			//在当前目录创建run.bat临时文件
			String runFile = realPath + "run.bat";
			createRunBatFile(realPath,runFile, vo.getSystemId(), paramFilaPath);
			
			//执行run.bat文件生成证书
			ShellMgr.exec(runFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void buildSertificateParams(CertificateVO vo) {
		if(StringUtils.isEmpty(vo.getKeyAlias())){//KEY_ALIAS默认值
			vo.setKeyAlias(vo.getRandom() + "_" + "%SYS_ID%");
		}else{
			vo.setKeyAlias(vo.getKeyAlias() + "%SYS_ID%");
		}
		
		if(StringUtils.isEmpty(vo.getKeyPass())){//KEY_PASS默认值
			vo.setKeyPass("%SYS_ID%" + "_" + vo.getRandom());
		}else{
			vo.setKeyPass("%SYS_ID%" + vo.getKeyPass());
		}
		
		if(StringUtils.isEmpty(vo.getStorePass())){//STORE_PATH
			vo.setStorePass("tds_" + vo.getRandom());
		}else{
			vo.setStorePass(vo.getStorePass() + "_" + vo.getRandom());
		}
		
		
		if(StringUtils.isEmpty(vo.getKeyStoreFile())){//KEY_STORE默认值
			vo.setKeyStoreFile("%SYS_ID%.ks");
		}else{
			if(vo.getKeyStoreFile().indexOf(".ks") == -1){
				vo.setKeyStoreFile(vo.getKeyStoreFile() + ".ks");
			}
		}
		
		if(StringUtils.isEmpty(vo.getCsrFile())){//csr文件 默认文件名
			vo.setCsrFile("%SYS_ID%.csr");
		}else{
			if(vo.getCsrFile().indexOf(".csr") == -1){
				vo.setCsrFile(vo.getCsrFile() + ".csr");
			}
		}
		
		if(StringUtils.isEmpty(vo.getCerFile())){//cer文件 默认文件名
			vo.setCerFile("%SYS_ID%.cer");
		}else{
			if(vo.getCerFile().indexOf(".cer") == -1){
				vo.setCerFile(vo.getCerFile() + ".cer");
			}
		}
	}
}
