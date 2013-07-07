package com.imti.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.imti.framework.common.StringUtils;
import com.imti.framework.common.constant.SessionConstant;
import com.imti.sysmgr.vo.UserVO;

public class ImtiUtil {

	
	public static UserVO getUser(HttpServletRequest request){
		UserVO userVO = (UserVO)request.getSession().getAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER);
		if(userVO != null){
			return userVO;
		}
		return null ;
	}
	public static String getUserName(HttpServletRequest request){
		UserVO userVO = (UserVO)request.getSession().getAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER);
		if(userVO != null){
			return userVO.getUserName();
		}
		return "" ;
	}
	public static String getZtId(HttpServletRequest request){
		String ztId = (String)request.getSession().getAttribute(SessionConstant.IMTI_SESSION_LOGIN_ZTID);
		return ztId ;
	}

	public static boolean permissionVerify(HttpServletRequest request, String rsCode) {
		Map pemissionMap = null;
		if(StringUtils.isEmpty(rsCode)){
			return false;
		}
		UserVO userVO = (UserVO)request.getSession().getAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER);
		if(userVO == null){
			return false;
		}else {
			pemissionMap = userVO.getRsOpeCodeMap();
			if(pemissionMap == null){
				return false;
			}
		}
		
		Object object = pemissionMap.get(rsCode);
		if(object != null){
			return true;
		}
		return false;
	}
	public static Map getPermissionMap(HttpServletRequest request){
		Object object = request.getSession().getAttribute(SessionConstant.IMTI_SESSION_USER_PEMISSION);
		if(object != null){
			return (Map)object;
		}
		return new HashMap();
	}
}
