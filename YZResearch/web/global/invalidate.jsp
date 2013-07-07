<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%
	String context =request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>系统提示信息</title>
<link href="../css/mycss.css" rel="stylesheet" type="text/css" />
<script>
function toPage(url){
	window.location = url;
}
</script>
</head>
<body style="text-align:center">
		<p style="height:100px; vertical-align:middle;">
            <div style="background-color:#3c6084;width:40%; color:#fff; font-weight:bold;">
            	<div style="height:15px;">
           	  		<div class="corner_in_top_left"></div>
              		<div class="corner_in_top_right"></div>
              	</div>
               	<div style=" clear:both;color:#FFF; font-weight:bold" align="center">
               	  <label style="font-size:20px;font-family:隶书">系统提示</label>
                  <hr style="color:#FFFFFF"/>
               	  <div align="center">
                  	<table cellpadding="0" width="100%" cellspacing="0" style="color:#3c6084;border:2px #3c6084 solid;">
                        <tr>
                        <td align="center" width="1%" style="padding-bottom:5px;font-family:Wingdings; font-size:40px; color:#FFFFFF">K</td>
                        <td align="left" style="padding-bottom:5px;color:#FFFFFF">您长时间未操作系统，请重新登录</td>
                        </tr>
                        <tr>
                        <td colspan="2" align="right" style="padding-bottom:5px;">
                        <input type="button" value="重新登录"  class="button"/>&nbsp;
                        </td>
                        </tr>
                     </table>
                  </div>
           	    </div>
               	<div style="height:15px;">
           	  		<div class="corner_in_bottom_left"></div>
                    <div class="corner_in_bottom_right"></div>
              	</div>
            </div>
        </p>
</body>
</html>
