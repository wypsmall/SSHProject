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
				fieldLabel : '角色名称',
				anchor: '100%',
				maxLength:300,
				allowBlank : true,
                name: "roleName"
			},{
				xtype : 'textfield',
				fieldLabel : '角色编码',
				anchor: '100%',
				maxLength:300,
				allowBlank : true,
                name: "roleCode"
			},{
				xtype : 'textarea',
				fieldLabel : '备注',
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
			title : '新增角色信息',
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
				title : '授权',
				containerScroll : true,
				loader : new Ext.tree.TreeLoader({
					clearOnLoad :true,
					dataUrl : context + '/sysmgr/resources.do?method=getRsOpeTreeNew&rsCode=IMTI_SYS_ROLE_INSERT'
				}),
				root : new Ext.tree.AsyncTreeNode({
						icon : context + '/images/icons/icon2_063.png',
						text : "资源列表",
						id : "root",
						leaf : false
					})
			})],
			buttons:[{
				text : '保存',
				id:'btn_add',
				icon : context + '/images/icons/save.gif',
				handler : function() {
					saveAddRole();
				}
			}, {
				text : '重置',
				icon : context + '/images/icons/refresh.png',
				handler : function() {
					Ext.getCmp("myForm").getForm().reset();
				}
			}, {
				text : '返回',
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
/*********************************保存动作*******************************************************/	
	function saveAddRole(){
		var form = Ext.getCmp("addWin").getComponent("myForm").getForm();
		var roleName = form.findField("roleName").getValue();
		var roleCode = form.findField("roleCode").getValue();
		if(roleName == ""){
			Ext.Msg.alert("提示","角色名称不能为空!");
			return ;
		}
		
		if(roleCode == ""){
			Ext.Msg.alert("提示","角色编码不能为空");
			return ;
		}
		
		//处理权限：约定非叶子节点ID后面加上“_1” 叶子节点ID后面加上“_0”
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
			waitMsg : "请稍侯...",
			method : 'POST',
			url :context + '/sysmgr/sysmgr.do?method=saveRole&rsCode=IMTI_SYS_ROLE_INSERT',
			success : function(form, action) {
				addWin.close();
				grid.getStore().reload();
				Ext.Msg.alert("信息","保存成功");
				
			},
			failure : function(form, action) {
				Ext.Msg.alert("信息",action.result.msg);
			}
		});	
	}	
}

// -----begin模块树列表部分--------//
var openAddAction = new Ext.Action({
	text : '【全部展开】',
	handler : function() {
		openAddAll();
	}
});

var closeAddAction = new Ext.Action({
	text : '【全部收拢】',
	handler : function() {
		closeAddAll();
	}
});

// ----打开树列表--//
function openAddAll() {
	Ext.getCmp("addTree").expandAll();
}
// ----关闭树列表--//
function closeAddAll() {
	Ext.getCmp("addTree").collapseAll();
}