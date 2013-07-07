function modifyRsModule() {
	var array = q_sm.getSelections();
	if(array.length == 0){
		Ext.Msg.alert('信息', '请选择你要修改的行！');
		return ;
	} else if(array.length > 1){
		Ext.Msg.alert('信息', '请选择一行数据！');
		return ;
	}
	var sm = q_grid.getSelectionModel();
	var record = sm.getSelected();
	var id = record.get("id"); //唯一标识
	var code = record.get("code");//编码
	var parentId = record.get("parentId");//父资源（父模块）
	var level = record.get("level"); //层次
	var validFlag = record.get("validFlag");//有效性
	var memo = record.get("memo");//备注
	var name = record.get("name");//模块名称
	var imgUrl = record.get("imgUrl");//个性化图片路径
	var displayIndex = record.get("displayIndex");//顺序号
	var url = record.get("url");//模块URL
	var ztId = record.get("ztId");//帐套

	modifyModule_formPanel = new Ext.FormPanel({
				labelAlign : 'left',
				buttonAlign : 'center',
				bodyStyle : 'padding:5px',
				frame : true,
				id : 'modifyModuleForm',
				labelWidth : 120,
				region : 'center',
				items : [{
							xtype : 'hidden',
							anchor: '100%',
                            name: "id"
						},{
							xtype : 'hidden',
							anchor: '100%',
                            name: "level"
						},{
							xtype : 'hidden',
							anchor: '100%',
                            name: "ztId"
						},{
							xtype : 'hidden',
							anchor: '100%',
                            name: "validFlag"
						},{
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
								modifyModuleSave();
							}
						}, {
							text : '重置',
							icon : context + '/images/icons/refresh.png',
							handler : function() {
								modifyModuleReset();
							}
						}, {
							text : '返回',
							icon : context + '/images/icons/rollback.png',
							handler : function() {
								modifyModuleWin.close();
							}
						}]

			});

	modifyModuleWin = new Ext.Window({
				width : 450,
				height : 320,
				id : 'modifyModuleWin',
				title : '修改模块',
				plain : true,
				closable : true,
				resizable : false,
				frame : true,
				layout : 'fit',
				border : false,
				modal : true,
				items : [modifyModule_formPanel]
			})

	modifyModuleWin.show();

	var mf = modifyModuleWin.getComponent("modifyModuleForm").getForm();
	mf.findField("id").setValue(id);
	mf.findField("name").setValue(name);
	mf.findField("memo").setValue(memo);
	mf.findField("code").setValue(code);
	mf.findField("level").setValue(level);
	mf.findField("validFlag").setValue(validFlag);
	mf.findField("url").setValue(url);
	mf.findField("displayIndex").setValue(displayIndex);
	mf.findField("parentId").setValue(parentId);
	mf.findField("imgUrl").setValue(imgUrl);
	mf.findField("ztId").setValue(ztId);
	
}

// ----保存修改模块---//
function modifyModuleSave() {
	var form = modifyModuleWin.getComponent("modifyModuleForm").getForm();
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
			modifyModuleWin.close();
		},
		failure : function(form, action) {
			Ext.Msg.alert("信息",action.result.msg);
		}
	});	
}