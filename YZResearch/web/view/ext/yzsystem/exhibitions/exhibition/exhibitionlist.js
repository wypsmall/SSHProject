Ext.onReady(function(){ 
	Ext.BLANK_IMAGE_URL = context + "/images/s.gif";
	Ext.QuickTips.init(); 
	var myReader = new Ext.data.JsonReader({
		root : "rows" 
	}, [{name : 'exhProId'},{name : 'exhProChzName'},{name : 'exhProNo'}]);
	store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :context + '/yzsystem/exhibitions/exhibition.do?method=getExhProList&rsCode=EXHMGR_PAGE',
			method : "POST"
		}),
		reader : myReader,
		autoLoad : true
	});
	var grid = new Ext.grid.GridPanel({
		region:"center",
		id: "exhProGrid",
		height:window.screen.availHeight-210,
		width : window.screen.availWidth-212 ,
		bodyStyle :'width:99.8%',
		autoWidth:true,
		store:store,
	    columns:[new Ext.grid.RowNumberer({width:30}),{
			header :"展会名称",dataIndex :'exhProChzName',sortable :true,width:120
		},{ header :"展会编码",dataIndex :'exhProNo',width:100
		}],
		tbar : ['展会名称：',{xtype : 'textfield',width : 120,id : 'searchExhProName'},
		    '->',
		    Ext.extend.SearchAction("查询",context, function(){
		    		Ext.getCmp("exhProGrid").getStore().reload();
		    	}, function(e){
		    	checkRole(e,"EXHMGR_PAGE");
		    }), 
		    '&nbsp;'],
	    listeners:{
            'rowclick':function(grid, rowIndex, e){
				var activTabId=exhEnterpriseTab.getActiveTab().id;
				var exhProId = getExhProId();
				if(exhProId =="" || undefined == exhProId){
					return ;
				}
				if(activTabId=="basic_tab"){
	          		
	          	}else if(activTabId=="trans_tab"){
	          		Ext.getCmp("exhProTransGrid").getStore().load();
	          	}else if(activTabId=="adv_tab"){
	          		Ext.getCmp("exhProAdvsGrid").getStore().load();
	          	}
			}
        },	
	     bbar:  Ext.extend.PagingToolbar(store, 20)
	});		
	grid.getStore().on("beforeload", function() {
    	this.baseParams = {
    		searchExhProName: Ext.getCmp("searchExhProName").getValue()
    	};
    }); 
	
	/*全局布局*/
	var exhEnterpriseTab = new Ext.TabPanel({
		id:'tabExhEnterprise',
		region:'south',
		height:350,
		deferredRender:false,
		activeTab:0,
		resizeTabs:true, // turn on tab resizing
		enableTabScroll:true,
		items:[{
			id:"basic_tab",
			title:'基础资料',
			minTabWidth: 50,
			html:'<div id="basic_content"></div>'
		},{
			id:"trans_tab",
			title:'翻译标准制定',
			html:'<div id="trans_module"></div>',
			listeners:{  
	  			activate:function(tab){
					var exhProId = getExhProId();
					if(exhProId =="" || undefined == exhProId){return ;}
					Ext.getCmp("exhProTransGrid").getStore().load();
				}
			}
		},{
			id:"adv_tab",
			title:'广告标准制定',
			html:'<div id="adv_module"></div>',
			listeners:{  
	  			activate:function(tab){
					var exhProId = getExhProId();
					if(exhProId =="" || undefined == exhProId){return ;}
					Ext.getCmp("exhProAdvsGrid").getStore().load();
				}
			}
		}]});
	var q_viewPort = new Ext.Viewport({
		layout : 'border',
		items : [grid,exhEnterpriseTab]
	});
	tabPermission(Ext.getCmp("trans_tab"),'EXHMGR_PAGE_TRANSMGR',openExhProTransWin());
	tabPermission(Ext.getCmp("trans_tab"),'EXHMGR_PAGE_ADVMGR',openExhProAdvsWin());
});
function tabPermission(tabEls,rsCode, func){
	var show = checkRole(tabEls,rsCode);
	if(!show){
		Ext.getCmp("tabExhEnterprise").remove(tabEls);
	}else {
		eval(func);
	}
}
function getExhProId(){
	var record=Ext.getCmp("exhProGrid").getSelectionModel().getSelected();
	if(record){
		var recordId = Ext.getCmp("exhProGrid").selModel.selections.keys;
		return store.getById(recordId).get("exhProId");
	}else {
		return "";
	}
}