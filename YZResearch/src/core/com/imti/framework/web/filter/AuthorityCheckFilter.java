package com.imti.framework.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.util.Log;

import com.imti.common.util.CurrentUserThread;
import com.imti.framework.common.StringUtils;
import com.imti.framework.common.constant.SessionConstant;
import com.imti.framework.web.log.ExtLog;
import com.imti.sysmgr.vo.UserVO;

/**
 * 权限验证：包含两个方面的
 * 1、jsp页面路径验证
 * 2、请求servlet的路径（含有rsCode=资源编码.操作编码）
 * @author Administrator
 *
 */
public class AuthorityCheckFilter implements Filter {
    private String invalidateFile;  
    private String ignoreFiles; 
    private ExtLog extLog = new ExtLog(AuthorityCheckFilter.class);;
	public static List<String> notNeedUrl = new ArrayList();

    public void init(FilterConfig filterConfig) throws ServletException {
    	invalidateFile = filterConfig.getInitParameter("invalidateFile");
        ignoreFiles = filterConfig.getInitParameter("ignoreFiles");
        if(notNeedUrl.size()<1) {
	        //notNeedUrl.add("/mainpage.jsp"); 
        }
    }

    public void destroy() {
    	invalidateFile = null;
    	ignoreFiles = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        boolean nextFilter = false;

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        HttpSession session = httpRequest.getSession(false);
        String currentAction = httpRequest.getRequestURI();
        String preStr = httpRequest.getContextPath();
        extLog.info("******************rsCode="+httpRequest.getParameter("rsCode")+"***************************");
        currentAction = currentAction.replace(preStr, "");
        if (ignoreFiles.indexOf(currentAction) < 0 ) {
        	//需要进行校验权限的资源
            if (session == null) { 
            	//“未登录”情况下强行登陆
            	nextFilter = false;
            	httpResponse.sendRedirect(preStr + "/"); 
            } else if (session.getAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER) == null){
            	//“未登录”情况下强行登陆
            	nextFilter = false;
                session.invalidate();
                httpResponse.sendRedirect(preStr + "/"); 
            } else {  
            	String rsCode = httpRequest.getParameter("rsCode");
            	if(StringUtils.isEmpty(rsCode)){
            		nextFilter = false;//路径非法
            		httpResponse.setContentType("text/json; charset=utf-8");
            		try {
            			PrintWriter writer = response.getWriter();
            			extLog.info("{success:false,data:'路径非法'}");
            			writer.write("{success:false,data:'路径非法'}");
            		} catch (IOException e) {
            			extLog.error("您没有此操作的权限：" + e.getMessage());
            		}
            	}else {
            		//在用户登录时，就已经将该用户的权限放到session中了
            		Object object = httpRequest.getSession().getAttribute(SessionConstant.IMTI_SESSION_USER_PEMISSION);
            		if(object != null && ((Map)object).get(rsCode) != null){
            			nextFilter = true;
            			CurrentUserThread.set((UserVO)session.getAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER));
            		}else {
            			nextFilter = false;//没有操作权限
                		httpResponse.setContentType("text/json; charset=utf-8");
                		try {
                			PrintWriter writer = response.getWriter();
                			extLog.info("{success:false,data:'您没有此操作的权限'}");
                			writer.write("{success:false,data:'您没有此操作的权限'}");
                		} catch (IOException e) {
                			extLog.error("您没有此操作的权限：" + e.getMessage());
                		}
            		}
            	}
            }
        }else {
        	//不需要进行校验权限的资源
        	if (session == null && (currentAction.indexOf("/login.jsp") != -1 
        			|| currentAction.endsWith("/"))) { 
        		
        		nextFilter = true;
            }else if(session == null ){
            	nextFilter = false;
            	httpResponse.sendRedirect(preStr + "/"); 
            }else if(currentAction.indexOf("/login.do") != -1){
            	nextFilter = true;
            }else if(session.getAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER) == null){
            	/*没有登录的话就让他先到登录页面*/
            	nextFilter = false;
            	session.invalidate();
            	httpResponse.sendRedirect(preStr + "/"); 
            }else {
            	/*对于已经登录的用户，公共资源都是有权限的*/
            	nextFilter = true;
            	CurrentUserThread.set((UserVO)session.getAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER));
            }
        }
        
        if (nextFilter) {
            chain.doFilter(request, response);
        } 
       
    }
    
}
