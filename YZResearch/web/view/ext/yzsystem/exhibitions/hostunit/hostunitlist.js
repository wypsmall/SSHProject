Ext.onReady(function(){ 
	Ext.BLANK_IMAGE_URL = context + "/images/s.gif";
	Ext.QuickTips.init(); 
	var myReader = new Ext.data.JsonReader({
		root : "rows" 
	}, [{name : 'hostUnitId'},{name : 'hostUnitName'},{name : 'postalcode'},{name : 'address'},
	    {name : 'contact'},{name : 'postName'},{name : 'telephone'},{name : 'contactEmail'},
	    {name : 'officeTel'},{name : 'officeFax'},{name : 'webSite'},{name : 'remark'},
	    {name : 'enterUser'},{name : 'enterDate'},{name : 'lastModifyUser'},{name : 'lastModifyDate'},{name : 'delFlag'}]);
	q_sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});	
	store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :context + '/yzsystem/exhibitions/hostunit.do?method=getHostunitList&rsCode=HOSMGR_PAGE_VIEW',
			method : "POST"
		}),
		reader : myReader,
		autoLoad : true
	});
	grid = new Ext.grid.GridPanel({
		renderTo:"center",
		title:"<center>主办单位列表</center>",
		id: "hostunitGrid",
		height:window.screen.availHeight-210,
		width : window.screen.availWidth-212 ,
		bodyStyle :'width:99.8%',
		autoWidth:true,
		sm : q_sm,
		store:store,
	    columns:[q_sm,{
			header :"单位名称",dataIndex :'hostUnitName',sortable :true,width:120
		},{ header :"邮政编码",dataIndex :'postalcode',width:100
		},{ header :"通讯地址",dataIndex :'address',width:150
		},{ header :"联系人",dataIndex :'contact',width:90
		},{ header :"职务",dataIndex :'postName',width:80
		},{ header :"联系手机",dataIndex :'telephone',width:120
		},{ header :"联系邮箱",dataIndex :'contactEmail',width:120
		},{ header :"办公电话",dataIndex :'officeTel',width:120
		},{ header :"传真电话",dataIndex :'officeFax',width:120
		},{ header :"公司网址",dataIndex :'webSite',width:150
		},{ header :"备注",dataIndex :'remark',width:150
		},{ header :"录入员",dataIndex :'enterUser',width:90
		},{ header :"录入时间",dataIndex :'enterDate',width:90
		},{ header :"修改人",dataIndex :'lastModifyUser',width:90
		},{ header :"修改时间",dataIndex :'lastModifyDate',width:90
		}],
		tbar : ['主办单位：',{xtype : 'textfield',width : 120,id : 'searchHostUnitName'},
			    '录入员：',{xtype : 'textfield',width : 80,id : 'searchEnterUser'},
			    '最后修改人：',{xtype : 'textfield',width : 80,id : 'searchLastModifyUser'},
		    '->',
		    Ext.extend.SearchAction("查询",context, function(){
		    		Ext.getCmp("hostunitGrid").getStore().reload();
		    	}, function(e){
		    	checkRole(e,"HOSMGR_PAGE_VIEW");
		    }), 
		    Ext.extend.AddAction(context, function(){
		    	submmitUrl="/yzsystem/exhibitions/hostunit.do?method=insetHostunit&rsCode=HOSMGR_PAGE_INSERT";
		    	openEditWin();
		    }, function(e){
			    	checkRole(e,"HOSMGR_PAGE_INSERT");
		    }), 
		    Ext.extend.UpdateAction(context, function(){
		    	loadEditData();
		    }, function(e){
		    	checkRole(e,"HOSMGR_PAGE_UPDATE");
		    }), 
		    Ext.extend.DeleteAction(context, function(){
		    	var url = context + '/yzsystem/exhibitions/hostunit.do';
		    	var paramName = 'hostUnitId';
		    	var acMethod = 'deleteHostunit';
		    	var rsCode = 'HOSMGR_PAGE_DELETE';
		    	function callBackFunc(){
		    		grid.getStore().load();
		    	}
		    	operatePkAction(q_sm,'hostUnitId','','', "", url, paramName,acMethod, rsCode,callBackFunc);
		    },function(e){
		    	checkRole(e,"HOSMGR_PAGE_DELETE");
		    }),'&nbsp;'],
		listeners:{
	    	"rowdblclick" : function(grid, rowIndex, e){
		    	if(parent && parent.permissionMap && parent.permissionMap.get("HOSMGR_PAGE_UPDATE")){
		    		loadEditData();
				 }
	         }
	     },
	     bbar:  Ext.extend.PagingToolbar(store, 20)
	});		
	grid.getStore().on("beforeload", function() {
    	this.baseParams = {
    		hostUnitName: Ext.getCmp("searchHostUnitName").getValue(),
    		enterUser : Ext.getCmp("searchEnterUser").getValue(),
    		lastModifyUser : Ext.getCmp("searchLastModifyUser").getValue()
    	};
    }); 
});