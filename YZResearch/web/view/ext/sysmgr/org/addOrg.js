// **********************begin������֯����*************//
function addOrg() {

var typeRadio=new Ext.form.RadioGroup({ 
	id : 'type', 
	fieldLabel : '���Ź�������', 
	vertical : true, 
	columns : 2, 
	items : [{boxLabel:'��ͨ����', name:'type',checked:true,
				 		listeners : { 
				     	'check' : function(checkbox, checked){ 
				        	 if(checked){
				         	type="01";}}}}, 
						{boxLabel:'��������', name:'type',
				 		listeners : { 
				     		'check' : function(checkbox, checked){ 
				        		 if(checked){ type="02";}}}},
				        {boxLabel:'��������', name:'type',
				 		listeners : { 
				     		'check' : function(checkbox, checked){ 
				        		 if(checked){ type="03";}}}},
				        {boxLabel:'��������', name:'type',
				 		listeners : { 
				     		'check' : function(checkbox, checked){ 
				        		 if(checked){ type="04";}}}}]}); 

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
							fieldLabel : "��������(����)",
							name : "orgName",
							width : 250,
							maxLength:32,
							allowBlank : false
						}, {
							xtype : 'textfield',
							fieldLabel : "��������(����)",
							name : "orgCode",
							width : 250,
							maxLength:32,
							allowBlank : false
						}, {
							xtype : 'textfield',
							fieldLabel : "����������",
							name : "orgParentName",
							width : 250,
							maxLength:32,
							value : q_orgName,
							readOnly : true
						}, {
							xtype : 'textarea',
							fieldLabel : "��������",
							name : "memo",
							maxLength:1024,
							width : 250
						},
				         {
							xtype : 'textfield',
							name : "orgParentId",
							width : 250,
							hidden : true,
							maxLength:32,
							value : q_OrgId,
							readOnly : true
						},typeRadio],
				        
							buttonAlign : 'center',
							buttons : [{text : '����',
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
				title : '������֯����',
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
		type :type
	}
	form.submit({
		waitMsg : "���Ժ�...",
		method : 'POST',
		url :context + '/sysmgr/sysmgr.do?method=saveOrg&rsCode=IMTI_SYS_ORG',
		success : function(form, action) {
			q_store.reload();
			addWin.close();
		},
		failure : function(form, action) {
			Ext.Msg.alert("��Ϣ",action.result.msg);
		}
	});	
}