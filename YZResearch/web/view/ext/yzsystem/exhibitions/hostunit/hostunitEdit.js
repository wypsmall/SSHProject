function loadEditData(){
	var record=Ext.getCmp("hostunitGrid").getSelectionModel().getSelected();
	if (record) {
		submmitUrl="/yzsystem/exhibitions/hostunit.do?method=updateHostunit&rsCode=HOSMGR_PAGE_UPDATE";
		openEditWin();
		Ext.getCmp("hostunitEditForm").getForm().loadRecord(record);
	}
}
/*
 * ���쵥λ
 */
function openEditWin(){
	var hostunitEditWin = new Ext.Window({
		width : 520,
		height : 350,
		id : 'hostunitEditWin',
		iconCls : 'window-edit',
		buttonAlign : 'center',
		title : '���쵥λ����ά��',
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
	          		columnWidth:.60,items: [{xtype : 'textfield',fieldLabel : "��λ����",name : "hostUnitName",width:230,allowBlank : false}]
				},{columnWidth:.40,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "��������",name : "postalcode",width:85}]
				}]
	  		},{xtype : 'textfield',fieldLabel : "ͨѶ��ַ",name : "address",width:380
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{
	          		columnWidth:.50,items: [{xtype : 'textfield',fieldLabel : "��ϵ��",name : "contact",width:160}]
				},{columnWidth:.5,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "ְ��",name : "postName",width:135}]
				}]
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{columnWidth:.5,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "�ֻ�",name : "telephone",width:160}]
				},{columnWidth:.5,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "����",name : "contactEmail",width:135}]
				}]
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{
	          		columnWidth:.50,items: [{xtype : 'textfield',fieldLabel : "�칫�绰",name : "officeTel",width:160}]
				},{columnWidth:.50,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "����绰",id : "officeFax",width:135}]
				}]
	  		},{xtype : 'textfield',fieldLabel : "��ַ",id : "webSite",width:380
	  		},{xtype : 'textarea',fieldLabel : "��ע",id : "remark", width:380}
	  		,{xtype : 'hidden',id : "hostUnitId"},{xtype : 'hidden',id : "enterUser"},{xtype : 'hidden',id : "enterDate"}
	  		,{xtype : 'hidden',id : "lastModifyUser"},{xtype : 'hidden',id : "lastModifyDate"},{xtype : 'hidden',id : "delFlag"}]
		})],
		buttons:[{
			text : 'ȷ��',
			id:'btn_add',
			icon : context + '/images/icons/save.gif',
			handler : function() {
				var form = Ext.getCmp("hostunitEditWin").getComponent("hostunitEditForm").getForm();
				var hostUnitName = form.findField("hostUnitName").getValue();
				
				form.submit({
					waitMsg : "���Ժ�...",
					method : 'POST',
					url : context + submmitUrl,
					success : function(form, action) {
						Ext.getCmp("hostunitGrid").getStore().reload();
						Ext.Msg.alert("��Ϣ",action.result.data);
						hostunitEditWin.close();
						hostunitEditWin=null;
					},
					failure : function(form, action) {
						Ext.Msg.alert("��Ϣ",action.result.data);
					}
				});
			}
		},{
			text : '�ر�',
			icon : context + '/images/icons/delete.gif',
			handler : function() {
				hostunitEditWin.close();
				hostunitEditWin=null;
			}
		}]
	});
	hostunitEditWin.show();
}