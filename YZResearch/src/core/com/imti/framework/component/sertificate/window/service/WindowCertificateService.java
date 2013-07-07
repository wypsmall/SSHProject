package com.imti.framework.component.sertificate.window.service;

import com.imti.framework.component.sertificate.bean.CertificateVO;

public interface WindowCertificateService {
	/**
	 * 生正证书
	 * @param vo 证书相关信息的封装
	 * @param realPath 生成证书相关的文件的绝对路径
	 */
	public void generateCertificate(CertificateVO vo, String realPath);
}
