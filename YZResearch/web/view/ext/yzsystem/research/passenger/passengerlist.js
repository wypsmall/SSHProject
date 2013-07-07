Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = context + "/images/s.gif";
	Ext.QuickTips.init();
	var fm = Ext.form;
	
	var q_sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
	var r_sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
	var q_store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : context + '/yzsystem/research/passenger.do?method=getPassengers&rsCode=RCH_PASSENGER_PAGE_VIEW',
			method : 'POST'
		}),
		reader : new Ext.data.JsonReader({
					totalProperty : 'total',
					root : 'rows',
					id : "id"
				}, [{name : 'uid'},
					{name : 'uname'},
					{name : 'ucompany'},
					{name : 'usex'},
					{name : 'uroom'}]),
		autoLoad : true
	}); 
	var r_store = new Ext.data.Store({
		reader : new Ext.data.JsonReader({
					totalProperty : 'total',
					root : 'rows',
					id : "id"
				}, [{name : 'uid'},
					{name : 'uname'},
					{name : 'ucompany'},
					{name : 'usex'},
					{name : 'uroom'}])
	}); 
	function fsex(val) {
		if(val == 1)
			return "��";
		else
			return "Ů";
	}
	var q_grid = new Ext.grid.EditorGridPanel({
		id : 'passengerGrid',
		region : 'center',
		ddGroup : 'secondGridDDGroup',
		//frame : true,
		cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({width:30}),q_sm, {
			header : "����",dataIndex : "uname",width:80
			},{ header : "��˾",dataIndex : "ucompany",width:80
			},{ header : "�Ա�",dataIndex : "usex", renderer: fsex, width:80
			},{ header : "����",dataIndex : "uroom", sortable: true,width:80, 
				editor: new fm.TextField({
               		allowBlank: false
           		})
			}
		]),
		enableDragDrop   : true,
        //stripeRows       : true,
        //autoExpandColumn : 'uname',
		store : q_store,
		sm : q_sm,
		bodyStyle :'width:99.8%',
		height:window.screen.availHeight-210,
		width : window.screen.availWidth-212 ,
		autoWidth:true,
		//autoScroll:true,
		//externalFilters :true,
		tbar : ['','����ţ�',{xtype : 'textfield',width : 80,id : 'roomNo'},
		'->',
	        Ext.extend.SearchAction("��ѯ",context, function(){
	    		Ext.getCmp("passengerGrid").getStore().reload();
	    	}, function(e){
		    	checkRole(e,"RCH_PASSENGER_PAGE_VIEW");
		    })
		    ,Ext.extend.ConfigureAction("��������",context, function(){
	    		batchSaveRoomNo();
	    	}, function(e){
		    	checkRole(e,"RCH_PASSENGER_PAGE_SAVEROOMNO");
		    })
		    ,Ext.extend.SaveAction("����",context, function(){
	    		saveGroup();
	    	}, function(e){
		    	checkRole(e,"RCH_PASSENGER_PAGE_SAVEGROUP");
		    })
		    ],
		listeners:{
            "rowdblclick" : function(grid, rowIndex, e){

            }
        },
        bbar:  Ext.extend.PagingToolbar(q_store, 20)
	});
	var r_grid = new Ext.grid.GridPanel({
		id : 'rightGrid',
		region : 'east',
		ddGroup : 'firstGridDDGroup',
		enableDragDrop   : true,
        //stripeRows       : true,
        //autoExpandColumn : 'uname',
		cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({width:30}),r_sm, {
			header : "����",dataIndex : "uname",width:80
			},{ header : "��˾",dataIndex : "ucompany",width:80
			},{ header : "�Ա�",dataIndex : "usex", renderer: fsex, width:80
			},{ header : "����",dataIndex : "uroom", sortable: true,width:80}
		]),
		bodyStyle :'width:99.8%',
		height:window.screen.availHeight-210,
		width : 550 ,
		autoWidth:true,
		tbar : ['',
		'->',
	        Ext.extend.SearchAction("��ѯ",context, function(){
	    		Ext.getCmp("rightGrid").getStore().reload();
	    	}, function(e){
		    	checkRole(e,"RCH_PASSENGER_PAGE_VIEW");
		    })
		    ],
		store : r_store,
		sm : r_sm,
		listeners:{
            "rowdblclick" : function(grid, rowIndex, e){

            }
        },
        bbar:  Ext.extend.PagingToolbar(r_store, 20)
	});

	q_grid.getStore().on("beforeload", function() {
    	this.baseParams = {
    		parentId: ''
    	};
    }); 
    function batchSaveRoomNo() {
    	var groupArray = new Array();
    	var  recordlist =q_grid.getSelectionModel().getSelections();//��ȡ����
		for(var i=0; i<recordlist.length; i++)
		{
			var obj = new Object();
			obj.uid = recordlist[i].get("uid");//ȡ������
		    groupArray.push(obj);
		}

		var batchData = Ext.util.JSON.encode(groupArray);
		alert(batchData);
		/* ǰ̨û�л���Ȩ��ʱ��ʵʱ�����̨��֤Ȩ�� */
		Ext.Ajax.request({
			url : context + '/yzsystem/research/passenger.do',
			params : { 
				'method' : 'batchSaveRoomNo',
				'rsCode' : 'RCH_PASSENGER_PAGE_SAVEGROUP',
				'roomNo' : Ext.getCmp("roomNo").getValue(),
				'batchdata' : batchData },
			success : function(response, options) {
				Ext.getCmp("passengerGrid").getStore().reload();
			},
			failure : function(response, options) {

			}
		});
	}
    function saveGroup() {
    	var groupArray = new Array();
    	for (i = 0; i < q_store.getCount(); i++) {
			var record = q_store.getAt(i);
			if (record.dirty) {
//				var data = {};
//				data.uid = record.get('uid');
//				data.uroom = record.get('uroom');
				groupArray.push(record.data);
				//record.commit();
			}
		}
		var dirtyData = Ext.util.JSON.encode(groupArray);
		//alert(dirtyData);
		/* ǰ̨û�л���Ȩ��ʱ��ʵʱ�����̨��֤Ȩ�� */
		Ext.Ajax.request({
			url : context + '/yzsystem/research/passenger.do',
			params : { 
				'method' : 'saveGroup',
				'rsCode' : 'RCH_PASSENGER_PAGE_SAVEGROUP',
				'groupdata' : dirtyData },
			success : function(response, options) {
				Ext.getCmp("passengerGrid").getStore().reload();
			},
			failure : function(response, options) {

			}
		});
	}
	
	var q_viewPort = new Ext.Viewport({
		layout : 'border',
		items : [q_grid,r_grid]
	});	
	


	/****
	* Setup Drop Targets
	***/
	// This will make sure we only drop to the view container
	var firstGridDropTargetEl =  q_grid.getView().el.dom.childNodes[0].childNodes[1];
	var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
		ddGroup    : 'firstGridDDGroup',
		copy       : true,
		notifyDrop : function(ddSource, e, data){

			// Generic function to add records.
			function addRow(record, index, allItems) {
				q_store.add(record);
				ddSource.grid.store.remove(record);
			}

			// Loop through the selections
			Ext.each(ddSource.dragData.selections ,addRow);
			return(true);
		}
	});
	// This will make sure we only drop to the view container
	var secondGridDropTargetEl = r_grid.getView().el.dom.childNodes[0].childNodes[1]

	var destGridDropTarget = new Ext.dd.DropTarget(secondGridDropTargetEl, {
		ddGroup    : 'secondGridDDGroup',
		copy       : false,
		notifyDrop : function(ddSource, e, data){

			// Generic function to add records.
			function addRow(record, index, allItems) {
				r_store.add(record);
				ddSource.grid.store.remove(record);
			}
			// Loop through the selections
			Ext.each(ddSource.dragData.selections ,addRow);
			return(true);
		}
	});
});	