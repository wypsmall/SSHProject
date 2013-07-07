package com.imti.common.util;

import com.imti.sysmgr.vo.UserVO;

public class CurrentUserThread {

	private static ThreadLocal<UserVO> currentUser = new ThreadLocal<UserVO>();

	public static final  UserVO get() {
		
		return (UserVO) currentUser.get();
	}

	public static final void set(UserVO userinfo) {
		
		currentUser.set(userinfo);
	}
	
	public static final String getUserName(){
		UserVO user = currentUser.get();
		if(user != null){
			return user.getUserName();
		}
		return "";
	}
}
