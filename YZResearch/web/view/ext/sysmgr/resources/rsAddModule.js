// **********************begin新增模块*************//
function addModule() {
	q_formPanel = new Ext.FormPanel({
				labelAlign : 'left',
				buttonAlign : 'center',
				bodyStyle : 'padding:5px',
				frame : true,
				id : 'myForm',
				labelWidth : 120,
				region : 'center',
				items : [{
							xtype : 'textfield',
							fieldLabel : "模块名称(必填)",
							name : "name",
							width : 250,
							maxLength:32,
							allowBlank : false
						}, {
							xtype : 'textfield',
							fieldLabel : "模块编码(必填)",
							name : "code",
							width : 250,
							maxLength:32,
							allowBlank : false
						}, {
							xtype : 'textfield',
							fieldLabel : "父模块名称",
							name : "parentName",
							width : 250,
							maxLength:32,
							value : q_moduleName,
							readOnly : true
						}, {
							xtype : 'numberfield',
							allowNegative : false,
							allowDecimals : false,
							fieldLabel : "模块顺序号(必填)",
							name : "displayIndex",
							width : 250,
							maxLength:20,
							value : 1,
							allowBlank : false
						}, {
							xtype : 'textfield',
							fieldLabel : "个性化图片样式",
							name : "imgUrl",
							maxLength:100,
							width : 250
						}, {
							xtype : 'textfield',
							fieldLabel : "模块URL",
							name : "url",
							maxLength:100,
							width : 250
						}, {
							xtype : 'textarea',
							fieldLabel : "模块描述",
							name : "memo",
							maxLength:1024,
							width : 250
						}, {
							xtype : 'textfield',
							name : "parentId",
							width : 250,
							hidden : true,
							maxLength:32,
							value : q_moduleId,
							readOnly : true
						}],
				buttonAlign : 'center',
				buttons : [{
							text : '保存',
							icon : context + '/images/icons/save.gif',
							handler : function() {
								save();
							}
						}, {
							text : '重置',
							icon : context + '/images/icons/refresh.png',
							handler : function() {
								reset("myForm");
							}
						}, {
							text : '返回',
							icon : context + '/images/icons/rollback.png',
							handler : function() {
								addWin.close();
							}
						}]

			});

	addWin = new Ext.Window({
				width : 450,
				height : 320,
				id : 'addWin',
				title : '新增模块',
				plain : true,
				closable : true,
				resizable : false,
				frame : true,
				layout : 'fit',
				border : false,
				modal : true,
				items : [q_formPanel]
			});
	addWin.show();
}
function save() {
	var form = addWin.getComponent("myForm").getForm();
	var moduleName = form.findField("name").getValue();
	var moduleCode = form.findField("code").getValue();
	var modulePid = form.findField("parentId").getValue();
	var moduleNum = form.findField("displayIndex").getValue();
	
	if(moduleName == null || moduleName==""){
		Ext.Msg.alert('信息', '请填写模块名称！');
		return;
	}
	if(moduleCode == null || moduleCode==""){
		Ext.Msg.alert('信息', '请填写模块编码！');
		return;
	}
	if(moduleNum == null || moduleNum==""){
		Ext.Msg.alert('信息', '请填写模块顺序号！');
		return;
	}
	
	form.baseParams = {
		name : moduleName,
		code : moduleCode,
		imgUrl : form.findField("imgUrl").getValue(),
		url : form.findField("url").getValue(),
		memo : form.findField("memo").getValue()
	}
	form.submit({
		waitMsg : "请稍侯...",
		method : 'POST',
		url :context + '/sysmgr/resources.do?method=saveRsModule&rsCode=IMTI_SYS_RESOURCE',
		success : function(form, action) {
			q_store.reload();
			addWin.close();
		},
		failure : function(form, action) {
			Ext.Msg.alert("信息",action.result.data);
		}
	});	
}