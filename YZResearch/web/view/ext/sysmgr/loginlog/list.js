Ext.onReady(function(){ 
	Ext.QuickTips.init(); 
	var myReader = new Ext.data.JsonReader({
		root : "rows" 
	}, [{name : 'userName'},{name : 'loginIP'},{name : 'loginTime'},{name : 'loginSuccess'},{name : 'type'}]);
	var store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :context + '/sysmgr/loginlog.do?method=toList&rsCode=IMTI_SYS_LOGINLOG',
			method : "POST"
		}),
		reader : myReader
	});
	var grid = new Ext.grid.GridPanel({
		renderTo:"center",
		title:"<center>用户登录日志列表</center>",
		id: "contentGrid",
		height:window.screen.availHeight-210,
		store:store,
		viewConfig : {
			forceFit : 'true'
	    },
	    columns:[new Ext.grid.RowNumberer({width:30}),{
			header :"用户",dataIndex :'userName',sortable :true,width:100
		},{ header :"登录IP",dataIndex :'loginIP',sortable :true,width:120
		},{ header :"时间",dataIndex :'loginTime',sortable :true,width:120
		},{ header :"登录类型",dataIndex :'type',sortable :true,width:100,
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
				if(value == "0"){return "退出系统";}
				else if(value == "1"){return '<font color="Green">'+'登录系统'+'</font>';}
			}
		},{ header :"是否成功",dataIndex :'loginSuccess',width:100,
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
				if(value == "0"){return "失败";}
				else if(value == "1"){return '<font color="Green">'+'成功'+'</font>';}
			}
		}],
		tbar : ['&nbsp;','姓名：',{
					xtype : 'textfield',
					width : 120,
					id : 'searchName'
				},'时间区间：',{
					xtype: 'datefield',
					name : 'startTime',
					id:'startTime',
					width:100,
					format:'Y-m-d'
				},'至：',{
					xtype: 'datefield',
					name : 'endTime',
					id:'endTime',
					width:100,
					format:'Y-m-d'
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
		var searchName = Ext.getCmp("searchName").getValue();//按姓名搜索
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
    		operator : searchName,
			opeDateBegin : startTimeStr,
			opeDateEnd : endTimeStr
    	};
    }); 	
});