function modifyOrg() {
	var sm = q_grid.getSelectionModel();
	var record = sm.getSelected();
	var id = record.get("orgId"); //Ψһ��ʶ
	var code = record.get("orgCode");//����
	var parentId = record.get("orgParentId");//����Դ����ģ�飩
	var memo = record.get("memo");//��ע
	var name = record.get("orgName");//ģ������
	var ztId = record.get("ztId");//����
	var ownerCompany = record.get("ownerCompany");//����
	var displayIndex = record.get("displayIndex");
    type = record.get("type");//ģ������

var typeRadio=new Ext.form.RadioGroup({ 
	id : 'type', 
	fieldLabel : '���Ź�������', 
	vertical : true, 
	columns : 2, 
	items : [{boxLabel:'��ͨ����', name:'type',id:"type1",
				 		listeners : { 
				     	'check' : function(checkbox, checked){ 
				        	 if(checked){
				         	type="01";}}}}, 
						{boxLabel:'��������', name:'type',id:"type2",
				 		listeners : { 
				     		'check' : function(checkbox, checked){ 
				        		 if(checked){ type="02";}}}},
				        {boxLabel:'��������', name:'type',id:"type3",
				 		listeners : { 
				     		'check' : function(checkbox, checked){ 
				        		 if(checked){ type="03";}}}},
				        {boxLabel:'��������', name:'type',id:"type4",
				 		listeners : { 
				     		'check' : function(checkbox, checked){ 
				        		 if(checked){ 
				        		 type="04";}}}}]});
	modifyOrg_formPanel = new Ext.FormPanel({
				labelAlign : 'left',
				buttonAlign : 'center',
				bodyStyle : 'padding:5px',
				frame : true,
				id : 'modifyOrgForm',
				labelWidth : 120,
				region : 'center',
				items : [{
							xtype : 'hidden',
							anchor: '100%',
                            name: "orgId"
						},{
							xtype : 'hidden',
							anchor: '100%',
                            name: "orgParentId"
						},{
							xtype : 'hidden',
							anchor: '100%',
                            name: "ztId"
						},{
							xtype : 'hidden',
							anchor: '100%',
                            name: "ownerCompany"
						},{
							xtype : 'hidden',
							anchor: '100%',
                            name: "displayIndex"
						},{
							xtype : 'textfield',
							fieldLabel : "ģ������(����)",
							name : "orgName",
							width : 250,
							maxLength:32,
							allowBlank : false
						}, {
							xtype : 'textfield',
							fieldLabel : "ģ�����(����)",
							name : "orgCode",
							width : 250,
							maxLength:32,
							allowBlank : false
						}, {
							xtype : 'textfield',
							fieldLabel : "��ģ������",
							name : "orgParentName",
							width : 250,
							maxLength:32,
							value : q_orgName,
							readOnly : true
						}, {
							xtype : 'textarea',
							fieldLabel : "ģ������",
							name : "memo",
							maxLength:1024,
							width : 250
						},
						typeRadio
						],
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
								modifyOrgWin.close();
							}
						}]

			});

	modifyOrgWin = new Ext.Window({
				width : 450,
				height : 320,
				id : 'modifyOrgWin',
				title : '�޸�ģ��',
				plain : true,
				closable : true,
				resizable : false,
				frame : true,
				layout : 'fit',
				border : false,
				modal : true,
				items : [modifyOrg_formPanel]
			});
	modifyOrgWin.show();
	var mf = modifyOrgWin.getComponent("modifyOrgForm").getForm();
	mf.findField("orgId").setValue(id);
	mf.findField("orgName").setValue(name);
	mf.findField("memo").setValue(memo);
	mf.findField("orgCode").setValue(code);
	mf.findField("ztId").setValue(ztId);
	mf.findField("orgParentId").setValue(parentId);
	mf.findField("ownerCompany").setValue(ownerCompany);
	mf.findField("displayIndex").setValue(displayIndex);
}
// ----�����޸�ģ��---//
function modifyModuleSave() {
	var form = modifyOrgWin.getComponent("modifyOrgForm").getForm();
	var orgName = form.findField("orgName").getValue();
	var orgCode = form.findField("orgCode").getValue();
	var orgParentId = form.findField("orgParentId").getValue();
	
	if(orgName == null || orgName==""){
		Ext.Msg.alert('��Ϣ', '����д�������ƣ�');
		return;
	}
	if(orgCode == null || orgCode==""){
		Ext.Msg.alert('��Ϣ', '����д�������룡');
		return;
	}
	if(orgParentId == null || orgParentId==""){
		Ext.Msg.alert('��Ϣ', '����������Ϊ�գ�');
		return;
	}
	form.baseParams = {
		orgName : orgName,
		orgCode : orgCode,
		memo : form.findField("memo").getValue(),
		type: type
	}
	form.submit({
		waitMsg : "���Ժ�...",
		method : 'POST',
		url :context + '/sysmgr/sysmgr.do?method=saveOrg&rsCode=IMTI_SYS_ORG',
		success : function(form, action) {
			q_grid.getStore().reload();
			modifyOrgWin.close();
		},
		failure : function(form, action) {
			Ext.Msg.alert("��Ϣ",action.result.msg);
		}
	});	
}