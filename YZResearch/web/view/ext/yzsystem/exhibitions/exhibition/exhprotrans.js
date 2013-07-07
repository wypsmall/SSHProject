function openExhProTransWin(){
	
	var store_detail = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy( {
			url : context + '/yzsystem/exhibitions/exhibition.do?method=getExhProTransList&rsCode=EXHMGR_PAGE_TRANSMGR',
			method :'POST'
		}),
   	 	reader :new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'rows'
			}, [{name : 'exhProModuleId'},{name : 'exhProId'},{name : 'charges'},{name : 'measurementUnits'},
			    {name : 'lockStatus'},{name : 'operator'},{name : 'operateDate'},{name : 'paramId'},
			    {name : 'paramValue'},{name : 'paramRemark'}]),
			    autoLoad : false	
	});
	var q_sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var colM_detail = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),q_sm,{
			header :"����",dataIndex :'paramValue',width : 120,sortable :true,
				renderer : function(value, cellmeta, record, rowIndex,columnIndex, store) {
					if(record.get("lockStatus") == 0 ){
						return "<font color='blue'>" + value + "</font>";
					}else if(record.get("lockStatus") == 1 ){
						return "<font color='red'>" + value + "</font>";
					}else {
						return value;
					}
				}
			},{ header :"����",dataIndex :'measurementUnits',width : 80
			},{ header :"��λ",dataIndex :'paramRemark',width : 80
			},{ header :"����(Ԫ)",dataIndex :'charges',width : 100
			},{ header :"������",dataIndex :'operator',width : 90
			},{ header :"����ʱ��",dataIndex :'operateDate',width : 90
		}]);
	/*�����б�*/
	var sysParamTransStore = new Ext.data.JsonStore({
		url : context + '/yzsystem/sysparam/sysparam.do?method=getExhProTransList&rsCode=EXHMGR_PAGE_TRANSMGR',
		root:'rows',
		totalProperty: 'total',
		fields:[{name:'paramId', mapping:'paramId'},{name:'paramValue', mapping:'paramValue'},{name:'paramRemark', mapping:'paramRemark'}]
	}); 
	var sysParamTransCmp = new Ext.form.ComboBox({
  		name:'sysParamTransCmp',
        id :'sysParamTransCmp',
        hiddenName:'paramId',
        displayField:'paramValue',
        valueField:'paramId',
        width : 80,
        editable:false,
        mode:'remote', 
        selectOnFocus: true,
        forceSelection : true,
        triggerAction:'all',
     	store: sysParamTransStore,
     	listeners:{'select':function(combo,record,index){ 
	        Ext.getCmp("paramRemark").setValue(record.get("paramRemark")); 
	   	}} 

	}); 
	var exhProTransGrid = new Ext.grid.GridPanel({
		id: "exhProTransGrid",
		autoScroll:true,
		bodyStyle :'width:99.8%',
		height:330,
		renderTo:'trans_module',
		sm : q_sm,
		store: store_detail,
	    cm :colM_detail,
	    tbar : ['���룺',sysParamTransCmp,'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
			'������',{xtype : 'textfield',width : 80,id : 'measurementUnits'
			},'��λ��',{xtype : 'textfield',width : 60,id : 'paramRemark'
			},'����(Ԫ)��',{xtype : 'textfield',width : 60,id : 'charges'
			},'->',
		Ext.extend.SearchAction("��ѯ",context, function(){
			var exhProId = getExhProId();
			if(exhProId =="" || undefined == exhProId){
				Ext.Msg.alert("��Ϣ","��ѡ��һ��չ����Ŀ");
				return ;
			}
			exhProTransGrid.getStore().reload();
	    }, function(e){
	    	checkRole(e,"EXHMGR_PAGE_TRANSMGR");
	    }),
		Ext.extend.SaveAction("����",context, function(){
			var exhProId = getExhProId();
			var sysParamTransCmpID = sysParamTransCmp.getValue();
			var measurementUnits = Ext.getCmp("measurementUnits").getValue();
			var charges = Ext.getCmp("charges").getValue();
			if(exhProId =="" || undefined == exhProId){Ext.Msg.alert("��Ϣ","��ѡ��һ��չ����Ŀ");return ;}
			if(sysParamTransCmpID == ""){alert("�������಻��Ϊ��");return ;}
			if(measurementUnits == ""){alert("��������Ϊ��");return ;}
			try{if(charges == "" || parseFloat(charges) == 0){alert("���ò���Ϊ��");return ;}}catch(e){alert("���ø�ʽ¼�벻��ȷ");return ;}
			Ext.Ajax.request({
				url : context + '/yzsystem/exhibitions/exhibition.do',
				params:{
					'mod.exhProId' : exhProId,
					'mod.paramId' : sysParamTransCmpID,
					'mod.measurementUnits' : measurementUnits,
					'mod.charges' : charges,
					method : 'saveExhProModule',
					rsCode : 'EXHMGR_PAGE_SAVETRANS'
				},
				success : function(response, options) {
					var responseObject = Ext.util.JSON.decode(response.responseText);
					Ext.Msg.alert("��Ϣ", responseObject.data);
					if(responseObject.success){
						exhProTransGrid.getStore().reload();
					}
				},
				failure : function(form, action) {
					Ext.Msg.alert("��Ϣ", action.result.data);
				}
			});
	    }, function(e){
	    	checkRole(e,"EXHMGR_PAGE_SAVETRANS");
	    }),
	    Ext.extend.DeleteAction(context, function(){
	    	var url = context + '/yzsystem/exhibitions/exhibition.do';
	    	var paramName = 'exhProModuleId';
	    	var acMethod = 'deleteExhProModule';
	    	var rsCode = 'EXHMGR_PAGE_DELETETRANS';
	    	function callBackFunc(){
	    		exhProTransGrid.getStore().reload();
	    	}
	    	operatePkAction(q_sm,'exhProModuleId','','', "", url, paramName,acMethod, rsCode,callBackFunc);
	    },function(e){
	    	checkRole(e,"EXHMGR_PAGE_DELETETRANS");
	    }),
	    Ext.extend.SubmitAction(context, function(){
	    	var url = context + '/yzsystem/exhibitions/exhibition.do';
	    	var paramName = 'exhProModuleId';
	    	var acMethod = 'submitExhProModule';
	    	var rsCode = 'EXHMGR_PAGE_LOCKTRANS';
	    	function callBackFunc(){
	    		exhProTransGrid.getStore().reload();
	    	}
	    	operatePkAction(q_sm,'exhProModuleId','','', "", url, paramName,acMethod, rsCode,callBackFunc);
	    },function(e){
	    	checkRole(e,"EXHMGR_PAGE_LOCKTRANS");
	    })]
	})
	store_detail.on("beforeload", function() {
		var exhProId = getExhProId();
    	store_detail.baseParams = {
    		exhProId : exhProId
    	};
    });
}