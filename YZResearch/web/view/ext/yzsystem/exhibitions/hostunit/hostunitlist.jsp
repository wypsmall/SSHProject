<%@ page contentType="text/html;charset=GBK"%>
<html>
	<head>
	<%
		String context = request.getContextPath();
	%>
	<link rel="stylesheet" type="text/css" href="<%=context%>/ext3.0/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="<%=context%>/ext3.0/resources/css/ext-extend.css" />
	<script type="text/javascript" src="<%=context%>/ext3.0/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext-all.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext_action.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext_extend.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext_grid.js"></script>
	<script type="text/javascript" src="<%=context%>/view/ext/yzsystem/exhibitions/hostunit/hostunitlist.js"></script>
	<script type="text/javascript" src="<%=context%>/view/ext/yzsystem/exhibitions/hostunit/hostunitEdit.js"></script>
</head>
<script type="text/javascript">
var context = "<%=context%>";
var submmitUrl="/yzsystem/exhibitions/hostunit.do?method=insetHostunit&rsCode=HOSMGR_PAGE_INSERT";
</script>
<body>
<div id="center"></div>
</body>
</html>