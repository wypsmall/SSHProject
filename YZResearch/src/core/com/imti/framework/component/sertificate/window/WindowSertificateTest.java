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
    	//����ϵͳ��ʶ
    	vo.setSystemId("aboss");
    	
    	//֤����صĲ���
    	vo.setKeyPass("aboss");
    	vo.setKeyAlias("aboss");
    	vo.setStorePass("aboss_0913");
    	
    	//֤��������jdk
    	vo.setJavaHome("D:/IBMjava/IBMjava");
    	
    	//֤������ʱ�Ը�������ز������һ�㸴�Ӷȣ�����һ������룩
    	vo.setRandom("1234");
    	
    	//toolKey��������Ĳ���
    	vo.setFirstName("imti");
    	vo.setCountry("cn");
    	vo.setArea("guangzhou");
    	vo.setUnitName("trust");
    	vo.setOrgName("tds");
    	vo.setProvince("guangdong");
    	
    	service.generateCertificate(vo,"C:/certificate/");
	}

}
