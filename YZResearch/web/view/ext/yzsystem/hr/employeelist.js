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
				header : "姓名",dataIndex : "employeeName",width:80
			},{ header : "工号",dataIndex : "employeeNo",width:80
			},{ header : "部门",dataIndex : "orgParentId",width:80,
				renderer : function(value, cellmeta, record, rowIndex,columnIndex, store) {
					if(value=="root"){
						return record.get("groupName");
					}else {
						return record.get("orgName");
					}
				}
			},{ header : "分组",dataIndex : "orgParentId",width:80,
				renderer : function(value, cellmeta, record, rowIndex,columnIndex, store) {
					if(value=="root"){
						return '未分组';
					}else {
						return record.get("groupName");
					}
				}
			},{ header : "性别",dataIndex : "employeeSex",width:60,
				renderer : function(value) {
					if(value=="1"){
						return '<font color="blue">男<font>';
					}else if(value=="2"){
						return '<font color="green">女</font>';
					}else {
						return '<font color="red">保密</font>';
					}
				}
			},{ header : "生日",dataIndex : "employeeBirthType",width:100,
				renderer : function(value, cellmeta, record, rowIndex,columnIndex, store) {
					if(record.get('employeeBirth')==''){
						return '<font color="red">保密</font>';
					}else if(value=="1"){
						return record.get('employeeBirth') + '<font color="blue">[农历]<font>';
					}else if(value=="2"){
						return record.get('employeeBirth') + '<font color="green">[阳历]</font>';
					} 
				}
			},{ header : "职位",dataIndex : "employeePostName",width:80
			},{ header : "在职状态",dataIndex : "employeeState",width:80,
				renderer : function(value, cellmeta, record, rowIndex,columnIndex, store) {
					if(value=="1"){
						return '<font color="blue">实习期<font>';
					}else if(value=="2"){
						return '<font color="green">试用期</font>';
					}else if(value=="3"){
						return '<font color="purple">正式职员</font>';
					}else if(value=="4"){
						return '<font color="red">已离职</font>';
					}
				}
			},{ header : "入职日期",dataIndex : "entryJobDate",width:80
			},{ header : "合同开始日期",dataIndex : "contractStartDate",width:100
			},{ header : "合同结束日期",dataIndex : "contractEndDate",width:100
			},{ header : "离职日期",dataIndex : "offJobDate",width:80
			},{ header : "身份证号",dataIndex : "identification",width:100
			},{ header : "紧急联系人",dataIndex : "contact",width:80
			},{ header : "联系电话",dataIndex : "contactTel",width:80
			},{ header : "备注",dataIndex : "memo",width:80
			},{ header : "家庭住址",dataIndex : "homeAddress",width:80
			},{ header : "户口类别",dataIndex : "accountType",width:80,
				renderer : function(value) {
					if(value=="1"){
						return '<font color="blue">外地农业<font>';
					}else if(value=="2"){
						return '<font color="green">外地非农</font>';
					}else if(value=="3"){
						return '<font color="purple">本地农业</font>';
					}else if(value=="4"){
						return '<font color="red">本地非农</font>';
					}
				}
			},{ header : "社保购买状态",dataIndex : "hasBuyShebao",width:80,
				renderer : function(value) {
					if(value=="0"){
						return '<font color="red">未购买<font>';
					}else if(value=="1"){
						return '<font color="green">已购买</font>';
					}
				}
			},{ header : "毕业时间",dataIndex : "graduateDate",width:80
			},{ header : "毕业院校",dataIndex : "graduateSch",width:80
			},{ header : "学历",dataIndex : "education",width:80
			},{ header : "专业",dataIndex : "profession",width:80
			}
		]),
		bodyStyle :'width:99.8%',
		height:window.screen.availHeight-210,
		width : window.screen.availWidth-212 ,
		autoScroll:true,
		externalFilters :true,
		tbar : ['','->',
	        Ext.extend.SearchAction("查询",context, function(){
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
		    }),Ext.extend.GroupAction('分组',context, function(){
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