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
		title:"<center>ҵ����־��־�б�</center>",
		id: "contentGrid",
		height:window.screen.availHeight-210,
		store:store,
		viewConfig : {
			forceFit : 'true'
	    },
	    columns:[new Ext.grid.RowNumberer({width:30}),{
			header :"��ϵͳ",dataIndex :'rootModuleName',sortable :true,width:80
		},{ header :"ģ��",dataIndex :'secondModuleName',sortable :true,width:100
		},{ header :"��ģ��",dataIndex :'thirdModuleName',sortable :true,width:100
		},{ header :"��������",dataIndex :'operateName',sortable :true,width:60
		},{ header :"�ɹ���",dataIndex :'operateStatusName',width:50
		},{ header :"����˵��",dataIndex :'errMsg',width:250
		},{ header :"������",dataIndex :'operator',width:60
		},{ header :"����ʱ��",dataIndex :'operateTime',width:100
		}],
		tbar : ['����ʱ�䣺',{xtype: 'datefield',name : 'startTime',id:'startTime',width:90,format:'Y-m-d'
				},'����',{xtype: 'datefield',name : 'endTime',id:'endTime',width:90,format:'Y-m-d'
				},'��ϵͳ��',{xtype : 'textfield',width : 70,id : 'searchRootModuleName'
				},'ģ�飺',{	xtype : 'textfield',width : 70,id : 'searchSecondModuleName'
				},'��ģ�飺',{xtype : 'textfield',width : 70,id : 'searchThirdModuleName'
				},'��������',{xtype : 'textfield',width : 60,id : 'searchOperateName'
				},'�����ˣ�',{xtype : 'textfield',width : 60,id : 'searchOperator'
				},'->',
				Ext.extend.SearchAction("��ѯ",context, function(){
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
       			Ext.Msg.alert("��Ϣ","&nbsp;&nbsp;&nbsp;��ʼ���ڲ��ܴ��ڽ������ڣ�&nbsp;");
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