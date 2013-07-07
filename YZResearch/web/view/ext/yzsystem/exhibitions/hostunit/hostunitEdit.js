function loadEditData(){
	var record=Ext.getCmp("hostunitGrid").getSelectionModel().getSelected();
	if (record) {
		submmitUrl="/yzsystem/exhibitions/hostunit.do?method=updateHostunit&rsCode=HOSMGR_PAGE_UPDATE";
		openEditWin();
		Ext.getCmp("hostunitEditForm").getForm().loadRecord(record);
	}
}
/*
 * 主办单位
 */
function openEditWin(){
	var hostunitEditWin = new Ext.Window({
		width : 520,
		height : 350,
		id : 'hostunitEditWin',
		iconCls : 'window-edit',
		buttonAlign : 'center',
		title : '主办单位资料维护',
		plain : true,
		closable : true,
		frame : true,
		modal : true,
		items : [new Ext.form.FormPanel({
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true,
			height : 320,
			region : 'center',
			id : 'hostunitEditForm',
			labelWidth :60,
			items : [{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{
	          		columnWidth:.60,items: [{xtype : 'textfield',fieldLabel : "单位名称",name : "hostUnitName",width:230,allowBlank : false}]
				},{columnWidth:.40,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "邮政编码",name : "postalcode",width:85}]
				}]
	  		},{xtype : 'textfield',fieldLabel : "通讯地址",name : "address",width:380
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{
	          		columnWidth:.50,items: [{xtype : 'textfield',fieldLabel : "联系人",name : "contact",width:160}]
				},{columnWidth:.5,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "职务",name : "postName",width:135}]
				}]
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{columnWidth:.5,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "手机",name : "telephone",width:160}]
				},{columnWidth:.5,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "邮箱",name : "contactEmail",width:135}]
				}]
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{
	          		columnWidth:.50,items: [{xtype : 'textfield',fieldLabel : "办公电话",name : "officeTel",width:160}]
				},{columnWidth:.50,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "传真电话",id : "officeFax",width:135}]
				}]
	  		},{xtype : 'textfield',fieldLabel : "网址",id : "webSite",width:380
	  		},{xtype : 'textarea',fieldLabel : "备注",id : "remark", width:380}
	  		,{xtype : 'hidden',id : "hostUnitId"},{xtype : 'hidden',id : "enterUser"},{xtype : 'hidden',id : "enterDate"}
	  		,{xtype : 'hidden',id : "lastModifyUser"},{xtype : 'hidden',id : "lastModifyDate"},{xtype : 'hidden',id : "delFlag"}]
		})],
		buttons:[{
			text : '确定',
			id:'btn_add',
			icon : context + '/images/icons/save.gif',
			handler : function() {
				var form = Ext.getCmp("hostunitEditWin").getComponent("hostunitEditForm").getForm();
				var hostUnitName = form.findField("hostUnitName").getValue();
				
				form.submit({
					waitMsg : "请稍侯...",
					method : 'POST',
					url : context + submmitUrl,
					success : function(form, action) {
						Ext.getCmp("hostunitGrid").getStore().reload();
						Ext.Msg.alert("信息",action.result.data);
						hostunitEditWin.close();
						hostunitEditWin=null;
					},
					failure : function(form, action) {
						Ext.Msg.alert("信息",action.result.data);
					}
				});
			}
		},{
			text : '关闭',
			icon : context + '/images/icons/delete.gif',
			handler : function() {
				hostunitEditWin.close();
				hostunitEditWin=null;
			}
		}]
	});
	hostunitEditWin.show();
}