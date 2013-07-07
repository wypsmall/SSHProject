Ext.onReady(function(){ 
	Ext.BLANK_IMAGE_URL = context + "/images/s.gif";
	Ext.QuickTips.init(); 
	var myReader = new Ext.data.JsonReader({
		root : "rows" 
	}, [{name : 'ecid'},{name : 'cid'},{name : 'cname'},{name : 'eid'},{name : 'ename'},{name : 'eno'}]);
	var store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :context + '/yzsystem/research/abroadperson.do?method=listExhCom&rsCode=RCH_ABROAD_PERSON_PAGE_FIND_EXHCOM',
			method : "POST"
		}),
		reader : myReader,
		autoLoad : true
	});
	var grid = new Ext.grid.GridPanel({
		region:"center",
		id: "exhcomGrid",
		height:window.screen.availHeight-210,
		width : window.screen.availWidth-212 ,
		bodyStyle :'width:99.8%',
		autoWidth:true,
		store:store,
	    columns:[new Ext.grid.RowNumberer({width:30}),{
			header :"展会编码",dataIndex :'eno',sortable :true,width:120
		},{ header :"展会名称",dataIndex :'ename',sortable :true,width:120
		},{ header :"企业名称",dataIndex :'cname',width:100
		}],
		tbar : ['展会代码：',{xtype : 'textfield',width : 90,id : 'sr_eno'},
				'企业名称：',{xtype : 'textfield',width : 90,id : 'sr_cname'},
		    '->',
		    Ext.extend.SearchAction("查询",context, function(){
		    		Ext.getCmp("exhcomGrid").getStore().reload();
		    	}, function(e){
		    	checkRole(e,"RCH_ABROAD_PERSON_PAGE_FIND_EXHCOM");
		    }), 
		    '&nbsp;'],
	    listeners:{
            'rowdblclick':function(grid, rowIndex, e){
				Ext.getCmp("personGrid").getStore().reload();
			}
        },	
	     bbar:  Ext.extend.PagingToolbar(store, 20)
	});		
	grid.getStore().on("beforeload", function() {
    	this.baseParams = {
    		sr_eno: Ext.getCmp("sr_eno").getValue(),
    		sr_cname: Ext.getCmp("sr_cname").getValue()
    	};
    }); 
    
    var personReader = new Ext.data.JsonReader({
		root : "rows" 
	}, [{name : 'apid'},
		{name : 'cid'},
		{name : 'cname'},
		{name : 'eid'},
		{name : 'ename'},
		{name : 'eno'},
		
		{name : 'apaddress'},
		{name : 'apname'},
		{name : 'apspell'},
		{name : 'apsex'},
		{name : 'apmstate'},
		{name : 'apduties'},
		{name : 'apbirthday'},
		{name : 'apborn'},
		{name : 'aptel'},
		
		{name : 'apmobile'},
		{name : 'apfax'},
		{name : 'apcardno'},
		{name : 'appasstype'},
		{name : 'appassno'},
		{name : 'appassdate'},
		{name : 'aptriptype'},
		{name : 'apdeparture'},
		{name : 'aproomtype'}
		]);
	var personstore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :context + '/yzsystem/research/abroadperson.do?method=listAbroadPersons&rsCode=RCH_ABROAD_PERSON_PAGE_FIND_PERSON',
			method : "POST"
		}),
		reader : personReader,
		autoLoad : true
	});
	var persongrid = new Ext.grid.GridPanel({
		region:'east',
		id: "personGrid",
		height:window.screen.availHeight-210,
		width:750,
		bodyStyle :'width:99.8%',
		autoWidth:true,
		store:personstore,
	    columns:[new Ext.grid.RowNumberer({width:30}),{
			header :"企业名称",dataIndex :'cname',sortable :true,width:120
		},{ header :"姓名",dataIndex :'apname',sortable :true,width:120
		},{ header :"性别",dataIndex :'apsex',renderer: rdsex, sortable :true,width:120
		},{ header :"电话",dataIndex :'aptel',sortable :true,width:120
		},{ header :"手机",dataIndex :'apmobile',sortable :true,width:120
		},{ header :"行程类型",dataIndex :'aptriptype',renderer: rdtrip,sortable :true,width:120
		},{ header :"离港城市",dataIndex :'apdeparture',renderer: rddeparture,sortable :true,width:120
		},{ header :"住宿标准",dataIndex :'aproomtype',renderer: rdroom,width:100
		}],
		tbar : ['姓名：',{xtype : 'textfield',width : 90,id : 'sr_apname'},
			{xtype : 'checkbox',boxLabel: '性别', id : 'exp_sex'},
			{xtype : 'checkbox',boxLabel: '手机', id : 'exp_mobile'},
			{xtype : 'checkbox',boxLabel: '电话', id : 'exp_tel'},
		    '->',
		    Ext.extend.ConfigureAction("导出",context, function(){
	    		//alert(Ext.getCmp("exp_sex").getValue() + " " + Ext.getCmp("exp_mobile").getValue() + " " + Ext.getCmp("exp_tel").getValue())
	    		var condition = "&exp_sex="+Ext.getCmp("exp_sex").getValue()+"&exp_mobile="+Ext.getCmp("exp_mobile").getValue()+"&exp_tel="+Ext.getCmp("exp_tel").getValue();
	    		//alert(condition);
	    		expDown.location.href=context + "/yzsystem/research/abroadperson.do?method=exportAP&rsCode=RCH_ABROAD_PERSON_PAGE_EXPORT"+condition;
	    	
	    	}, function(e){
		    	checkRole(e,"RCH_ABROAD_PERSON_PAGE_EXPORT");
		    })
		    ,Ext.extend.SearchAction("查询",context, function(){
		    		Ext.getCmp("personGrid").getStore().reload();
		    	}, function(e){
		    	checkRole(e,"RCH_ABROAD_PERSON_PAGE_FIND_PERSON");
		    }),
		    Ext.extend.AddAction(context, function(){
		    	submmitUrl="/yzsystem/research/abroadperson.do?method=insertAP&rsCode=RCH_ABROAD_PERSON_PAGE_INSERT_PERSON";
		    	openPersonEditWin();
		    }, function(e){
			    	checkRole(e,"RCH_ABROAD_PERSON_PAGE_INSERT_PERSON");
		    }), 
		    Ext.extend.UpdateAction(context, function(){
		    	loadPersonEditData();
		    }, function(e){
		    	checkRole(e,"RCH_ABROAD_PERSON_PAGE_UPDATE_PERSON");
		    }), 
		    '&nbsp;'],
	    listeners:{
            'rowclick':function(grid, rowIndex, e){
				/*var activTabId=exhEnterpriseTab.getActiveTab().id;
				var exhProId = getExhProId();
				if(exhProId =="" || undefined == exhProId){
					return ;
				}
				if(activTabId=="basic_tab"){
	          		
	          	}else if(activTabId=="trans_tab"){
	          		Ext.getCmp("exhProTransGrid").getStore().load();
	          	}else if(activTabId=="adv_tab"){
	          		Ext.getCmp("exhProAdvsGrid").getStore().load();
	          	}*/
			},
			"rowdblclick" : function(grid, rowIndex, e){
				if(parent && parent.permissionMap && parent.permissionMap.get("RCH_ABROAD_PERSON_PAGE_UPDATE_PERSON")){
					loadPersonEditData();
				 }
            }
        },	
	     bbar:  Ext.extend.PagingToolbar(personstore, 20)
	});		
	persongrid.getStore().on("beforeload", function() {
    	this.baseParams = {
    		sr_eid: getExhibitionId(),
    		sr_cid: getCompanyId()
    	};
    }); 

	var q_viewPort = new Ext.Viewport({
		layout : 'border',
		items : [grid,persongrid]
	});


});

function getCompanyId() {
	var record=Ext.getCmp("exhcomGrid").getSelectionModel().getSelected();
	if(record){
		return record.get('cid');
	}else {
		return "";
	}	
}
function getExhibitionId() {
	var record=Ext.getCmp("exhcomGrid").getSelectionModel().getSelected();
	if(record){
		return record.get('eid');
	}else {
		return "";
	}		
}

function rdsex(val) {
	if(val == 1)
		return "男";
	else
		return "女";
}
function rdtrip(val) {
	if(val == 0)
		return "展期随团";
	else if(val == 1)
		return "全程随团";
	else
		return "特殊";
}
function rddeparture(val) {
	if(val == 0)
		return "香港";
	else if(val == 1)
		return "广州";
	else
		return "上海";
}
function rdroom(val) {
	if(val == 0)
		return "VIP";
	else if(val == 1)
		return "双床";
	else
		return "大床";
}