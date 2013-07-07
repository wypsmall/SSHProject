Ext.onReady(function(){ 
	Ext.QuickTips.init(); 
	var myReader = new Ext.data.JsonReader({
		root : "rows" 
	}, [{name : 'rootModuleName'},{name : 'secondModuleName'},{name : 'thirdModuleName'},{name : 'operateName'},
	    {name : 'operateStatusName'},{name : 'errMsg'},{name : 'operator'}, {name : 'operateTime'}]);
	var store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :context + '/sysmgr/loginlog.do?method=busiLogList&rsCode=IMTI_BUSI_LOG_VIEW',
			method : "POST"
		}),
		reader : myReader
	});
	var grid = new Ext.grid.GridPanel({
		renderTo:"center",
		title:"<center>业务日志日志列表</center>",
		id: "contentGrid",
		height:window.screen.availHeight-210,
		store:store,
		viewConfig : {
			forceFit : 'true'
	    },
	    columns:[new Ext.grid.RowNumberer({width:30}),{
			header :"子系统",dataIndex :'rootModuleName',sortable :true,width:80
		},{ header :"模块",dataIndex :'secondModuleName',sortable :true,width:100
		},{ header :"子模块",dataIndex :'thirdModuleName',sortable :true,width:100
		},{ header :"操作名称",dataIndex :'operateName',sortable :true,width:60
		},{ header :"成功否",dataIndex :'operateStatusName',width:50
		},{ header :"错误说明",dataIndex :'errMsg',width:250
		},{ header :"操作人",dataIndex :'operator',width:60
		},{ header :"操作时间",dataIndex :'operateTime',width:100
		}],
		tbar : ['操作时间：',{xtype: 'datefield',name : 'startTime',id:'startTime',width:90,format:'Y-m-d'
				},'至：',{xtype: 'datefield',name : 'endTime',id:'endTime',width:90,format:'Y-m-d'
				},'子系统：',{xtype : 'textfield',width : 70,id : 'searchRootModuleName'
				},'模块：',{	xtype : 'textfield',width : 70,id : 'searchSecondModuleName'
				},'子模块：',{xtype : 'textfield',width : 70,id : 'searchThirdModuleName'
				},'操作名：',{xtype : 'textfield',width : 60,id : 'searchOperateName'
				},'操作人：',{xtype : 'textfield',width : 60,id : 'searchOperator'
				},'->',
				Ext.extend.SearchAction("查询",context, function(){
			    		Ext.getCmp("contentGrid").getStore().reload();
			    	}, function(e){
			    	checkRole(e,"IMTI_SYS_LOGINLOG_VIEW");
			    }) ,'&nbsp;'],
		bbar:  Ext.extend.PagingToolbar(store, 20)	
	});	
	
	grid.getStore().on("beforeload", function() {
		var startTime = Ext.getCmp("startTime").getValue();
    	var endTime = Ext.getCmp("endTime").getValue();
    	var startTimeStr="";
		var endTimeStr="";
		if(startTime != ""){
       		startTimeStr = startTime.format("Y-m-d");
       	}  
		if(endTime != ""){
    	 	endTimeStr = endTime.format("Y-m-d");
       	}  
		if(startTime != "" && endTime != ""){
       		if(startTime > endTime){
       			Ext.Msg.alert("信息","&nbsp;&nbsp;&nbsp;开始日期不能大于结束日期！&nbsp;");
       		}
       	}   
    	this.baseParams = {
    		limit: 20,
    		rootModuleName : Ext.getCmp("searchRootModuleName").getValue(),
    		secondModuleName : Ext.getCmp("searchSecondModuleName").getValue(),
    		thirdModuleName : Ext.getCmp("searchThirdModuleName").getValue(),
    		operateName : Ext.getCmp("searchOperateName").getValue(),
    		operator : Ext.getCmp("searchOperator").getValue(),
			opeDateBegin : startTimeStr,
			opeDateEnd : endTimeStr
    	};
    }); 	
});