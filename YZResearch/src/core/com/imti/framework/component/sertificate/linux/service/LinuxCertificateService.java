package com.imti.framework.component.sertificate.linux.service;

import com.imti.framework.component.sertificate.bean.CertificateVO;

public interface LinuxCertificateService {
	/**
	 * 生正证书
	 * @param vo 证书相关信息的封装
	 * @param realPath 生成证书相关的文件的绝对路径
	 */
	public void generateCertificate(CertificateVO vo, String realPath);
}
