package com.imti.sysmgr.bean;

import java.util.HashMap;
import java.util.Map;

public class ImtiMapFactory {

	//½ÇÉ«È«¾Ömap
	private static Map rsRoleMap = new HashMap();
	private static final String ROLE_INDEX = "role_index_";

	public static Map getRsRoleMap(String roleId) {
		Object object = rsRoleMap.get(ROLE_INDEX + roleId);
		if(object != null){
			return (Map)object;
		}
		return null;
	}

	public static void setRsRoleMap(Map rsRoleMap, String roleId) {
		rsRoleMap.put(ROLE_INDEX + roleId, rsRoleMap);
	}
	
	
}
