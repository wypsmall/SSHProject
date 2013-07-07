function openEditWin(){
	
	var editWin = new Ext.Window({
		width : 700,
		height : 450,
		id : 'editWin',
		buttonAlign : 'center',
		title : '修改角色信息',
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
			title : '权限配置',
			containerScroll : true,
			loader : new Ext.tree.TreeLoader({
				clearOnLoad :true,
				dataUrl : context + '/sysmgr/resources.do?method=getRsOpeTreeNew&rsCode=IMTI_SYS_ROLE_UPDATE'
			}),
			root : new Ext.tree.AsyncTreeNode({
						id : "root",
						icon : context + '/images/icons/icon2_063.png',
						text : "资源列表",
						leaf : false
					})
		})],
		buttons:[{
			text : '保存',
			id:'btn_add',
			icon : context + '/images/icons/save.gif',
			handler : function() {
				saveEditRole();
			}
		}, {
			text : '返回',
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
/****************************保存动作*********************************************/	
	function saveEditRole(){
		var form = Ext.getCmp("editWin").getComponent("editForm").getForm();
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
			waitMsg : "请稍侯...",
			method : 'POST',
			url :context + '/sysmgr/sysmgr.do?method=saveRole&rsCode=IMTI_SYS_ROLE_UPDATE',
			success : function(form, action) {
				editWin.close();
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
var openAction = new Ext.Action({
	text : '【全部展开】',
	handler : function() {
		openAll();
	}
});

var closeAction = new Ext.Action({
	text : '【全部收拢】',
	handler : function() {
		closeAll();
	}
});
// ----打开树列表--//
function openAll() {
	Ext.getCmp("editTree").expandAll();
}
// ----关闭树列表--//
function closeAll() {
	Ext.getCmp("editTree").collapseAll();
}	