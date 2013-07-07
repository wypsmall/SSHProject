package com.imti.framework.component.security.client;


public class SecurityClientTest {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String signCode = "5d6935f9b0d07222107bb3585f77f003e6d851cb6e2a582d33c4bb4f200f4a7965186546f312b960195c841837f0ab8793ed6df11af93d40c83cd344bfd9385ca6dbde5b7a23c1297ce47c8641d03a8ebbf6a29c17b5420d2579d4b94d3730b06ab5ed808843fdee46e3f5e00f7fe404b30ba881370aff1eb0ef9aedad1b6d46";
		String publicKeyFile = "E:\\myeclipse7\\workspace\\dsiss_bie_config_source\\war\\WEB-INF\\classes\\cer\\sys_crv.cer";
		String plainString = "7d5e0770c340bf4627b1534e5dffd4d5";
		
		boolean verify = SecClientMgr.verifySignCode(plainString, signCode, publicKeyFile);
		if(verify){
			System.out.println("sss");
		}
		String localMd5Code = SecClientMgr.getFileMd5Code("F:/tel.txt");
		if(localMd5Code.equals(plainString)){
			System.out.println("sss");
		}else{
			System.out.println("sss");
		}
	}

}
