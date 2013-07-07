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
 * Ȩ����֤���������������
 * 1��jspҳ��·����֤
 * 2������servlet��·��������rsCode=��Դ����.�������룩
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
        	//��Ҫ����У��Ȩ�޵���Դ
            if (session == null) { 
            	//��δ��¼�������ǿ�е�½
            	nextFilter = false;
            	httpResponse.sendRedirect(preStr + "/"); 
            } else if (session.getAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER) == null){
            	//��δ��¼�������ǿ�е�½
            	nextFilter = false;
                session.invalidate();
                httpResponse.sendRedirect(preStr + "/"); 
            } else {  
            	String rsCode = httpRequest.getParameter("rsCode");
            	if(StringUtils.isEmpty(rsCode)){
            		nextFilter = false;//·���Ƿ�
            		httpResponse.setContentType("text/json; charset=utf-8");
            		try {
            			PrintWriter writer = response.getWriter();
            			extLog.info("{success:false,data:'·���Ƿ�'}");
            			writer.write("{success:false,data:'·���Ƿ�'}");
            		} catch (IOException e) {
            			extLog.error("��û�д˲�����Ȩ�ޣ�" + e.getMessage());
            		}
            	}else {
            		//���û���¼ʱ�����Ѿ������û���Ȩ�޷ŵ�session����
            		Object object = httpRequest.getSession().getAttribute(SessionConstant.IMTI_SESSION_USER_PEMISSION);
            		if(object != null && ((Map)object).get(rsCode) != null){
            			nextFilter = true;
            			CurrentUserThread.set((UserVO)session.getAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER));
            		}else {
            			nextFilter = false;//û�в���Ȩ��
                		httpResponse.setContentType("text/json; charset=utf-8");
                		try {
                			PrintWriter writer = response.getWriter();
                			extLog.info("{success:false,data:'��û�д˲�����Ȩ��'}");
                			writer.write("{success:false,data:'��û�д˲�����Ȩ��'}");
                		} catch (IOException e) {
                			extLog.error("��û�д˲�����Ȩ�ޣ�" + e.getMessage());
                		}
            		}
            	}
            }
        }else {
        	//����Ҫ����У��Ȩ�޵���Դ
        	if (session == null && (currentAction.indexOf("/login.jsp") != -1 
        			|| currentAction.endsWith("/"))) { 
        		
        		nextFilter = true;
            }else if(session == null ){
            	nextFilter = false;
            	httpResponse.sendRedirect(preStr + "/"); 
            }else if(currentAction.indexOf("/login.do") != -1){
            	nextFilter = true;
            }else if(session.getAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER) == null){
            	/*û�е�¼�Ļ��������ȵ���¼ҳ��*/
            	nextFilter = false;
            	session.invalidate();
            	httpResponse.sendRedirect(preStr + "/"); 
            }else {
            	/*�����Ѿ���¼���û���������Դ������Ȩ�޵�*/
            	nextFilter = true;
            	CurrentUserThread.set((UserVO)session.getAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER));
            }
        }
        
        if (nextFilter) {
            chain.doFilter(request, response);
        } 
       
    }
    
}
