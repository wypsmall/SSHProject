Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = context + "/images/s.gif";
	Ext.QuickTips.init();
	
	var personCombox = new Ext.ux.PersonComboBox('tpComboBox'); 
	personCombox.width = 150;
	var q_sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var q_store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : context + '/yzsystem/study/tperson.do?method=getTPerson&rsCode=TPERSON_PAGE_VIEW',
			method : 'POST'
		}),
		reader : new Ext.data.JsonReader({
					totalProperty : 'total',
					root : 'rows',
					idProperty : "personId"
				}, [{name : 'personId'},{name : 'personName'},{name : 'inTime'},{name : 'modTime'}]),
		autoLoad : true
	}); 
	var editor = new Ext.ux.grid.RowEditor({
        saveText: '保存',
        cancelText :'取消'
    });
    editor.on({     
		afteredit : function(roweditor, changes, record, rowIndex) {
			var personId = record.get("personId");
			var personName = record.get("personName");
			Ext.Ajax.request({
				url : context + '/yzsystem/study/tperson.do',
				params:{
					'personId' : personId,
					'personName' : personName,
					method : 'upateTPerson',
					rsCode : 'TPERSON_PAGE_MODIFY'
				},
				success : function(response, options) {
					var responseObject = Ext.util.JSON.decode(response.responseText);
					Ext.Msg.alert("信息", responseObject.data);
					if(responseObject.success){
						record.commit();
					} else {
						record.set("personName",record.modified.personName);
						record.commit();
					}
				},
				failure : function(form, action) {
					Ext.Msg.alert("信息", action.result.data);
				}
			});
		},
/*		validateedit : function(roweditor, changes, record, rowIndex) {         
			var personId = record.get("personId");
			var personName = changes.personName;
			var isValidate = false;
			Ext.Ajax.request({
				url : context + '/yzsystem/study/tperson.do',
				params:{
					'personId' : personId,
					'personName' : personName,
					method : 'upateTPerson',
					rsCode : 'TPERSON_PAGE_MODIFY'
				},
				success : function(response, options) {
					var responseObject = Ext.util.JSON.decode(response.responseText);
					Ext.Msg.alert("信息", responseObject.data);
//					if(responseObject.success){
//						q_store.reload();
//					}
					isValidate = true;
				},
				failure : function(form, action) {
					isValidate = false;
					Ext.Msg.alert("信息", action.result.data);
				}
			});
			//由于是异步所以isValidate始终为false
			return isValidate;
		}, */
		canceledit : function(roweditor, changes, record, rowIndex) {         
			alert("根本没有此事件");
		} 
	});
	var q_grid = new Ext.grid.GridPanel({
		id : 'tpersonGrid',
		renderTo : 'center',
		plugins: [editor],
		//viewConfig:{markDirty:false},
		cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({width:30}), {
				header : "Id",dataIndex : "personId",width:80,editor: { xtype: 'textfield', readOnly : true }
			},{ header : "人员名称",dataIndex : "personName",width:80,editor: { xtype: 'textfield'}
			},{ header : "入库时间",dataIndex : "inTime",width:80,editor: { xtype: 'textfield'}
			},{ header : "修改时间",dataIndex : "modTime",width:80,editor: { xtype: 'textfield'}
			}
		]),
		bodyStyle :'width:99.8%',
		height:window.screen.availHeight-210,
		width : window.screen.availWidth-212 ,
		autoScroll:true,
		//externalFilters :true,
		tbar : ['',personCombox,
			'->',
	        Ext.extend.SearchAction("查询",context, function(){
	        	//alert(Ext.getCmp("tpComboBox").getValue());
	    		//Ext.getCmp("tpersonGrid").getStore().reload();
	    	}, function(e){
		    	checkRole(e,"TPERSON_PAGE_VIEW");
		    })],
		store : q_store,
/*		listeners:{
            "rowdblclick" : function(grid, rowIndex, e){

            }
        },*/
        bbar:  Ext.extend.PagingToolbar(q_store, 20)
	});
	

});	