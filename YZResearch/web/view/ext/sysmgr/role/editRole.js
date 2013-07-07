function openEditWin(){
	
	var editWin = new Ext.Window({
		width : 700,
		height : 450,
		id : 'editWin',
		buttonAlign : 'center',
		title : '�޸Ľ�ɫ��Ϣ',
		plain : true,
		closable : true,
		resizable : true,
		frame : true,
		layout : 'border',
		border : false,
		modal : true,
		items : [new Ext.form.FormPanel({
			labelAlign : 'left',
			buttonAlign : 'center',
			frame : true,
			height : 160,
			region : 'center',
			id : 'editForm',
			labelWidth :60,
			items : [{
				xtype : 'hidden',
				anchor: '100%',
                name: "id"
			},{
				xtype : 'textfield',
				fieldLabel : '��ɫ����',
				anchor: '100%',
				maxLength:300,
				allowBlank : true,
                name: "roleName"
			},{
				xtype : 'textfield',
				fieldLabel : '��ɫ����',
				anchor: '100%',
				maxLength:300,
				allowBlank : true,
                name: "roleCode"
			},{
				xtype : 'textarea',
				fieldLabel : '��ע',
				anchor: '100%',
				maxLength:300,
				width:200,
				height:200,
                name: "roleMemo"
			}]
		}),new Ext.tree.TreePanel({
			region : 'east',
			xtype : 'treepanel',
			width : 300,
			id : 'editTree',
			collapsible : true,
			split : true,
			useArrows : true,
			tbar : [openAction, closeAction],
			autoScroll : true,
			animate : true,
			title : 'Ȩ������',
			containerScroll : true,
			loader : new Ext.tree.TreeLoader({
				clearOnLoad :true,
				dataUrl : context + '/sysmgr/resources.do?method=getRsOpeTreeNew&rsCode=IMTI_SYS_ROLE_UPDATE'
			}),
			root : new Ext.tree.AsyncTreeNode({
						id : "root",
						icon : context + '/images/icons/icon2_063.png',
						text : "��Դ�б�",
						leaf : false
					})
		})],
		buttons:[{
			text : '����',
			id:'btn_add',
			icon : context + '/images/icons/save.gif',
			handler : function() {
				saveEditRole();
			}
		}, {
			text : '����',
			icon : context + '/images/icons/rollback.png',
			handler : function() {
				editWin.close();
			}
		}]
	});
	Ext.getCmp("editTree").getLoader().on("beforeload", function() {
	   	this.baseParams = {
	   		roleId : role_id
	   	};
   });	
   Ext.getCmp("editTree").on('checkchange', function(node, checked) {
		node.expand();
		node.attributes.checked = checked;
		node.eachChild( function(child) {
			child.ui.toggleCheck(checked);
			child.attributes.checked = checked;
			child.fireEvent('checkchange', child, checked);
		});
	}, Ext.getCmp("editTree"));
	
	editWin.show();
	openAll();
/****************************���涯��*********************************************/	
	function saveEditRole(){
		var form = Ext.getCmp("editWin").getComponent("editForm").getForm();
		var roleName = form.findField("roleName").getValue();
		var roleCode = form.findField("roleCode").getValue();
		if(roleName == ""){
			Ext.Msg.alert("��ʾ","��ɫ���Ʋ���Ϊ��!");
			return ;
		}
		if(roleCode == ""){
			Ext.Msg.alert("��ʾ","��ɫ���벻��Ϊ��");
			return ;
		}
		
		//����Ȩ�ޣ�Լ����Ҷ�ӽڵ�ID������ϡ�_1�� Ҷ�ӽڵ�ID������ϡ�_0��
		var rightRsIds = '';
		var selNodes = Ext.getCmp("editTree").getChecked();
		
		Ext.each(selNodes, function(node) {
			if (rightRsIds.length > 0) {
				rightRsIds += ',';
			}
			rightRsIds += node.id;
		});
		form.baseParams = {
			roleName : roleName,
			roleCode : roleCode,
			roleMemo : form.findField("roleMemo").getValue(),
			rightRsIds : rightRsIds
		}	;
		Ext.getCmp("editForm").getForm().submit({
			waitMsg : "���Ժ�...",
			method : 'POST',
			url :context + '/sysmgr/sysmgr.do?method=saveRole&rsCode=IMTI_SYS_ROLE_UPDATE',
			success : function(form, action) {
				editWin.close();
				grid.getStore().reload();
				Ext.Msg.alert("��Ϣ","����ɹ�");
			},
			failure : function(form, action) {
				Ext.Msg.alert("��Ϣ",action.result.msg);
			}
		});	
	}
}
// -----beginģ�����б���--------//
var openAction = new Ext.Action({
	text : '��ȫ��չ����',
	handler : function() {
		openAll();
	}
});

var closeAction = new Ext.Action({
	text : '��ȫ����£��',
	handler : function() {
		closeAll();
	}
});
// ----�����б�--//
function openAll() {
	Ext.getCmp("editTree").expandAll();
}
// ----�ر����б�--//
function closeAll() {
	Ext.getCmp("editTree").collapseAll();
}	