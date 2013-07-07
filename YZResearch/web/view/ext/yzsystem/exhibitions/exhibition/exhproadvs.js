function openExhProAdvsWin(){

	var groupReader = new Ext.data.JsonReader({  
		root:'rows',   
		totalProperty:'total', 
		idProperty:'exhProModuleId',
		fields: [{name : 'exhProModuleId'},{name : 'exhProId'},{name : 'charges'},{name : 'measurementUnits'},
		    {name : 'lockStatus'},{name : 'operator'},{name : 'operateDate'},{name : 'paramId'},
		    {name : 'paramValue'},{name : 'paramName',type: 'string'},{name : 'paramRemark'}
		]
	});
	var store_detail = new Ext.data.GroupingStore({
		proxy : new Ext.data.HttpProxy( {
			url : context + '/yzsystem/exhibitions/exhibition.do?method=getExhProAdvList&rsCode=EXHMGR_PAGE_ADVMGR',
			method :'POST',
			disableCaching:true
		}),
   	 	reader : groupReader, 
   	 	remoteSort: true,
		sortInfo:{field: 'measurementUnits', direction: "ASC"},
		groupField:'paramName'
	});
	var editor = new Ext.ux.grid.RowEditor({
        saveText: '����',
        cancelText :'ȡ��'
    });
	editor.on({     
		afteredit : function(roweditor, changes, record, rowIndex) {
			var exhProId = getExhProId();
			var sysParamTransCmpID = record.get("paramId");
			var measurementUnits = record.get("measurementUnits");
			var charges = record.get("charges");
			if(measurementUnits == "" || parseFloat(charges) == 0){alert("��������Ϊ��");return ;}
			try{if(charges == "" || parseFloat(charges) == 0){alert("���ò���Ϊ��");return ;}}catch(e){alert("���ø�ʽ¼�벻��ȷ");return ;}
			if(record.get("lockStatus")==0){
				store_detail.reload();
				Ext.Msg.alert("��Ϣ", "������ύ��,�����޸�");
				return ;
			}
			if(exhProId =="" || undefined == exhProId){
				Ext.Msg.alert("��Ϣ","��ѡ��һ��չ����Ŀ");
				return ;
			}
			Ext.Ajax.request({
				url : context + '/yzsystem/exhibitions/exhibition.do',
				params:{
					'mod.exhProId' : exhProId,
					'mod.paramId' : sysParamTransCmpID,
					'mod.measurementUnits' : measurementUnits,
					'mod.charges' : charges,
					method : 'saveExhProModule',
					rsCode : 'EXHMGR_PAGE_SAVEADV'
				},
				success : function(response, options) {
					var responseObject = Ext.util.JSON.decode(response.responseText);
					Ext.Msg.alert("��Ϣ", responseObject.data);
					if(responseObject.success){
						exhProAdvsGrid.getStore().reload();
					}
				},
				failure : function(form, action) {
					Ext.Msg.alert("��Ϣ", action.result.data);
				}
			});
		},     
		canceledit : function(roweditor, changes, record, rowIndex) {         
			exhProAdvsGrid.getStore().reload(); 
		} 
	});
	var exhProAdvsGrid = new Ext.grid.GridPanel({
		id: "exhProAdvsGrid",
		bodyStyle :'width:99.8%',
		height:330,
		renderTo:'adv_module',
		view: new Ext.grid.GroupingView({
            forceFit:true,
            showGroupName: false,
            enableNoGroups:false,
			enableGroupingMenu:false,
            hideGroupedColumn: true,
            showGroupsText : "ddd"
        }),
        clicksToEdit: 1,
        collapsible: true,
        animCollapse: false,
        trackMouseOver: false,
        plugins: [editor],
		ds: store_detail,
		columns : [{
				header :"С��",dataIndex :'paramValue',width : 80,
				renderer : function(value, cellmeta, record, rowIndex,columnIndex, store) {
					if(record.get("lockStatus") == 0 ){
						return "<font color='blue'>" + value + "</font>";
					}else if(record.get("lockStatus") == 1 ){
						return "<font color='red'>" + value + "</font>";
					}else {
						return value;
					}
				}
			},{header :"��λ",dataIndex :'paramRemark',width : 60
			},{header :"����",dataIndex :'measurementUnits',width : 80,
				editor: {
	                xtype: 'textfield',
	                allowBlank: false
	            }
			},{header :"����(Ԫ)",dataIndex :'charges',width : 120,
				editor: new Ext.form.NumberField({
                    allowBlank: false,
                    allowNegative: false,
                    style: 'text-align:left'
                })
			},{header :"����",dataIndex :'paramName',width : 80,sortable :true
			},{header :"������",dataIndex :'operator',width : 100
			},{header :"����ʱ��",dataIndex :'operateDate',width : 100,sortable: true
		}]
	   
	});
	store_detail.on("beforeload", function() {
		var exhProId = getExhProId();
    	store_detail.baseParams = {
    		exhProId : exhProId,
    		exhProModuleCode : 'advertisement_param'
    	};
    });
}