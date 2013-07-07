function modifyOrg() {
	var sm = q_grid.getSelectionModel();
	var record = sm.getSelected();
	var id = record.get("orgId"); //唯一标识
	var code = record.get("orgCode");//编码
	var parentId = record.get("orgParentId");//父资源（父模块）
	var memo = record.get("memo");//备注
	var name = record.get("orgName");//模块名称
	var ztId = record.get("ztId");//帐套
	var ownerCompany = record.get("ownerCompany");//帐套
	var displayIndex = record.get("displayIndex");
    type = record.get("type");//模块名称

var typeRadio=new Ext.form.RadioGroup({ 
	id : 'type', 
	fieldLabel : '部门归属类型', 
	vertical : true, 
	columns : 2, 
	items : [{boxLabel:'普通类型', name:'type',id:"type1",
				 		listeners : { 
				     	'check' : function(checkbox, checked){ 
				        	 if(checked){
				         	type="01";}}}}, 
						{boxLabel:'车队类型', name:'type',id:"type2",
				 		listeners : { 
				     		'check' : function(checkbox, checked){ 
				        		 if(checked){ type="02";}}}},
				        {boxLabel:'驳船类型', name:'type',id:"type3",
				 		listeners : { 
				     		'check' : function(checkbox, checked){ 
				        		 if(checked){ type="03";}}}},
				        {boxLabel:'其他类型', name:'type',id:"type4",
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
							fieldLabel : "模块名称(必填)",
							name : "orgName",
							width : 250,
							maxLength:32,
							allowBlank : false
						}, {
							xtype : 'textfield',
							fieldLabel : "模块编码(必填)",
							name : "orgCode",
							width : 250,
							maxLength:32,
							allowBlank : false
						}, {
							xtype : 'textfield',
							fieldLabel : "父模块名称",
							name : "orgParentName",
							width : 250,
							maxLength:32,
							value : q_orgName,
							readOnly : true
						}, {
							xtype : 'textarea',
							fieldLabel : "模块描述",
							name : "memo",
							maxLength:1024,
							width : 250
						},
						typeRadio
						],
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
								modifyOrgWin.close();
							}
						}]

			});

	modifyOrgWin = new Ext.Window({
				width : 450,
				height : 320,
				id : 'modifyOrgWin',
				title : '修改模块',
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
// ----保存修改模块---//
function modifyModuleSave() {
	var form = modifyOrgWin.getComponent("modifyOrgForm").getForm();
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
		type: type
	}
	form.submit({
		waitMsg : "请稍侯...",
		method : 'POST',
		url :context + '/sysmgr/sysmgr.do?method=saveOrg&rsCode=IMTI_SYS_ORG',
		success : function(form, action) {
			q_grid.getStore().reload();
			modifyOrgWin.close();
		},
		failure : function(form, action) {
			Ext.Msg.alert("信息",action.result.msg);
		}
	});	
}