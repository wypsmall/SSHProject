function openAddWin(){
	var q_formPanel = new Ext.form.FormPanel({
			labelAlign : 'left',
			buttonAlign : 'center',
			frame : true,
			height : 160,
			region : 'center',
			id : 'myForm',
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
		});

		addWin = new Ext.Window({
			width : 700,
			height : 450,
			id : 'addWin',
			buttonAlign : 'center',
			title : '������ɫ��Ϣ',
			plain : true,
			closable : true,
			resizable : true,
			frame : true,
			layout : 'border',
			border : false,
			modal : true,
			items : [q_formPanel,new Ext.tree.TreePanel({
				region : 'east',
				xtype : 'treepanel',
				width : 300,
				id : 'addTree',
				collapsible : true,
				split : true,
				useArrows : true,
				autoScroll : true,
				tbar : [openAddAction, closeAddAction],
				animate : true,
				title : '��Ȩ',
				containerScroll : true,
				loader : new Ext.tree.TreeLoader({
					clearOnLoad :true,
					dataUrl : context + '/sysmgr/resources.do?method=getRsOpeTreeNew&rsCode=IMTI_SYS_ROLE_INSERT'
				}),
				root : new Ext.tree.AsyncTreeNode({
						icon : context + '/images/icons/icon2_063.png',
						text : "��Դ�б�",
						id : "root",
						leaf : false
					})
			})],
			buttons:[{
				text : '����',
				id:'btn_add',
				icon : context + '/images/icons/save.gif',
				handler : function() {
					saveAddRole();
				}
			}, {
				text : '����',
				icon : context + '/images/icons/refresh.png',
				handler : function() {
					Ext.getCmp("myForm").getForm().reset();
				}
			}, {
				text : '����',
				icon : context + '/images/icons/rollback.png',
				handler : function() {
					addWin.close();
				}
			}]
	});
	Ext.getCmp("addTree").on('checkchange', function(node, checked) {
		node.expand();
		node.attributes.checked = checked;
		node.eachChild( function(child) {
			child.ui.toggleCheck(checked);
			child.attributes.checked = checked;
			child.fireEvent('checkchange', child, checked);
		});
	}, Ext.getCmp("addTree"));
	addWin.show();
/*********************************���涯��*******************************************************/	
	function saveAddRole(){
		var form = Ext.getCmp("addWin").getComponent("myForm").getForm();
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
		var selNodes = Ext.getCmp("addTree").getChecked();
		Ext.each(selNodes, function(node) {
			if (rightRsIds.length > 0) {
				rightRsIds += ',';
			}
			rightRsIds += node.id ;
		});
		
		form.baseParams = {
			roleName : roleName,
			roleCode : roleCode,
			roleMemo : form.findField("roleMemo").getValue(),
			rightRsIds : rightRsIds
		}
		Ext.getCmp("myForm").getForm().submit({
			waitMsg : "���Ժ�...",
			method : 'POST',
			url :context + '/sysmgr/sysmgr.do?method=saveRole&rsCode=IMTI_SYS_ROLE_INSERT',
			success : function(form, action) {
				addWin.close();
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
var openAddAction = new Ext.Action({
	text : '��ȫ��չ����',
	handler : function() {
		openAddAll();
	}
});

var closeAddAction = new Ext.Action({
	text : '��ȫ����£��',
	handler : function() {
		closeAddAll();
	}
});

// ----�����б�--//
function openAddAll() {
	Ext.getCmp("addTree").expandAll();
}
// ----�ر����б�--//
function closeAddAll() {
	Ext.getCmp("addTree").collapseAll();
}