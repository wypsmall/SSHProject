//省份
Ext.ux.PersonComboBox = function(ID) {
	var self = this;
	var unitCombRd = new Ext.data.JsonReader({
		//总记录数
        totalProperty: 'total', 
        //哪儿是数据的头，可以看action里面是怎么定义数据格式的，这里就是如何解析的           
        root: 'rows', 
        //有那些字段呢？
        fields:[
                 {name:'personId',mapping:'personId',type:'string'},
                 {name:'personName',mapping:'personName',type:'string'}
                 ]
	});
	var unitCombDs = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url : context + '/yzsystem/study/tperson.do?method=getTPerson&rsCode=TPERSON_PAGE_VIEW',
			method : 'POST'
		}),
		listeners : {
			beforeload:function(th, o) {
        		//Ext.getBody().mask("正在加载数据.请稍等...","x-mask-loading");
        	},
        	load : function(th, o, arg) {
        		//Ext.getBody().unmask(); 
        	}
		},
		reader:unitCombRd
	});
	Ext.ux.PersonComboBox.superclass.constructor.call(this,{
    		fieldLabel: '人员',
    	    id : ID,
    		store:unitCombDs,
		    displayField:'personName',
		    valueField:'personId',
		    editable:true,
		    readOnly: false,
		    typeAhead:false,
		    mode: 'local',
		    triggerAction: 'all',
		    emptyText:'请输入...',
		    listeners:{
		    	beforequery : function(event) {
		    		if("" == self.getRawValue())
		    			return;
		    		unitCombDs.on("beforeload", function() {
							 this.baseParams = {
								sr_personName : self.getRawValue()
					    	};
					    }); 
						unitCombDs.load();
		    	},
				change:function(th,newValue,oldValue ){
/*					
					if(oldValue != newValue) {
						self.clearValue();
						unitCombDs.on("beforeload", function() {
							 this.baseParams = {
								sr_personName : newValue
					    	};
					    }); 
						unitCombDs.load();
					}*/
				}
	    	}
	});
};

Ext.extend(Ext.ux.PersonComboBox, Ext.form.ComboBox);