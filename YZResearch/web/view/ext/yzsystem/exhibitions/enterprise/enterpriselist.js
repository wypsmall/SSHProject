Ext.onReady(function(){ 
	Ext.BLANK_IMAGE_URL = context + "/images/s.gif";
	Ext.QuickTips.init();
	
	var myReader = new Ext.data.JsonReader({
		root : "rows" 
	}, [{name : 'exhEnterpriseId'},{name : 'enterpriseName'},{name : 'exhProNo'}]);
	var q_sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});	
	store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :context + '/yzsystem/exhibitions/enterprise.do?method=listEnterprise&rsCode=ENTMGR_PAGE',
			method : "POST"          
		}),
		reader : myReader,
		autoLoad : true
	});
	var grid = new Ext.grid.GridPanel({
		region:"center",
		id: "exhEnterpriseProGrid",
		height:window.screen.availHeight-450,
		width : window.screen.availWidth-212 ,
		bodyStyle :'width:99.8%',
		autoWidth:true,
		sm : q_sm,
		store:store,
	    columns:[q_sm,{
			header :"企业名称",dataIndex :'enterpriseName',sortable :true,width:120
		},{ header :"展会编码",dataIndex :'exhProNo',width:100
		}],
		tbar : ['展会编码：',{xtype : 'textfield',width : 80,id : 'searchExhProNo'},
		    '->',
		    Ext.extend.SearchAction("查询",context, function(){
		    		Ext.getCmp("exhEnterpriseProGrid").getStore().reload();
		    	}, function(e){
		    	checkRole(e,"EXHMGR_PAGE");
		    }), 
		    '&nbsp;'],
	     bbar:  Ext.extend.PagingToolbar(store, 20)
	});		
	grid.getStore().on("beforeload", function() {
    	this.baseParams = {
    		exhProNo: Ext.getCmp("searchExhProNo").getValue()
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
			id:"exhSeat_tab",
			title:'展位管理',
			html:'<div id="exhSeat_content"></div>'
		},{
			id:"adv_tab",
			title:'广告管理',
			html:'<div id="adv_content"></div>'
		},{
			id:"exhibition_tab",
			title:'展具管理',
			html:'<div id="exhibition_content"></div>'
		},{
			id:"schedule_tab",
			title:'进度管理',
			html:'<div id="schedule_content"></div>'
	}]});
	var q_viewPort = new Ext.Viewport({
		layout : 'border',
		items : [grid,exhEnterpriseTab]
	});
	
	/*
	 * 通过权限来控制tab的显示
	 * exhEnterpriseTab.remove(Ext.getCmp("basic_tab"));
	 */
});
