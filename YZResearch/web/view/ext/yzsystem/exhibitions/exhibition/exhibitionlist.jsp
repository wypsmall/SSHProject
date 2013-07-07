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
	<script type="text/javascript" src="<%=context%>/view/ext/yzsystem/exhibitions/exhibition/exhibitionlist.js"></script>
	<script type="text/javascript" src="<%=context%>/view/ext/yzsystem/exhibitions/exhibition/exhprotrans.js"></script>
	<script type="text/javascript" src="<%=context%>/view/ext/yzsystem/exhibitions/exhibition/exhproadvs.js"></script>
	<style type="text/css">
        .x-grid3-cell-inner {
            font-family:"segoe ui",tahoma, arial, sans-serif;
        }
        .x-grid-group-hd div {
            font-family:"segoe ui",tahoma, arial, sans-serif;
        }
        .x-grid3-hd-inner {
            font-family:"segoe ui",tahoma, arial, sans-serif;
            font-size:12px;
        }
        .x-grid3-body .x-grid3-td-cost {
            background-color:#f1f2f4;
        }
        .x-grid3-summary-row .x-grid3-td-cost {
            background-color:#e1e2e4;
        }
        .x-grid3-dirty-cell {
            background-image:none;
        }
    </style>
</head>
<script type="text/javascript">
var context = "<%=context%>";
</script>
<body>
<div id="center"></div>
</body>
</html>