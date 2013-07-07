package com.imti.framework.component.security.client;

import com.imti.framework.component.security.client.service.ClientService;
import com.imti.framework.component.security.client.service.impl.ClientServiceImpl;

public class SecClientMgr {

	private static ClientService service = new ClientServiceImpl();
	
	public static String getFileMd5Code(String filePath){
		return service.getMd5CheckOutCode(filePath);
	}
	
	
	public static boolean verifySignCode(String plainString, String signCode, String publicKeyFile){
		return service.verifySignCode(plainString, signCode, publicKeyFile);
	}
	
}
