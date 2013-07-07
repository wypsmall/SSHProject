package com.imti.framework.component.security.client.service;

public interface ClientService {

	public boolean verifySignCode(String plainString, String signCode,String publicKeyFile);
	public String getMd5CheckOutCode(String filePath);
}
