<%@ page contentType="text/html;charset=GB2312"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
	<head> 
		<title>"新增用户</title>
		<%
			String context = request.getContextPath();
		%>
	</head>
<script language="javascript" src="<%=context%>/js/Validator.js"></script>
<link href="<%=context%>/style/css1.css" rel="stylesheet" type="text/css">
<body scroll="no">
	<form action="firstCheck.do" method="post" name="myform" enctype="multipart/form-data">
		<input type=hidden id="cusId" name="cusId"/>
		<table width="98%" border="0" cellpadding="0" cellspacing="0" align="center" style="margin-top: -15px">
			<tr><td width="100%" class="title_bg1">新增用户</td></tr>
		</table>
		<table width="98%" style="margin-top:3px;" border="1" bordercolor="#96B1D3" bordercolordark="#FFFFFF" cellpadding="3" cellspacing="0" align="center">
			<tr>
				<td class="text_a" align="left">登陆账号：</td>
				<td><input type="text" id="loginId" name="loginId" class="input" style="width:100" /></td>
				<td class="text_a" align="left">密码：</td>
				<td><input type="text" id="password" name="password" class="input" style="width:100" /></td>
			</tr>
			<tr>
				<td class="text_a" align="left">用户昵称：</td>
				<td><input type="text" class="input"  id="nickName" name="nickName" style="width:100" /></td>
				<td class="text_a" align="left">用户名称(关联客户)：</td>
				<td><input type="text" class="input"  id="cusName" name="cusName" style="width:100"  onclick="selCustomer()" /></td>
			</tr>
		</table>
		<table width="98%" style="margin-top:3px;" border="0" cellpadding="3" cellspacing="0" align="center">
			<tr>
				<td align="center">
					<a href="#" onclick="save()"><img src="<%=context%>/images/save_btn.gif" border="0"> </a>
					<a href="#" onclick="javascript:window.close()"> <img src="<%=context%>/images/close_btn.gif" border="0"> </a>
				</td>
			</tr>
		</table>
	</form>
</body>
<script>
function selCustomer(){
	var iHeight = 550;
	var iWidth  = 600;
	var iTop = (window.screen.availHeight-30-iHeight)/2; 
	var iLeft = (window.screen.availWidth-10-iWidth)/2;
	var  url="<%=context%>/jplms/customer/customer.do?method=getAllCustomer&type=cusUser&rsCode=JP_CUS_USER_ADD";
	var styleStr = "height="+iHeight+",width="+iWidth+",top="+iTop+",left="+iLeft+","+"toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no";
	var OpenWindow =window.open(url,"选择客户",styleStr);
	try{
  		OpenWindow.focus();
	}catch(e){}
}
function save(){
	if(document.all.cusName.value == ""){
		alert("关联客户不能为空！");
		return ;
	}
	if(document.all.loginId.value == ""){
		alert("登陆账号不能为空！");
		return ;
	}
	if(document.all.password.value == ""){
		alert("登陆密码不能为空！");
		return ;
	}
	var loginId = document.all.loginId.value;
	var userName = document.all.cusName.value;
	var password = document.all.password.value;
	var userId = document.all.cusId.value;
	var nickName = document.all.nickName.value;
	 myform.action="<%=context%>/jplms/adapter/sysmgr.do?method=saveCusUser&rsCode=JP_CUS_USER&loginId="+loginId
	 +"&userName="+userName+"&password="+password+"&userId="+userId+"&nickName="+nickName;
	 myform.submit();
}
</script>
</html>
