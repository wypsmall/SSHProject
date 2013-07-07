package com.imti.framework.component.security.server;

import com.imti.framework.component.security.server.service.GeneratorSignCodeService;
import com.imti.framework.component.security.server.service.impl.GeneratorSignCodeServiceImpl;

public class ImtiSecMgr {

	private static GeneratorSignCodeService service = new GeneratorSignCodeServiceImpl();

	public static String getSignCode(String filePath, String privateKeyFile, String storepass, String keyAlias, String keypass){
		String sign5Code = service.getSignCode(filePath, privateKeyFile, storepass, keyAlias, keypass);
		return sign5Code;
	}
	
	public static String getFileMd5Code(String filePath){
		return service.getMd5CheckOutCode(filePath);
	}
}
