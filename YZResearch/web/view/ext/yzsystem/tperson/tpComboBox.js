//ʡ��
Ext.ux.PersonComboBox = function(ID) {
	var self = this;
	var unitCombRd = new Ext.data.JsonReader({
		//�ܼ�¼��
        totalProperty: 'total', 
        //�Ķ������ݵ�ͷ�����Կ�action��������ô�������ݸ�ʽ�ģ����������ν�����           
        root: 'rows', 
        //����Щ�ֶ��أ�
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
        		//Ext.getBody().mask("���ڼ�������.���Ե�...","x-mask-loading");
        	},
        	load : function(th, o, arg) {
        		//Ext.getBody().unmask(); 
        	}
		},
		reader:unitCombRd
	});
	Ext.ux.PersonComboBox.superclass.constructor.call(this,{
    		fieldLabel: '��Ա',
    	    id : ID,
    		store:unitCombDs,
		    displayField:'personName',
		    valueField:'personId',
		    editable:true,
		    readOnly: false,
		    typeAhead:false,
		    mode: 'local',
		    triggerAction: 'all',
		    emptyText:'������...',
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