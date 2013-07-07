function modifyRsModule() {
	var array = q_sm.getSelections();
	if(array.length == 0){
		Ext.Msg.alert('��Ϣ', '��ѡ����Ҫ�޸ĵ��У�');
		return ;
	} else if(array.length > 1){
		Ext.Msg.alert('��Ϣ', '��ѡ��һ�����ݣ�');
		return ;
	}
	var sm = q_grid.getSelectionModel();
	var record = sm.getSelected();
	var id = record.get("id"); //Ψһ��ʶ
	var code = record.get("code");//����
	var parentId = record.get("parentId");//����Դ����ģ�飩
	var level = record.get("level"); //���
	var validFlag = record.get("validFlag");//��Ч��
	var memo = record.get("memo");//��ע
	var name = record.get("name");//ģ������
	var imgUrl = record.get("imgUrl");//���Ի�ͼƬ·��
	var displayIndex = record.get("displayIndex");//˳���
	var url = record.get("url");//ģ��URL
	var ztId = record.get("ztId");//����

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
							fieldLabel : "ģ������(����)",
							name : "name",
							width : 250,
							maxLength:32,
							allowBlank : false
						}, {
							xtype : 'textfield',
							fieldLabel : "ģ�����(����)",
							name : "code",
							width : 250,
							maxLength:32,
							allowBlank : false
						}, {
							xtype : 'textfield',
							fieldLabel : "��ģ������",
							name : "parentName",
							width : 250,
							maxLength:32,
							value : q_moduleName,
							readOnly : true
						}, {
							xtype : 'numberfield',
							allowNegative : false,
							allowDecimals : false,
							fieldLabel : "ģ��˳���(����)",
							name : "displayIndex",
							width : 250,
							maxLength:20,
							value : 1,
							allowBlank : false
						}, {
							xtype : 'textfield',
							fieldLabel : "���Ի�ͼƬ��ʽ",
							name : "imgUrl",
							maxLength:100,
							width : 250
						}, {
							xtype : 'textfield',
							fieldLabel : "ģ��URL",
							name : "url",
							maxLength:100,
							width : 250
						}, {
							xtype : 'textarea',
							fieldLabel : "ģ������",
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
							text : '����',
							icon : context + '/images/icons/save.gif',
							handler : function() {
								modifyModuleSave();
							}
						}, {
							text : '����',
							icon : context + '/images/icons/refresh.png',
							handler : function() {
								modifyModuleReset();
							}
						}, {
							text : '����',
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
				title : '�޸�ģ��',
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

// ----�����޸�ģ��---//
function modifyModuleSave() {
	var form = modifyModuleWin.getComponent("modifyModuleForm").getForm();
	var moduleName = form.findField("name").getValue();
	var moduleCode = form.findField("code").getValue();
	var modulePid = form.findField("parentId").getValue();
	var moduleNum = form.findField("displayIndex").getValue();
	
	if(moduleName == null || moduleName==""){
		Ext.Msg.alert('��Ϣ', '����дģ�����ƣ�');
		return;
	}
	if(moduleCode == null || moduleCode==""){
		Ext.Msg.alert('��Ϣ', '����дģ����룡');
		return;
	}
	if(moduleNum == null || moduleNum==""){
		Ext.Msg.alert('��Ϣ', '����дģ��˳��ţ�');
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
		waitMsg : "���Ժ�...",
		method : 'POST',
		url :context + '/sysmgr/resources.do?method=saveRsModule&rsCode=IMTI_SYS_RESOURCE',
		success : function(form, action) {
			q_store.reload();
			modifyModuleWin.close();
		},
		failure : function(form, action) {
			Ext.Msg.alert("��Ϣ",action.result.msg);
		}
	});	
}