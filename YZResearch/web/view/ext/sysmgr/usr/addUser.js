// **********************begin�����û�*************//
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
				fieldLabel : "�˺�(����)",
				name : "loginId",
				width : 150,
				maxLength:32,
				allowBlank : false
			}, {
				xtype : 'textfield',
				fieldLabel : "����(����)",
				name : "password",
				inputType: "password",
				width : 150,
				maxLength:32,
				value:'123456',
				allowBlank : false
			}, {
				xtype : 'textfield',
				fieldLabel : "�ǳ�(����)",
				name : "userNick",
				width : 150,
				maxLength:32
			}, {
				xtype : 'textfield',
				fieldLabel : "����",
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
		text : "��ɫ�б�",
		leaf : false
	});
	var insertUserWin = new Ext.Window({
		width : 500,
		height : 420,
		id : 'insertUserWin',
		title : '�����û�',
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
			title : 'ѡ���ɫ',
			containerScroll : true,
			loader : new Ext.tree.TreeLoader({
				dataUrl : context + '/sysmgr/sysmgr.do?method=getRoleListTree&rsCode=IMTI_SYS_USR_INSERT'
			}),
			root : root3
		}],
		buttonAlign : 'center',
		buttons : [{
			text : '����',
			icon : context + '/images/icons/save.gif',
			handler : function() {
				save();
			}
		}, {
			text : '����',
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
		Ext.Msg.alert('��Ϣ', '�˺Ų���Ϊ�գ�');
		return;
	}
	if(password == null || password==""){
		Ext.Msg.alert('��Ϣ', '���벻��Ϊ�գ�');
		return;
	}
	var optypestr = '';
	var selNodes = Ext.getCmp("addUserTree").getChecked();
	Ext.each(selNodes, function(node) {
		if(node.leaf){//�����˾�����ݿ�û�д�����ڵ��Ȩ����Ϣ�������������
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
		waitMsg : "���Ժ�...",
		method : 'POST',
		url :context + '/sysmgr/sysmgr.do?method=saveUser&rsCode=IMTI_SYS_USR_INSERT',
		success : function(form, action) {
			Ext.Msg.alert("��Ϣ","����ɹ�");
			Ext.getCmp("userGrid").getStore().reload();
			Ext.getCmp("insertUserWin").close();
		},
		failure : function(form, action) {
			Ext.Msg.alert("��Ϣ",action.result.data);
		}
	});	
}