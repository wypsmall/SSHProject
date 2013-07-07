<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.imti.framework.component.vopomapping.utils.PropertyUtils"%>
<html>
<head>
	<%
		String context = request.getContextPath();
	%>
	<link rel="stylesheet" type="text/css" href="<%=context%>/ext3.0/resources/css/ext-all.css" />
	<script type="text/javascript" src="<%=context%>/ext3.0/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext-all.js"></script>
<title>�û��б�</title>
</head>
<body>
<script type="text/javascript">
var context = "<%=context%>";
var q_viewPort = null;//ҳ�沼��
var q_treePanel = null;//��Դ��
var q_OrgId = null;//��ѡ�е���ԴID
var q_orgName = null;//��ѡ�е���Դ����
var q_store = null;//��Դ����
var q_grid = null;//��ԴGrid
var q_formPanel = null;//����ģ��
var q_sm = null;
var addWin = null;
var modifyUser_formPanel = null;
Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = "<%=context%>/images/blank.gif";
	Ext.QuickTips.init();
	//����
	var addAction = new Ext.Action({
		icon : '<%=context%>/ext3.0/resources/images/default/dd/drop-add.gif',
		cls : 'x-btn-text-icon',
		text : '�������û���',
		handler : function() {
			//������
			var iHeight = 200;
			var iWidth  = 500;
			var iTop = (window.screen.availHeight-30-iHeight)/2; 
			var iLeft = (window.screen.availWidth-10-iWidth)/2;
			var  url="customerUserAdd.jsp?rsCode=JP_CUS_USER_ADD&type=add";
			var styleStr = "height="+iHeight+",width="+iWidth+",top="+iTop+",left="+iLeft+","+"toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no";
			var OpenWindow =window.open(url,"�����û�",styleStr);
			try{
		  		OpenWindow.focus();
			}catch(e){}
		},
		listeners:{
            "render" : function(e){
            	//ͨ��ajax�����ж��Ƿ��д˲���Ȩ��permissionVerify
            	Ext.Ajax.request({
					url :context + '/sysmgr/resources.do?method=permissionVerify&rsCode=JP_CUS_USER_ADD',
					success : function(response, options) {
		            	var responseObject = Ext.util.JSON.decode(response.responseText);
						if(responseObject.data){
							e.show();
						}else {
							e.hide();
						}
					},
					failure : function(response, options) {
						//û��ͨ��Ȩ����֤ʱ�����ذ�ť
						e.hide();
					}
				});	
				
            }
        }
	});
	//ɾ��
	var delAction = new Ext.Action({
		icon : '<%=context%>/images/icons/delete.png',
		cls : 'x-btn-text-icon',
		text : '��ɾ���û���',
		handler : function() {
			var array = q_sm.getSelections();
			if (array.length != 0) {
				Ext.Msg.confirm('��Ϣ', '��ȷ��Ҫɾ����', function(btn) {
					if (btn == 'yes') {
						deleteUser();
					}
				});
			} else {
				Ext.Msg.alert('��Ϣ', '��ѡ����Ҫɾ�����У�');
			}
		},
		listeners:{
            "render" : function(e){
            	//ͨ��ajax�����ж��Ƿ��д˲���Ȩ��permissionVerify
            	Ext.Ajax.request({
					url :context + '/sysmgr/resources.do?method=permissionVerify&rsCode=JP_CUS_USER_DEL',
					success : function(response, options) {
		            	var responseObject = Ext.util.JSON.decode(response.responseText);
						if(responseObject.data){
							e.show();
						}else {
							e.hide();
						}
					},
					failure : function(response, options) {
						//û��ͨ��Ȩ����֤ʱ�����ذ�ť
						e.hide();
					}
				});	
            }
        }
	});
	
	//�˺���ͣ
	var stopAction = new Ext.Action({
		icon : '<%=context%>/images/icons/suspension.png',
		cls : 'x-btn-text-icon',
		text : '���˺���ͣ��',
		handler : function() {
			var array = q_sm.getSelections();
			if (array.length != 0) {
				Ext.Msg.confirm('��Ϣ', '��ȷ��Ҫͣ�ø��˺���', function(btn) {
					if (btn == 'yes') {
						suspensionUser();
					}
				});
			} else {
				Ext.Msg.alert('��Ϣ', '��ѡ����Ҫͣ�õ��˺ţ�');
			}
		},
		listeners:{
            "render" : function(e){
            	//ͨ��ajax�����ж��Ƿ��д˲���Ȩ��permissionVerify
            	Ext.Ajax.request({
					url :context + '/sysmgr/resources.do?method=permissionVerify&rsCode=JP_CUS_USER_STOP',
					success : function(response, options) {
		            	var responseObject = Ext.util.JSON.decode(response.responseText);
						if(responseObject.data){
							e.show();
						}else {
							e.hide();
						}
					},
					failure : function(response, options) {
						//û��ͨ��Ȩ����֤ʱ�����ذ�ť
						e.hide();
					}
				});	
            }
        }
	});
	
	//�˺ż���
	var activeAction = new Ext.Action({
		icon : '<%=context%>/images/icons/activation.png',
		cls : 'x-btn-text-icon',
		text : '���˺ż��',
		handler : function() {
			var array = q_sm.getSelections();
			if (array.length != 0) {
				Ext.Msg.confirm('��Ϣ', '��ȷ��Ҫ������˺���', function(btn) {
					if (btn == 'yes') {
						activationUser();
					}
				});
			} else {
				Ext.Msg.alert('��Ϣ', '��ѡ����Ҫ������˺ţ�');
			}
		},
		listeners:{
            "render" : function(e){
            	//ͨ��ajax�����ж��Ƿ��д˲���Ȩ��permissionVerify
            	Ext.Ajax.request({
					url :context + '/sysmgr/resources.do?method=permissionVerify&rsCode=JP_CUS_USER_ACT',
					success : function(response, options) {
		            	var responseObject = Ext.util.JSON.decode(response.responseText);
						if(responseObject.data){
							e.show();
						}else {
							e.hide();
						}
					},
					failure : function(response, options) {
						//û��ͨ��Ȩ����֤ʱ�����ذ�ť
						e.hide();
					}
				});	
            }
        }
	});
	 
	
	q_sm = new Ext.grid.CheckboxSelectionModel();
	q_store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : '<%=context%>/jplms/adapter/sysmgr.do?method=getUserListByLogId&rsCode=JP_CUS_USER',
			method : 'POST'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'rows',
			id : "id"
		}, [{
			name : 'id'
		}, {
			name : 'loginId'
		}, {
			name : 'userNick'
		}, {
			name : 'userName'
		}, {
			name : 'valid'
		}, {
			name : 'orgId'
		}, {
			name : 'loginType'
		}, {
			name : 'orgName'
		}]),
		autoLoad : true
	});
	// �û��б�
	colM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),q_sm, {
				header : "id",
				hidden : true,
				dataIndex : "id"
			},{
				header : "loginType",
				hidden : true,
				dataIndex : "loginType"
			}, {
				header : "�˺�",
				sortable : true,
				dataIndex : "loginId"
			}, {
				header : "�û��ǳ�",
				dataIndex : "userNick",
				sortable : true
			}, {
				header : "�û�����",
				dataIndex : "userName"
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
						return ' ����';
					}
					return '<font color="red">��ͣ</font>';
				}
			}
	]);
	
	//������ť
	var actionButton = new Ext.Action({
        text: '��������',
        icon : '<%=context%>/images/icons/zoom.png',
		cls : 'x-btn-text-icon',
        handler: function(){
			q_grid.getStore().load();
        }
    });

	q_grid = new Ext.grid.EditorGridPanel({
		id : 'gridId',
		region : 'center',
		title : "<center>�û��б�</center>",
		fram : true,
		cm : colM,
		height : 250,
		width : 400,
		tbar : ['��½�˺ţ�',{
					xtype : 'textfield',
					width : 80,
					id : 'loginId'
				},actionButton, addAction, 
				stopAction, activeAction],
		bbar:  new Ext.PagingToolbar({
			lastText : 'ǰһҳ',
			pageSize : 20,
			store : q_store,
			displayInfo : true,
			displayMsg : '��ʾ�� {0} ���� {1} ����¼���� {2} ��',
			emptyMsg : "<font color='red'>-----û�м�¼-----</font>",
			prevText : "��һҳ",
			nextText : "��һҳ",
			refreshText : "ˢ��",
			lastText : "���ҳ",
			firstText : "��һҳ",
			beforePageText : "��ǰҳ",
			afterPageText : "��{0}ҳ"
		}),
		store : q_store,
		sm : q_sm,
		viewConfig : {
			forceFit : 'true'
		},
		listeners:{
            "rowdblclick" : function(grid, rowIndex, e){
            	modifyUser();
            }
        }
	});
	
	q_grid.getStore().on("beforeload", function() {
    	this.baseParams = {
    		loginId : encodeURIComponent(Ext.getCmp("loginId").getValue())
    	};
    }); 
	//����ҳ�沼��
	q_viewPort = new Ext.Viewport({
		layout : 'border',
		items : [q_grid]
	});
	
	
});	
</script>
<div id="center"></div>
</body>
</html>