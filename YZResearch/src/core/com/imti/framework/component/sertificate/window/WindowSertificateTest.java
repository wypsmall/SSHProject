package com.imti.framework.component.sertificate.window;

import com.imti.framework.component.sertificate.bean.CertificateVO;
import com.imti.framework.component.sertificate.window.service.WindowCertificateService;
import com.imti.framework.component.sertificate.window.service.impl.WindowCertificateServiceImpl;

public class WindowSertificateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WindowCertificateService service = new WindowCertificateServiceImpl();
    	CertificateVO vo = new CertificateVO();
    	//接入系统标识
    	vo.setSystemId("aboss");
    	
    	//证书相关的参数
    	vo.setKeyPass("aboss");
    	vo.setKeyAlias("aboss");
    	vo.setStorePass("aboss_0913");
    	
    	//证书依赖的jdk
    	vo.setJavaHome("D:/IBMjava/IBMjava");
    	
    	//证书生成时对给定的相关参数添加一点复杂度（加上一个随机码）
    	vo.setRandom("1234");
    	
    	//toolKey工具所需的参数
    	vo.setFirstName("imti");
    	vo.setCountry("cn");
    	vo.setArea("guangzhou");
    	vo.setUnitName("trust");
    	vo.setOrgName("tds");
    	vo.setProvince("guangdong");
    	
    	service.generateCertificate(vo,"C:/certificate/");
	}

}
