Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = context + "/images/s.gif";
	Ext.QuickTips.init();
	
	var q_sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var q_store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : context + '/yzsystem/hr/hrmgr.do?method=getEmployeeListByOrg&rsCode=EMPMGR_PAGE_VIEW',
			method : 'POST'
		}),
		reader : new Ext.data.JsonReader({
					totalProperty : 'total',
					root : 'rows',
					id : "employeeId"
				}, [{name : 'employeeId'},{name : 'employeeNo'},{name : 'employeeName'},{name : 'employeeSex'},{name : 'employeeBirthType'},
				    {name : 'employeeBirth'},{name : 'employeePostName'},{name : 'employeeState'},{name : 'entryJobDate'},{name : 'contractStartDate'},
				    {name : 'contractEndDate'},{name : 'offJobDate'},{name : 'identification'},{name : 'contact'},{name : 'contactTel'},{name : 'memo'},
				    {name : 'homeAddress'},{name : 'accountType'},{name : 'hasBuyShebao'},{name : 'graduateDate'},{name : 'graduateSch'},{name : 'education'},
				    {name : 'profession'},{name : 'orgId'},{name : 'orgName'},{name : 'groupName'},{name : 'orgParentId'}]),
		autoLoad : true
	}); 
	
	var q_grid = new Ext.grid.EditorGridPanel({
		id : 'employeeGrid',
		renderTo : 'center',
		fram : true,
		cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({width:30}),q_sm, {
				header : "����",dataIndex : "employeeName",width:80
			},{ header : "����",dataIndex : "employeeNo",width:80
			},{ header : "����",dataIndex : "orgParentId",width:80,
				renderer : function(value, cellmeta, record, rowIndex,columnIndex, store) {
					if(value=="root"){
						return record.get("groupName");
					}else {
						return record.get("orgName");
					}
				}
			},{ header : "����",dataIndex : "orgParentId",width:80,
				renderer : function(value, cellmeta, record, rowIndex,columnIndex, store) {
					if(value=="root"){
						return 'δ����';
					}else {
						return record.get("groupName");
					}
				}
			},{ header : "�Ա�",dataIndex : "employeeSex",width:60,
				renderer : function(value) {
					if(value=="1"){
						return '<font color="blue">��<font>';
					}else if(value=="2"){
						return '<font color="green">Ů</font>';
					}else {
						return '<font color="red">����</font>';
					}
				}
			},{ header : "����",dataIndex : "employeeBirthType",width:100,
				renderer : function(value, cellmeta, record, rowIndex,columnIndex, store) {
					if(record.get('employeeBirth')==''){
						return '<font color="red">����</font>';
					}else if(value=="1"){
						return record.get('employeeBirth') + '<font color="blue">[ũ��]<font>';
					}else if(value=="2"){
						return record.get('employeeBirth') + '<font color="green">[����]</font>';
					} 
				}
			},{ header : "ְλ",dataIndex : "employeePostName",width:80
			},{ header : "��ְ״̬",dataIndex : "employeeState",width:80,
				renderer : function(value, cellmeta, record, rowIndex,columnIndex, store) {
					if(value=="1"){
						return '<font color="blue">ʵϰ��<font>';
					}else if(value=="2"){
						return '<font color="green">������</font>';
					}else if(value=="3"){
						return '<font color="purple">��ʽְԱ</font>';
					}else if(value=="4"){
						return '<font color="red">����ְ</font>';
					}
				}
			},{ header : "��ְ����",dataIndex : "entryJobDate",width:80
			},{ header : "��ͬ��ʼ����",dataIndex : "contractStartDate",width:100
			},{ header : "��ͬ��������",dataIndex : "contractEndDate",width:100
			},{ header : "��ְ����",dataIndex : "offJobDate",width:80
			},{ header : "���֤��",dataIndex : "identification",width:100
			},{ header : "������ϵ��",dataIndex : "contact",width:80
			},{ header : "��ϵ�绰",dataIndex : "contactTel",width:80
			},{ header : "��ע",dataIndex : "memo",width:80
			},{ header : "��ͥסַ",dataIndex : "homeAddress",width:80
			},{ header : "�������",dataIndex : "accountType",width:80,
				renderer : function(value) {
					if(value=="1"){
						return '<font color="blue">���ũҵ<font>';
					}else if(value=="2"){
						return '<font color="green">��ط�ũ</font>';
					}else if(value=="3"){
						return '<font color="purple">����ũҵ</font>';
					}else if(value=="4"){
						return '<font color="red">���ط�ũ</font>';
					}
				}
			},{ header : "�籣����״̬",dataIndex : "hasBuyShebao",width:80,
				renderer : function(value) {
					if(value=="0"){
						return '<font color="red">δ����<font>';
					}else if(value=="1"){
						return '<font color="green">�ѹ���</font>';
					}
				}
			},{ header : "��ҵʱ��",dataIndex : "graduateDate",width:80
			},{ header : "��ҵԺУ",dataIndex : "graduateSch",width:80
			},{ header : "ѧ��",dataIndex : "education",width:80
			},{ header : "רҵ",dataIndex : "profession",width:80
			}
		]),
		bodyStyle :'width:99.8%',
		height:window.screen.availHeight-210,
		width : window.screen.availWidth-212 ,
		autoScroll:true,
		externalFilters :true,
		tbar : ['','->',
	        Ext.extend.SearchAction("��ѯ",context, function(){
	    		Ext.getCmp("hostunitGrid").getStore().reload();
	    	}, function(e){
		    	checkRole(e,"EMPMGR_PAGE_VIEW");
		    }),
		    Ext.extend.AddAction(context, function(){
		    	submmitUrl="/yzsystem/hr/hrmgr.do?method=insertHrEmployee&rsCode=EMPMGR_PAGE_INSERT";
		    	openEmployeeEditWin();
		    }, function(e){
			    	checkRole(e,"EMPMGR_PAGE_INSERT");
		    }), 
		    Ext.extend.UpdateAction(context, function(){
		    	loadEmployeeEditData();
		    }, function(e){
		    	checkRole(e,"EMPMGR_PAGE_UPDATE");
		    }), 
		    Ext.extend.DeleteAction(context, function(){
		    	var url = context + '/yzsystem/hr/hrmgr.do';
		    	var paramName = 'employeeId';
		    	var acMethod = 'deleteHrEmployee';
		    	var rsCode = 'EMPMGR_PAGE_DELETE';
		    	function callBackFunc(){
		    		q_grid.getStore().load();
		    	}
		    	operatePkAction(q_sm,'employeeId','','', "", url, paramName,acMethod, rsCode,callBackFunc);
		    },function(e){
		    	checkRole(e,"EMPMGR_PAGE_DELETE");
		    }),Ext.extend.GroupAction('����',context, function(){
		    	groupEmployee();
		    },function(e){
		    	checkRole(e,"EMPMGR_PAGE_GROUP");
		    })],
		store : q_store,
		sm : q_sm,
		listeners:{
            "rowdblclick" : function(grid, rowIndex, e){
				if(parent && parent.permissionMap && parent.permissionMap.get("EMPMGR_PAGE_UPDATE")){
					loadEmployeeEditData();
				 }
            }
        },
        bbar:  Ext.extend.PagingToolbar(q_store, 20)
	});
	
	q_grid.getStore().on("beforeload", function() {
    	this.baseParams = {
    		parentId: ''
    	};
    }); 
});	