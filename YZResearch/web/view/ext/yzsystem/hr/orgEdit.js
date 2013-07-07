function loadOrgEditData(){
	var record=Ext.getCmp("orgGrid").getSelectionModel().getSelected();
	if (record) {
		submmitUrl="/yzsystem/hr/hrmgr.do?method=updateHrOrg&rsCode=ORGMGR_PAGE_UPDATE";
		openOrgEditWin();
		Ext.getCmp("orgEditForm").getForm().loadRecord(record);
	}
}
/*
 * ��������
 */
function openOrgEditWin(){
	var orgEditWin = new Ext.Window({
		width : 520,
		height : 300,
		id : 'orgEditWin',
		iconCls : 'window-edit',
		buttonAlign : 'center',
		title : q_orgName,
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
			id : 'orgEditForm',
			labelWidth :60,
			items : [{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{
	          		columnWidth:.50,items: [{xtype : 'textfield',fieldLabel : "����",name : "orgName",width:160,allowBlank : false}]
				},{columnWidth:.5,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "����",name : "orgCode",width:135,allowBlank : false}]
				}]
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{columnWidth:.5,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "�绰",name : "tel",width:160}]
				},{columnWidth:.5,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "����",name : "fax",width:135}]
				}]
	  		},{xtype : 'textfield',fieldLabel : "��ַ",id : "address",width:380
	  		},{xtype : 'textarea',fieldLabel : "��ע",id : "memo", width:380},
	  		{xtype : 'hidden',id : "orgId"},{xtype : 'hidden',id : "orgParentId"},
	  		{xtype : 'hidden',id : "delFlag"},{xtype : 'hidden',id : "orgParentName"}]
		})],
		buttons:[{
			text : 'ȷ��',
			id:'btn_add',
			icon : context + '/images/icons/save.gif',
			handler : function() {
				var form = Ext.getCmp("orgEditWin").getComponent("orgEditForm").getForm();
				var orgParentId = form.findField("orgParentId").getValue();
				var orgParentName = form.findField("orgParentName").getValue();
				orgParentId = orgParentId == "" ? q_OrgId : orgParentId;
				orgParentName = orgParentName == "" ? q_orgName : orgParentName;
				form.baseParams = {
					"org.orgId" : form.findField("orgId").getValue(),
					"org.delFlag" : form.findField("delFlag").getValue(),
					"org.orgName" : form.findField("orgName").getValue(),
					"org.orgCode" : form.findField("orgCode").getValue(),
					"org.memo" : form.findField("memo").getValue(),
					"org.address" : form.findField("address").getValue(),
					"org.tel" : form.findField("tel").getValue(),
					"org.fax" : form.findField("fax").getValue(),
					"org.orgParentId" : orgParentId,
					"org.orgParentName" : orgParentName
				};
				form.submit({
					waitMsg : "���Ժ�...",
					method : 'POST',
					url : context + submmitUrl,
					success : function(form, action) {
						Ext.getCmp("orgGrid").getStore().reload();
						Ext.Msg.alert("��Ϣ",action.result.data);
						orgEditWin.close();
						orgEditWin=null;
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
				orgEditWin.close();
				orgEditWin=null;
			}
		}]
	});
	orgEditWin.show();
}