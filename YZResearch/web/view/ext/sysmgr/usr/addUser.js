// **********************begin新增用户*************//
function addUser() {
	var q_formPanel = new Ext.FormPanel({
		labelAlign : 'left',
		buttonAlign : 'center',
		bodyStyle : 'padding:5px',
		frame : true,
		id : 'insertUserForm',
		labelWidth : 80,
		region : 'center',
		width: 200,
		items : [{
				xtype : 'textfield',
				fieldLabel : "账号(必填)",
				name : "loginId",
				width : 150,
				maxLength:32,
				allowBlank : false
			}, {
				xtype : 'textfield',
				fieldLabel : "密码(必填)",
				name : "password",
				inputType: "password",
				width : 150,
				maxLength:32,
				value:'123456',
				allowBlank : false
			}, {
				xtype : 'textfield',
				fieldLabel : "昵称(必填)",
				name : "userNick",
				width : 150,
				maxLength:32
			}, {
				xtype : 'textfield',
				fieldLabel : "姓名",
				name : "userName",
				width : 150,
				maxLength:32,
				allowBlank : false
			},{
				xtype : 'hidden',
				anchor: '100%',
                name: "loginType",
                value : '3'
			}]
		});
	var root3 = new Ext.tree.AsyncTreeNode({
		id : "root",
		text : "角色列表",
		leaf : false
	});
	var insertUserWin = new Ext.Window({
		width : 500,
		height : 420,
		id : 'insertUserWin',
		title : '新增用户',
		plain : true,
		closable : true,
		resizable : false,
		frame : true,
		layout : 'border',
		border : false,
		modal : true,
		items : [q_formPanel,{
			region : 'east',
			xtype : 'treepanel',
			width: 200,
			id : 'addUserTree',
			collapsible : true,
			split : true,
			useArrows : true,
			autoScroll : true,
			animate : true,
			title : '选择角色',
			containerScroll : true,
			loader : new Ext.tree.TreeLoader({
				dataUrl : context + '/sysmgr/sysmgr.do?method=getRoleListTree&rsCode=IMTI_SYS_USR_INSERT'
			}),
			root : root3
		}],
		buttonAlign : 'center',
		buttons : [{
			text : '保存',
			icon : context + '/images/icons/save.gif',
			handler : function() {
				save();
			}
		}, {
			text : '返回',
			icon : context + '/images/icons/rollback.png',
			handler : function() {
				insertUserWin.close();
			}
		}]
	});
	insertUserWin.show();
}
function save() {
	var form = Ext.getCmp("insertUserWin").getComponent("insertUserForm").getForm();
	var loginId = form.findField("loginId").getValue();
	var password = form.findField("password").getValue();
	if(loginId == null || loginId==""){
		Ext.Msg.alert('信息', '账号不能为空！');
		return;
	}
	if(password == null || password==""){
		Ext.Msg.alert('信息', '密码不能为空！');
		return;
	}
	var optypestr = '';
	var selNodes = Ext.getCmp("addUserTree").getChecked();
	Ext.each(selNodes, function(node) {
		if(node.leaf){//如果公司的数据库没有处理根节点的权限信息，则在这里隔离
			if (optypestr.length > 0) {
				optypestr += ',';
			}
			optypestr += node.id;
		 }
	});
	form.baseParams = {
		roleId:optypestr,
		loginId : loginId,
		userNick : form.findField("userNick").getValue(),
		userName : form.findField("userName").getValue()
	}
	form.submit({
		waitMsg : "请稍侯...",
		method : 'POST',
		url :context + '/sysmgr/sysmgr.do?method=saveUser&rsCode=IMTI_SYS_USR_INSERT',
		success : function(form, action) {
			Ext.Msg.alert("信息","保存成功");
			Ext.getCmp("userGrid").getStore().reload();
			Ext.getCmp("insertUserWin").close();
		},
		failure : function(form, action) {
			Ext.Msg.alert("信息",action.result.data);
		}
	});	
}