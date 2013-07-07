package com.imti.framework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 编码过滤器：针对编码进行统一处理
 * encoding 设置的编码类型
 * ignore 是否启用编码过滤器
 * 编码过滤器，设置对所有满足条件的
 * @author 曹刚
 *
 */
public class CodeFilter implements Filter {

	//日志记录
	//protected  ExtLog log = new ExtLog(CodeFilter.class);
	protected FilterConfig config = null;
	protected String encoding = null; 
	protected boolean ignore = true; 
	
	public void destroy() {
		this.config = null;
		this.encoding = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		if (ignore || (request.getCharacterEncoding() == null)) {
		   String encoding = selectEncoding(request);
		   if (encoding != null){
			   request.setCharacterEncoding(encoding);
			   response.setCharacterEncoding(encoding);
		   }
		} 
		chain.doFilter(request, response); 
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		this.encoding = config.getInitParameter("encoding");
		String value = config.getInitParameter("ignore");
		if (value == null){
			this.ignore = true;
		}else if (value.equalsIgnoreCase("true")){
			this.ignore = true;
		}else if (value.equalsIgnoreCase("yes")){
			this.ignore = true;
		}else{
		    this.ignore = false; 
		} 
	}
	protected String selectEncoding(ServletRequest request) { 
		return (this.encoding); 
	}
}
