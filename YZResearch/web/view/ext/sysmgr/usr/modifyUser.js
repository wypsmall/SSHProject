function modifyUser(){
	var record=Ext.getCmp("userGrid").getSelectionModel().getSelected();
	if (record) {
		openEditUserWin();
		Ext.getCmp("modifyUserForm").getForm().loadRecord(record);
		Ext.getCmp("updateUserTree").getLoader().on("beforeload", function() {
	    	this.baseParams = {
	    		userId : record.get("id")
	    	};
	    }); 
	    Ext.getCmp("updateUserTree").expandAll();
	} else {
		Ext.Msg.alert('��Ϣ', '��ѡ����Ҫ�޸ĵ����ݣ�');
	}
}
function openEditUserWin() {
	var modifyUser_formPanel = new Ext.FormPanel({
		labelAlign : 'left',
		buttonAlign : 'center',
		bodyStyle : 'padding:5px',
		frame : true,
		id : 'modifyUserForm',
		labelWidth : 80,
		region : 'center',
		width: 200,
		items : [{
			xtype : 'hidden',name: "id"
		},{ xtype : 'hidden',name: "companyCode"
		},{ xtype : 'hidden',name: "ztId"
		},{ xtype : 'hidden',name: "orgParentName"
		},{ xtype : 'hidden',name: "finCode"
		},{ xtype : 'hidden',name: "finCode"
		},{ xtype : 'hidden',name: "password"
		},{	xtype : 'textfield',fieldLabel : "�˺�(����)",name : "loginId",width : 100,readOnly : true
		},{xtype : 'textfield',fieldLabel : "�ǳ�(����)",name : "userNick",width : 100,	maxLength:32,allowBlank : false
		},{xtype : 'textfield',fieldLabel : "����",name : "userName",width : 100,maxLength:32,readOnly : false
		}],
		buttonAlign : 'center',
		buttons : [{
			text : '����',
			icon : context + '/images/icons/save.gif',
			handler : function() {
				modifyModuleSave();
			}
		}, {
			text : '����',
			icon : context + '/images/icons/rollback.png',
			handler : function() {
				modifyUserWin.close();
			}
		}]
	});
	var root2 = new Ext.tree.AsyncTreeNode({
		id : "root",
		text : "��ɫ�б�",
		leaf : false
	});
	var modifyUserWin = new Ext.Window({
		width : 500,
		height : 420,
		id : 'modifyUserWin',
		title : '�޸��û���Ϣ',
		plain : true,
		closable : true,
		resizable : false,
		frame : true,
		layout : 'border',
		border : false,
		modal : true,
		items : [modifyUser_formPanel,{
			region : 'east',
			xtype : 'treepanel',
			width : 200,
			id : 'updateUserTree',
			collapsible : true,
			split : true,
			useArrows : true,
			autoScroll : true,
			animate : true,
			title : 'ѡ���ɫ',
			containerScroll : true,
			loader : new Ext.tree.TreeLoader({
				dataUrl : context + '/sysmgr/sysmgr.do?method=getRoleListTree&rsCode=IMTI_SYS_USR_UPDATE'
			}),
			root : root2
		}]
	});
	modifyUserWin.show();
}
// ----�����޸�ģ��---//
function modifyModuleSave() {
	var form = Ext.getCmp("modifyUserWin").getComponent("modifyUserForm").getForm();
	var loginId = form.findField("loginId").getValue();
	var userNick = form.findField("userNick").getValue();
	var userName = form.findField("userName").getValue();
	if(loginId == null || loginId==""){
		Ext.Msg.alert('��Ϣ', '�˺Ų���Ϊ�գ�');
		return;
	}
	if(userName == null || userName==""){
		Ext.Msg.alert('��Ϣ', '�û���������Ϊ�գ�');
		return;
	}
	var optypestr = '';
	var selNodes = Ext.getCmp("updateUserTree").getChecked();
	Ext.each(selNodes, function(node) {
		if(node.leaf){//�����˾�����ݿ�û�д�����ڵ��Ȩ����Ϣ�������������
			if (optypestr.length > 0) {
				optypestr += ',';
			}
			optypestr += node.id;
		 }
	});
	form.baseParams = {
		roleId : optypestr,
		loginId : loginId,
		userNick : userNick,
		userName : userName
	}
	
	form.submit({
		waitMsg : "���Ժ�...",
		method : 'POST',
		url :context + '/sysmgr/sysmgr.do?method=saveUser&rsCode=IMTI_SYS_USR_UPDATE',
		success : function(form, action) {
			Ext.Msg.alert("��Ϣ","����ɹ�");
			Ext.getCmp("userGrid").getStore().reload();
			Ext.getCmp("modifyUserWin").close();
		},
		failure : function(form, action) {
			Ext.Msg.alert("��Ϣ",action.result.data);
		}
	});	
}