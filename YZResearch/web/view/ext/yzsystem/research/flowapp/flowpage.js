Ext.onReady(function(){ 
	Ext.BLANK_IMAGE_URL = context + "/images/s.gif";
	Ext.QuickTips.init(); 
	
	var myReader = new Ext.data.JsonReader({
		root : "rows" 
	}, [{name : 'flwId'},
		{name : 'flwCode'},
		{name : 'flwTitle'},
		{name : 'flwRemark'},
		{name : 'flwProposerId'},
		{name : 'flwProposerName'},
		{name : 'flwAuditorId'},
		{name : 'flwAuditorName'},
		{name : 'flwReply'},
		{name : 'flwStatus'},
		{name : 'inTime'},
		{name : 'modTime'}]);
	var submitStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :context + '/yzsystem/research/appflow.do?method=getAppFlows&rsCode=RCH_APPFLOW_PAGE_FIND_FLOWS',
			method : "POST"
		}),
		reader : myReader,
		autoLoad : true
	});
	var submitGrid = new Ext.grid.GridPanel({
		region:"center",
		id: "submitGrid",
		height:window.screen.availHeight-210,
		width : window.screen.availWidth-212 ,
		bodyStyle :'width:99.8%',
		autoWidth:true,
		store:submitStore,
	    columns:[new Ext.grid.RowNumberer({width:30}),{
			header :"申请人",dataIndex :'flwProposerName',sortable :true,width:120
		},{ header :"审批人",dataIndex :'flwAuditorName',sortable :true,width:120
		},{ header :"标题",dataIndex :'flwTitle',sortable :true,width:120
		},{ header :"状态",dataIndex :'flwStatus',renderer: rdFlowStatus,width:100
		}],
		tbar : ['',
		    '->',
		    Ext.extend.SearchAction("查询",context, function(){
		    		Ext.getCmp("submitGrid").getStore().reload();
		    	}, function(e){
		    	checkRole(e,"RCH_APPFLOW_PAGE_FIND_FLOWS");
		    }), 
		   	Ext.extend.SubmitAction(context, function(){
		    	submmitUrl="/yzsystem/research/appflow.do?method=submitAppFlows&rsCode=RCH_APPFLOW_PAGE_SUBMIT_FLOW";
		    	openFlowEditWin(0);
		    }, function(e){
			    	checkRole(e,"RCH_APPFLOW_PAGE_SUBMIT_FLOW");
		    }), 
		    '&nbsp;'],
	    listeners:{
            'rowdblclick':function(grid, rowIndex, e){
				//Ext.getCmp("personGrid").getStore().reload();
            	loadFlowEditData(1);
			}
        },	
	     bbar:  Ext.extend.PagingToolbar(submitStore, 20)
	});		
	submitGrid.getStore().on("beforeload", function() {
    	this.baseParams = {
    		sFlag : 0
    	};
    }); 
    

	var replyStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :context + '/yzsystem/research/appflow.do?method=getAppFlows&rsCode=RCH_APPFLOW_PAGE_FIND_FLOWS',
			method : "POST"
		}),
		reader : myReader,
		autoLoad : true
	});
	var replyGrid = new Ext.grid.GridPanel({
		region:'east',
		id: "replyGrid",
		height:window.screen.availHeight-210,
		width:650,
		bodyStyle :'width:99.8%',
		autoWidth:true,
		store:replyStore,
	    columns:[new Ext.grid.RowNumberer({width:30}),{
			header :"申请人",dataIndex :'flwProposerName',sortable :true,width:120
		},{ header :"审批人",dataIndex :'flwAuditorName',sortable :true,width:120
		},{ header :"标题",dataIndex :'flwTitle',sortable :true,width:120
		},{ header :"状态",dataIndex :'flwStatus',renderer: rdFlowStatus,width:100
		}],
		tbar : ['',
		    '->',
		    Ext.extend.SearchAction("查询",context, function(){
		    		Ext.getCmp("replyGrid").getStore().reload();
		    	}, function(e){
		    	checkRole(e,"RCH_APPFLOW_PAGE_FIND_FLOWS");
		    }),
		   	Ext.extend.SubmitAction(context, function(){
		    	submmitUrl="/yzsystem/research/appflow.do?method=replyAppFlows&rsCode=RCH_APPFLOW_PAGE_REPLY_FLOW";
		    	loadFlowEditData(2);
		    }, function(e){
			    	checkRole(e,"RCH_APPFLOW_PAGE_REPLY_FLOW");
		    }), 
		    '&nbsp;'],
	    listeners:{
            'rowdblclick':function(grid, rowIndex, e){
            	submmitUrl="/yzsystem/research/appflow.do?method=replyAppFlows&rsCode=RCH_APPFLOW_PAGE_REPLY_FLOW";
				loadFlowEditData(2);
			}
        },	
	     bbar:  Ext.extend.PagingToolbar(replyStore, 20)
	});		
	replyGrid.getStore().on("beforeload", function() {
    	this.baseParams = {
    		sFlag : 1
    	};
    }); 

	var q_viewPort = new Ext.Viewport({
		layout : 'border',
		items : [submitGrid,replyGrid]
	});


});

function getSubmitId() {
	var record=Ext.getCmp("submitGrid").getSelectionModel().getSelected();
	if(record){
		return record.get('flwId');
	}else {
		return "";
	}	
}
function getReplyId() {
	var record=Ext.getCmp("replyGrid").getSelectionModel().getSelected();
	if(record){
		return record.get('flwId');
	}else {
		return "";
	}		
}


function rdFlowStatus(val) {
	if(val == 1)
		return "待审批";
	else if(val == 2)
		return "审批通过";
	else if(val == 3)
		return "驳回";
	else if(val == 4)
		return "撤回";
	else
		return "未知";
}
