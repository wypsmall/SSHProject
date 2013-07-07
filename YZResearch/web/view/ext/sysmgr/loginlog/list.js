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
		title:"<center>�û���¼��־�б�</center>",
		id: "contentGrid",
		height:window.screen.availHeight-210,
		store:store,
		viewConfig : {
			forceFit : 'true'
	    },
	    columns:[new Ext.grid.RowNumberer({width:30}),{
			header :"�û�",dataIndex :'userName',sortable :true,width:100
		},{ header :"��¼IP",dataIndex :'loginIP',sortable :true,width:120
		},{ header :"ʱ��",dataIndex :'loginTime',sortable :true,width:120
		},{ header :"��¼����",dataIndex :'type',sortable :true,width:100,
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
				if(value == "0"){return "�˳�ϵͳ";}
				else if(value == "1"){return '<font color="Green">'+'��¼ϵͳ'+'</font>';}
			}
		},{ header :"�Ƿ�ɹ�",dataIndex :'loginSuccess',width:100,
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
				if(value == "0"){return "ʧ��";}
				else if(value == "1"){return '<font color="Green">'+'�ɹ�'+'</font>';}
			}
		}],
		tbar : ['&nbsp;','������',{
					xtype : 'textfield',
					width : 120,
					id : 'searchName'
				},'ʱ�����䣺',{
					xtype: 'datefield',
					name : 'startTime',
					id:'startTime',
					width:100,
					format:'Y-m-d'
				},'����',{
					xtype: 'datefield',
					name : 'endTime',
					id:'endTime',
					width:100,
					format:'Y-m-d'
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
		var searchName = Ext.getCmp("searchName").getValue();//����������
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
    		operator : searchName,
			opeDateBegin : startTimeStr,
			opeDateEnd : endTimeStr
    	};
    }); 	
});