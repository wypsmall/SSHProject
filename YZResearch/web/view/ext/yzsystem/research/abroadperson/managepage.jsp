<%@ page contentType="text/html;charset=GBK"%>
<html>
	<head>
	<%
		String context = request.getContextPath();
	%>
	<link rel="stylesheet" type="text/css" href="<%=context%>/ext3.0/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="<%=context%>/ext3.0/resources/css/ext-extend.css" />
	<link rel="stylesheet" type="text/css" href="<%=context%>/ext3.0/ux/css/RowEditor.css" />
	<script type="text/javascript" src="<%=context%>/ext3.0/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext-all.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ux/RowEditor.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext_action.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext_extend.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext_grid.js"></script>
	<script type="text/javascript" src="<%=context%>/view/ext/yzsystem/research/abroadperson/personEdit.js"></script>
	<script type="text/javascript" src="<%=context%>/view/ext/yzsystem/research/abroadperson/managepage.js"></script>
</head>
<script type="text/javascript">
var context = "<%=context%>";
var submmitUrl= "/yzsystem/research/abroadperson.do?method=insertAP&rsCode=RCH_ABROAD_PERSON_PAGE_INSERT_PERSON";
</script>
<body>
<div id="center"></div>
<iframe width=0 height=0 name="expDown"></iframe>
</body>
</html>