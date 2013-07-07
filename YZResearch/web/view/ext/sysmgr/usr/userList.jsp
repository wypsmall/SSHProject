<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.imti.framework.component.vopomapping.utils.PropertyUtils"%>
<html>
<head>
	<%
		String context = request.getContextPath();
		String imtiSysName=PropertyUtils.getProperty("imti.sys.name","����ƽ̨");
	%>
	<link rel="stylesheet" type="text/css" href="<%=context%>/ext3.0/resources/css/ext-all.css" />
	<script type="text/javascript" src="<%=context%>/ext3.0/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext-all.js"></script>
	<script language="javascript" src="<%=context%>/ext3.0/ext_extend.js"></script>
	<script type="text/javascript" src="<%=context%>/view/ext/sysmgr/usr/addUser.js"></script>
	<script type="text/javascript" src="<%=context%>/view/ext/sysmgr/usr/modifyUser.js"></script>
<title>�û��б�</title>
</head>
<body>
<script type="text/javascript">
var context = "<%=context%>";
Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = "<%=context%>/images/s.gif";
	Ext.QuickTips.init();
	//��ѯ
	var actionSearch= new Ext.Action({
		icon : '<%=context%>/images/icons/zoom.png',
		cls : 'x-btn-text-icon',
		text : '����ѯ��',
		handler : function() {
			  q_store.reload(); 
		},
		listeners:{"render" : function(e){checkRole(e,"IMTI_SYS_USR_VIEW");}}
	});
	//����
	var addAction = new Ext.Action({
		icon : '<%=context%>/ext3.0/resources/images/default/dd/drop-add.gif',
		cls : 'x-btn-text-icon',
		text : '��������',
		handler : function() {
			addUser();
			Ext.getCmp("addUserTree").expandAll();
		},
		listeners:{"render" : function(e){checkRole(e,"IMTI_SYS_USR_INSERT");}}
	});
	//�޸�
	var modifyAction = new Ext.Action({
		icon : '<%=context%>/images/icons/ico03.png',
		cls : 'x-btn-text-icon',
		text : '���޸ġ�',
		handler : function() {
			modifyUser();
		},
		listeners:{"render" : function(e){checkRole(e,"IMTI_SYS_USR_UPDATE");}}
	});
	
	//�˺���ͣ
	var stopAction = new Ext.Action({
		icon : '<%=context%>/images/icons/suspension.png',
		cls : 'x-btn-text-icon',
		text : '��ͣ�á�',
		handler : function() {
			var record=Ext.getCmp("userGrid").getSelectionModel().getSelected();
			if (record) {
				Ext.Msg.confirm('��Ϣ', '��ȷ��Ҫͣ�ø��˺���', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : context + "/sysmgr/sysmgr.do?method=suspensionUser&rsCode=IMTI_SYS_USR_SUSPEND&userIds=" + record.get("id"),
							success : function(form, action) {
								Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;��ͣ�˺ųɹ���&nbsp;");
								q_store.reload();
							},
							failure : function(response, options) {
								Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;��ͣ�˺�ʧ�ܣ�&nbsp;");
							}
						});
					}
				});
			} else {
				Ext.Msg.alert('��Ϣ', '��ѡ����Ҫͣ�õ��˺ţ�');
			}
		},
		listeners:{"render" : function(e){checkRole(e,"IMTI_SYS_USR_SUSPEND");}}
	});
	
	//�˺ż���
	var activeAction = new Ext.Action({
		icon : '<%=context%>/images/icons/activation.png',
		cls : 'x-btn-text-icon',
		text : '�����á�',
		handler : function() {
			var record=Ext.getCmp("userGrid").getSelectionModel().getSelected();
			if (record) {
				Ext.Msg.confirm('��Ϣ', '��ȷ��Ҫ���ø��˺���', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : context + "/sysmgr/sysmgr.do?method=activationUser&rsCode=IMTI_SYS_USR_ACTIVETY&userIds=" + record.get("id"),
							success : function(form, action) {
								Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;�����˺ųɹ���&nbsp;");
								q_store.reload();
							},
							failure : function(response, options) {
								Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;�����˺�ʧ�ܣ�&nbsp;");
							}
						});
					}
				});
			} else {
				Ext.Msg.alert('��Ϣ', '��ѡ����Ҫ���õ��˺ţ�');
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
	// �û��б�
	colM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),q_sm, {
				header : "id",hidden : true,dataIndex : "id"
			},{header : "loginType",hidden : true,dataIndex : "loginType"
			}, {header : "�˺�",sortable : true,dataIndex : "loginId"
			}, {header : "�û�����",dataIndex : "userName"
			}, {header : "�û��ǳ�",dataIndex : "userNick"
			}, {
				header : "��ɫ",
				dataIndex : "roleName",
				hidden : true,
				sortable : true,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					if(value == ""){
						return '<a href="#" onclick="modifyUser()">���ý�ɫ</a>' + '&nbsp;&nbsp;&nbsp;&nbsp;'
					}
					return value;
				}
			}, {
				header : "�˺�״̬",
				dataIndex : "valid",
				rowspan : 2,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					if(value=="1"){
						return ' <font color="green">����</font>';
					}
					return '<font color="red">ͣ��</font>';
				}
			}, {
				header : "<center>���뻹ԭ</center>",
				dataIndex : "id",
				rowspan : 2,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					return '<center><a href="#" onClick="backPassword(\''+value+'\')">��ԭ</a></center>';
				}
			}
	]);
	q_grid = new Ext.grid.EditorGridPanel({
		id : 'userGrid',
		region : 'center',
		title : "<center>�û��б�</center>",
		fram : true,
		cm : colM,
		height:document.documentElement.clientHeight,
		width : 400,
		tbar : ['�ʺţ�',{xtype : 'textfield',width : 80,id : 'loginId'},'������',{xtype : 'textfield',width : 80,id : 'userName'},'->',actionSearch, addAction,  modifyAction, stopAction, activeAction],
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
	//����ҳ�沼��
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
			Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;���뻹ԭ�ɹ���&nbsp;");
			q_store.reload();
		},
		failure : function(response, options) {
			Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;ʧ�ܣ�&nbsp;");
		}
	});
}
</script>
<div id="center1"></div>
<div id="east"></div>
</body>
</html>