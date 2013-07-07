// **********************begin新增组织机构*************//
function addOrg() {

var typeRadio=new Ext.form.RadioGroup({ 
	id : 'type', 
	fieldLabel : '部门归属类型', 
	vertical : true, 
	columns : 2, 
	items : [{boxLabel:'普通类型', name:'type',checked:true,
				 		listeners : { 
				     	'check' : function(checkbox, checked){ 
				        	 if(checked){
				         	type="01";}}}}, 
						{boxLabel:'车队类型', name:'type',
				 		listeners : { 
				     		'check' : function(checkbox, checked){ 
				        		 if(checked){ type="02";}}}},
				        {boxLabel:'驳船类型', name:'type',
				 		listeners : { 
				     		'check' : function(checkbox, checked){ 
				        		 if(checked){ type="03";}}}},
				        {boxLabel:'其他类型', name:'type',
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
							fieldLabel : "机构名称(必填)",
							name : "orgName",
							width : 250,
							maxLength:32,
							allowBlank : false
						}, {
							xtype : 'textfield',
							fieldLabel : "机构编码(必填)",
							name : "orgCode",
							width : 250,
							maxLength:32,
							allowBlank : false
						}, {
							xtype : 'textfield',
							fieldLabel : "父机构名称",
							name : "orgParentName",
							width : 250,
							maxLength:32,
							value : q_orgName,
							readOnly : true
						}, {
							xtype : 'textarea',
							fieldLabel : "机构描述",
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
							buttons : [{text : '保存',
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
				title : '新增组织机构',
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
		Ext.Msg.alert('信息', '请填写机构名称！');
		return;
	}
	if(orgCode == null || orgCode==""){
		Ext.Msg.alert('信息', '请填写机构编码！');
		return;
	}
	if(orgParentId == null || orgParentId==""){
		Ext.Msg.alert('信息', '父机构不能为空！');
		return;
	}
	form.baseParams = {
		orgName : orgName,
		orgCode : orgCode,
		memo : form.findField("memo").getValue(),
		type :type
	}
	form.submit({
		waitMsg : "请稍侯...",
		method : 'POST',
		url :context + '/sysmgr/sysmgr.do?method=saveOrg&rsCode=IMTI_SYS_ORG',
		success : function(form, action) {
			q_store.reload();
			addWin.close();
		},
		failure : function(form, action) {
			Ext.Msg.alert("信息",action.result.msg);
		}
	});	
}