Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = context + "/images/s.gif";
	Ext.QuickTips.init();
	
	var q_sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var q_store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : context + '/yzsystem/hr/hrmgr.do?method=getOrgListByParentId&rsCode=ORGMGR_PAGE_VIEW',
			method : 'POST'
		}),
		reader : new Ext.data.JsonReader({
					totalProperty : 'total',
					root : 'rows',
					id : "orgId"
				}, [{name : 'orgId'},{name : 'orgName'},{name : 'orgCode'},{name : 'orgParentId'},{name : 'delFlag'},
				    {name : 'memo'},{name : 'address'},{name : 'tel'},{name : 'fax'},{name : 'orgParentName'}]),
		autoLoad : true
	});
	
	var q_grid = new Ext.grid.EditorGridPanel({
		id : 'orgGrid',
		region : 'center',
		fram : true,
		cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({width:30}),q_sm, {
				header : "��������",dataIndex : "orgName",width:100
			},{ header : "���ű���",dataIndex : "orgCode",width:80
			},{ header : "��ϵ�绰",dataIndex : "tel",width:100
			},{ header : "����",dataIndex : "fax",width:100
			},{ header : "��ַ",dataIndex : "address",width:250
			},{ header : "��ע",dataIndex : "memo",width:250
			}
		]),
		bodyStyle :'width:99.8%',
		autoScroll:true,
		externalFilters :true,
		tbar : ['','->',
		    Ext.extend.AddAction(context, function(){
		    	submmitUrl="/yzsystem/hr/hrmgr.do?method=insertHrOrg&rsCode=ORGMGR_PAGE_INSERT";
		    	openOrgEditWin();
		    }, function(e){
			    	checkRole(e,"ORGMGR_PAGE_INSERT");
		    }), 
		    Ext.extend.UpdateAction(context, function(){
		    	loadOrgEditData();
		    }, function(e){
		    	checkRole(e,"ORGMGR_PAGE_UPDATE");
		    }), 
		    Ext.extend.DeleteAction(context, function(){
		    	var url = context + '/yzsystem/hr/hrmgr.do';
		    	var paramName = 'orgId';
		    	var acMethod = 'deleteHrOrg';
		    	var rsCode = 'ORGMGR_PAGE_DELETE';
		    	function callBackFunc(){
		    		q_grid.getStore().load();
		    	}
		    	operatePkAction(q_sm,'orgId','','', "", url, paramName,acMethod, rsCode,callBackFunc);
		    },function(e){
		    	checkRole(e,"ORGMGR_PAGE_DELETE");
		    })],
		store : q_store,
		sm : q_sm,
		listeners:{
            "rowdblclick" : function(grid, rowIndex, e){
				if(parent && parent.permissionMap && parent.permissionMap.get("ORGMGR_PAGE_UPDATE")){
					loadOrgEditData();
				 }
            }
        }
	});
	
	q_grid.getStore().on("beforeload", function() {
    	this.baseParams = {
    		parentId: q_OrgId
    	};
    }); 
	    
	var root = new Ext.tree.AsyncTreeNode({
		id : "root",
		icon : context + '/images/icons/house.png',
		text : imtiSysName,
		leaf : false
	});
	/*
	*��Դ��
	*/
	q_treePanel = new Ext.tree.TreePanel({
		region : "west",
		root : root,
		width : 170,
		tbar : [new Ext.Action({
			text : '��ȫ��չ����',
			handler : function() {
				Ext.getCmp("orgTree").expandAll();
			}
		}), new Ext.Action({
			text : '��ȫ����£��',
			handler : function() {
				Ext.getCmp("orgTree").collapseAll();
			}
		})],
		region : 'west',
		xtype : 'treepanel',
		id : 'orgTree',
		collapsible : true,
		split : true,
		useArrows : true,
		autoScroll : true,
		animate : true,
		containerScroll : true,
		loader : new Ext.tree.TreeLoader({
			dataUrl : context + '/yzsystem/hr/hrmgr.do?method=getOrgTree&rsCode=ORGMGR_PAGE_VIEW'
		}),
		listeners : {
			click : function(node, e) {
				q_OrgId = node.id;
				q_orgName = node.text;
				q_grid.getStore().reload();
				q_grid.setTitle("���Ŷ���");
			}
		}
	});
	//����ҳ�沼��
	q_viewPort = new Ext.Viewport({
		layout : 'border',
		items : [q_treePanel, q_grid]
	});
});	