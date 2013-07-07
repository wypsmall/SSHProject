<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="java.util.*,com.imti.framework.component.vopomapping.utils.PropertyUtils"%>
<html>
<head>
	<%
		String context = request.getContextPath();
		String imtiSysName=PropertyUtils.getProperty("imti.sys.name","");
	%>
	<link rel="stylesheet" type="text/css" href="<%=context%>/ext3.0/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="<%=context%>/ext3.0/resources/css/ext-extend.css" />
	<script type="text/javascript" src="<%=context%>/ext3.0/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext-all.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext_extend.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext_action.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext_grid.js"></script>
	<script type="text/javascript" src="<%=context%>/view/ext/yzsystem/hr/employeelist.js"></script>
	<script type="text/javascript" src="<%=context%>/view/ext/yzsystem/hr/employeeEdit.js"></script>
	<script type="text/javascript" src="<%=context%>/view/ext/yzsystem/hr/empgrouplist.js"></script>
<title></title>
</head>
<body>
<script type="text/javascript">
var context = "<%=context%>";
var submmitUrl= "/yzsystem/hr/hrmgr.do?method=insertHrEmployee&rsCode=EMPMGR_PAGE_INSERT";
</script>
<div id="center"></div>
</body>
</html>