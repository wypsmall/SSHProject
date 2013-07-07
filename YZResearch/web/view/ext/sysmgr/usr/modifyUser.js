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
		Ext.Msg.alert('信息', '请选择你要修改的数据！');
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
		},{	xtype : 'textfield',fieldLabel : "账号(必填)",name : "loginId",width : 100,readOnly : true
		},{xtype : 'textfield',fieldLabel : "昵称(必填)",name : "userNick",width : 100,	maxLength:32,allowBlank : false
		},{xtype : 'textfield',fieldLabel : "姓名",name : "userName",width : 100,maxLength:32,readOnly : false
		}],
		buttonAlign : 'center',
		buttons : [{
			text : '保存',
			icon : context + '/images/icons/save.gif',
			handler : function() {
				modifyModuleSave();
			}
		}, {
			text : '返回',
			icon : context + '/images/icons/rollback.png',
			handler : function() {
				modifyUserWin.close();
			}
		}]
	});
	var root2 = new Ext.tree.AsyncTreeNode({
		id : "root",
		text : "角色列表",
		leaf : false
	});
	var modifyUserWin = new Ext.Window({
		width : 500,
		height : 420,
		id : 'modifyUserWin',
		title : '修改用户信息',
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
			title : '选择角色',
			containerScroll : true,
			loader : new Ext.tree.TreeLoader({
				dataUrl : context + '/sysmgr/sysmgr.do?method=getRoleListTree&rsCode=IMTI_SYS_USR_UPDATE'
			}),
			root : root2
		}]
	});
	modifyUserWin.show();
}
// ----保存修改模块---//
function modifyModuleSave() {
	var form = Ext.getCmp("modifyUserWin").getComponent("modifyUserForm").getForm();
	var loginId = form.findField("loginId").getValue();
	var userNick = form.findField("userNick").getValue();
	var userName = form.findField("userName").getValue();
	if(loginId == null || loginId==""){
		Ext.Msg.alert('信息', '账号不能为空！');
		return;
	}
	if(userName == null || userName==""){
		Ext.Msg.alert('信息', '用户姓名不能为空！');
		return;
	}
	var optypestr = '';
	var selNodes = Ext.getCmp("updateUserTree").getChecked();
	Ext.each(selNodes, function(node) {
		if(node.leaf){//如果公司的数据库没有处理根节点的权限信息，则在这里隔离
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
		waitMsg : "请稍侯...",
		method : 'POST',
		url :context + '/sysmgr/sysmgr.do?method=saveUser&rsCode=IMTI_SYS_USR_UPDATE',
		success : function(form, action) {
			Ext.Msg.alert("信息","保存成功");
			Ext.getCmp("userGrid").getStore().reload();
			Ext.getCmp("modifyUserWin").close();
		},
		failure : function(form, action) {
			Ext.Msg.alert("信息",action.result.data);
		}
	});	
}