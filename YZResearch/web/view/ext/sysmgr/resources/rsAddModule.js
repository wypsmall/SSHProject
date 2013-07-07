// **********************begin����ģ��*************//
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
								save();
							}
						}, {
							text : '����',
							icon : context + '/images/icons/refresh.png',
							handler : function() {
								reset("myForm");
							}
						}, {
							text : '����',
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
				title : '����ģ��',
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
			addWin.close();
		},
		failure : function(form, action) {
			Ext.Msg.alert("��Ϣ",action.result.data);
		}
	});	
}