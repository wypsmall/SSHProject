<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.imti.framework.component.vopomapping.utils.PropertyUtils"%>
<html>
<head>
	<%
		String context = request.getContextPath();
		String imtiSysName=PropertyUtils.getProperty("imti.sys.name","管理平台");
	%>
	<link rel="stylesheet" type="text/css" href="<%=context%>/ext3.0/resources/css/ext-all.css" />
	<script type="text/javascript" src="<%=context%>/ext3.0/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext-all.js"></script>
	<script language="javascript" src="<%=context%>/ext3.0/ext_extend.js"></script>
	<script type="text/javascript" src="<%=context%>/view/ext/sysmgr/usr/addUser.js"></script>
	<script type="text/javascript" src="<%=context%>/view/ext/sysmgr/usr/modifyUser.js"></script>
<title>用户列表</title>
</head>
<body>
<script type="text/javascript">
var context = "<%=context%>";
Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = "<%=context%>/images/s.gif";
	Ext.QuickTips.init();
	//查询
	var actionSearch= new Ext.Action({
		icon : '<%=context%>/images/icons/zoom.png',
		cls : 'x-btn-text-icon',
		text : '【查询】',
		handler : function() {
			  q_store.reload(); 
		},
		listeners:{"render" : function(e){checkRole(e,"IMTI_SYS_USR_VIEW");}}
	});
	//新增
	var addAction = new Ext.Action({
		icon : '<%=context%>/ext3.0/resources/images/default/dd/drop-add.gif',
		cls : 'x-btn-text-icon',
		text : '【新增】',
		handler : function() {
			addUser();
			Ext.getCmp("addUserTree").expandAll();
		},
		listeners:{"render" : function(e){checkRole(e,"IMTI_SYS_USR_INSERT");}}
	});
	//修改
	var modifyAction = new Ext.Action({
		icon : '<%=context%>/images/icons/ico03.png',
		cls : 'x-btn-text-icon',
		text : '【修改】',
		handler : function() {
			modifyUser();
		},
		listeners:{"render" : function(e){checkRole(e,"IMTI_SYS_USR_UPDATE");}}
	});
	
	//账号暂停
	var stopAction = new Ext.Action({
		icon : '<%=context%>/images/icons/suspension.png',
		cls : 'x-btn-text-icon',
		text : '【停用】',
		handler : function() {
			var record=Ext.getCmp("userGrid").getSelectionModel().getSelected();
			if (record) {
				Ext.Msg.confirm('信息', '你确定要停用该账号吗？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : context + "/sysmgr/sysmgr.do?method=suspensionUser&rsCode=IMTI_SYS_USR_SUSPEND&userIds=" + record.get("id"),
							success : function(form, action) {
								Ext.Msg.alert("信息", "&nbsp;&nbsp;&nbsp;暂停账号成功！&nbsp;");
								q_store.reload();
							},
							failure : function(response, options) {
								Ext.Msg.alert("信息", "&nbsp;&nbsp;&nbsp;暂停账号失败！&nbsp;");
							}
						});
					}
				});
			} else {
				Ext.Msg.alert('信息', '请选择你要停用的账号！');
			}
		},
		listeners:{"render" : function(e){checkRole(e,"IMTI_SYS_USR_SUSPEND");}}
	});
	
	//账号激活
	var activeAction = new Ext.Action({
		icon : '<%=context%>/images/icons/activation.png',
		cls : 'x-btn-text-icon',
		text : '【启用】',
		handler : function() {
			var record=Ext.getCmp("userGrid").getSelectionModel().getSelected();
			if (record) {
				Ext.Msg.confirm('信息', '你确定要启用该账号吗？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : context + "/sysmgr/sysmgr.do?method=activationUser&rsCode=IMTI_SYS_USR_ACTIVETY&userIds=" + record.get("id"),
							success : function(form, action) {
								Ext.Msg.alert("信息", "&nbsp;&nbsp;&nbsp;启用账号成功！&nbsp;");
								q_store.reload();
							},
							failure : function(response, options) {
								Ext.Msg.alert("信息", "&nbsp;&nbsp;&nbsp;启用账号失败！&nbsp;");
							}
						});
					}
				});
			} else {
				Ext.Msg.alert('信息', '请选择你要启用的账号！');
			}
		},
		listeners:{"render" : function(e){checkRole(e,"IMTI_SYS_USR_ACTIVETY");}}
	});
	 
	
	var q_sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var q_store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : '<%=context%>/sysmgr/sysmgr.do?method=getUserListByOrgId&rsCode=IMTI_SYS_USR',
			method : 'POST'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'rows',
			id : "id"}, [{name : 'id'}, {name : 'loginId'}, {name : 'userNick'
		}, {name : 'userName'}, {name : 'valid'}, {name : 'orgId'}, {name : 'loginType'}, {name : 'orgName'}, {name : 'finCode'}, {name : 'ztId'
		}])
	});
	// 用户列表
	colM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),q_sm, {
				header : "id",hidden : true,dataIndex : "id"
			},{header : "loginType",hidden : true,dataIndex : "loginType"
			}, {header : "账号",sortable : true,dataIndex : "loginId"
			}, {header : "用户姓名",dataIndex : "userName"
			}, {header : "用户昵称",dataIndex : "userNick"
			}, {
				header : "角色",
				dataIndex : "roleName",
				hidden : true,
				sortable : true,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					if(value == ""){
						return '<a href="#" onclick="modifyUser()">配置角色</a>' + '&nbsp;&nbsp;&nbsp;&nbsp;'
					}
					return value;
				}
			}, {
				header : "账号状态",
				dataIndex : "valid",
				rowspan : 2,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					if(value=="1"){
						return ' <font color="green">启用</font>';
					}
					return '<font color="red">停用</font>';
				}
			}, {
				header : "<center>密码还原</center>",
				dataIndex : "id",
				rowspan : 2,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					return '<center><a href="#" onClick="backPassword(\''+value+'\')">还原</a></center>';
				}
			}
	]);
	q_grid = new Ext.grid.EditorGridPanel({
		id : 'userGrid',
		region : 'center',
		title : "<center>用户列表</center>",
		fram : true,
		cm : colM,
		height:document.documentElement.clientHeight,
		width : 400,
		tbar : ['帐号：',{xtype : 'textfield',width : 80,id : 'loginId'},'姓名：',{xtype : 'textfield',width : 80,id : 'userName'},'->',actionSearch, addAction,  modifyAction, stopAction, activeAction],
		store : q_store,
		sm : q_sm,
		viewConfig : {
			forceFit : 'true'
		},
		listeners:{"rowdblclick" : function(grid, rowIndex, e){modifyUser();}}
	});
	q_grid.getStore().on("beforeload", function() {
		var userName=Ext.getCmp('userName').getValue();
		var loginId=Ext.getCmp('loginId').getValue();
    	this.baseParams = {
    		orgId : '',
    		loginId : loginId,
    		userName: userName
    	};
    }); 
	//整个页面布局
	var q_viewPort = new Ext.Viewport({
		layout : 'border',
		items : [q_grid]
	});
});	

function reset() {
	var form = addWin.getComponent("myForm").getForm();
	form.reset();
}
function modifyModuleReset() {
	var form = modifyUserWin.getComponent("modifyUserForm").getForm();
	form.reset();
}
function backPassword(userId) {
	Ext.Ajax.request({
		url : '<%=context%>/login.do?method=backPassWord&rsCode=IMTI_SYS_USR&userId='+userId,
		success : function(form, action) {
			Ext.Msg.alert("信息", "&nbsp;&nbsp;&nbsp;密码还原成功！&nbsp;");
			q_store.reload();
		},
		failure : function(response, options) {
			Ext.Msg.alert("信息", "&nbsp;&nbsp;&nbsp;失败！&nbsp;");
		}
	});
}
</script>
<div id="center1"></div>
<div id="east"></div>
</body>
</html>