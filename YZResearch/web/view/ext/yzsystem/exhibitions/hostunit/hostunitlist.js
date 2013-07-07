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
		title:"<center>���쵥λ�б�</center>",
		id: "hostunitGrid",
		height:window.screen.availHeight-210,
		width : window.screen.availWidth-212 ,
		bodyStyle :'width:99.8%',
		autoWidth:true,
		sm : q_sm,
		store:store,
	    columns:[q_sm,{
			header :"��λ����",dataIndex :'hostUnitName',sortable :true,width:120
		},{ header :"��������",dataIndex :'postalcode',width:100
		},{ header :"ͨѶ��ַ",dataIndex :'address',width:150
		},{ header :"��ϵ��",dataIndex :'contact',width:90
		},{ header :"ְ��",dataIndex :'postName',width:80
		},{ header :"��ϵ�ֻ�",dataIndex :'telephone',width:120
		},{ header :"��ϵ����",dataIndex :'contactEmail',width:120
		},{ header :"�칫�绰",dataIndex :'officeTel',width:120
		},{ header :"����绰",dataIndex :'officeFax',width:120
		},{ header :"��˾��ַ",dataIndex :'webSite',width:150
		},{ header :"��ע",dataIndex :'remark',width:150
		},{ header :"¼��Ա",dataIndex :'enterUser',width:90
		},{ header :"¼��ʱ��",dataIndex :'enterDate',width:90
		},{ header :"�޸���",dataIndex :'lastModifyUser',width:90
		},{ header :"�޸�ʱ��",dataIndex :'lastModifyDate',width:90
		}],
		tbar : ['���쵥λ��',{xtype : 'textfield',width : 120,id : 'searchHostUnitName'},
			    '¼��Ա��',{xtype : 'textfield',width : 80,id : 'searchEnterUser'},
			    '����޸��ˣ�',{xtype : 'textfield',width : 80,id : 'searchLastModifyUser'},
		    '->',
		    Ext.extend.SearchAction("��ѯ",context, function(){
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