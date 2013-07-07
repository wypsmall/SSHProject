function loadPersonEditData(){
	var record=Ext.getCmp("personGrid").getSelectionModel().getSelected();
	if (record) {
		submmitUrl="/yzsystem/research/abroadperson.do?method=updateAP&rsCode=RCH_ABROAD_PERSON_PAGE_UPDATE_PERSON";
		openPersonEditWin();
		Ext.getCmp("personEditForm").getForm().loadRecord(record);
	}
}
/*
 * 部门资料
 */
function openPersonEditWin(){
	Ext.override(Ext.form.RadioGroup, {   
	    getValue: function(){  
	        var v;  
	        if (this.rendered) {  
	            this.items.each(function(item){  
	                if (!item.getValue())   
	                    return true;  
	                v = item.getRawValue();  
	                return false;  
	            });  
	        }  
	        else {  
	            for (var k in this.items) {  
	                if (this.items[k].checked) {  
	                    v = this.items[k].inputValue;  
	                    break;  
	                }  
	            }  
	        }  
	        return v;  
	    },  
	    setValue: function(v){  
	        if (this.rendered)   
	            this.items.each(function(item){  
	                item.setValue(item.getRawValue() == v);  
	            });  
	        else {  
	            for (var k in this.items) {  
	                this.items[k].checked = this.items[k].inputValue == v;  
	            }  
	        }  
	    }  
	}); 


	var apSexType=new Ext.form.RadioGroup({ 
		id : 'apsex', 
		fieldLabel : '性别', 
		vertical : true, 
		width:160,
		columns : 2, 
		items : [{boxLabel:'男', name:'apSex',inputValue:'1',checked:true,width:30}, 
				 {boxLabel:'女', name:'apSex',inputValue:'0',width:30}]
	});
	var apmstateType=new Ext.form.RadioGroup({ 
		id : 'apmstate', 
		fieldLabel : '婚姻', 
		vertical : true, 
		width:160,
		columns : 2, 
		items : [{boxLabel:'未婚', name:'apmstate',inputValue:'0',checked:true,width:30}, 
				 {boxLabel:'已婚', name:'apmstate',inputValue:'1',width:30}]
	});
	var appassType=new Ext.form.RadioGroup({ 
		id : 'appasstype', 
		fieldLabel : '护照类型', 
		vertical : true, 
		width:160,
		columns : 2, 
		items : [{boxLabel:'因私护照', name:'appasstype',inputValue:'0',checked:true,width:30}, 
				 {boxLabel:'公务护照', name:'appasstype',inputValue:'1',width:30}]
	});
	//状态
	var aptriptypeData=[['0','展期随团'],['1','全程随团'],['2','特殊']];
	var aptriptypeStore = new Ext.data.SimpleStore({
		fields : ['returnValue','displayText'],
		data : aptriptypeData
	});
	var apdepartureData=[['0','香港'],['1','广州'],['2','上海']];
	var apdepartureStore = new Ext.data.SimpleStore({
		fields : ['returnValue','displayText'],
		data : apdepartureData
	});
	var aproomtypeData=[['0','VIP'],['1','双床'],['2','大床']];
	var aproomtypeStore = new Ext.data.SimpleStore({
		fields : ['returnValue','displayText'],
		data : aproomtypeData
	});	
	var personEditWin = new Ext.Window({
		width : 650,
		height : 360,
		id : 'personEditWin',
		iconCls : 'window-edit',
		buttonAlign : 'center',
		title : '拟派出国人员登记表',
		plain : true,
		closable : true,
		frame : true,
		modal : true,
		items : [new Ext.form.FormPanel({
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true,
			height : 360,
			region : 'center',
			id : 'personEditForm',
			labelWidth :100,
			items : [{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{
	          		columnWidth:.5,items: [{xtype : 'textfield',fieldLabel : "姓名",name : "apname",width:120,allowBlank : false}]
				},{
					columnWidth:.5,items: [{xtype : 'textfield',fieldLabel : "拼音",name : "apspell",width:120,allowBlank : false}]
				}]
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{
	    			columnWidth:.5, items: [apSexType]
				},{
					columnWidth:.5, items: [apmstateType]
				}]
	  		},{xtype : 'textfield', fieldLabel : "住址",id : "apaddress",width:400
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{
	          		columnWidth:.5,items: [{xtype : 'textfield',fieldLabel : "职务",name : "apduties",width:120,allowBlank : false}]
				},{
					columnWidth:.5,items: [{xtype : 'textfield',fieldLabel : "出生日期",name : "apbirthday",width:120,allowBlank : false}]
				}]
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{
	          		columnWidth:.5,items: [{xtype : 'textfield',fieldLabel : "电话",name : "aptel",width :120,allowBlank : false}]
				},{
					columnWidth:.5,items: [{xtype : 'textfield',fieldLabel : "出生省市",name : "apborn",width :120,allowBlank : false}]
				}]
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{
	          		columnWidth:.5,items: [{xtype : 'textfield',fieldLabel : "手机",name : "apmobile",width:120,allowBlank : false}]
				},{
					columnWidth:.5,items: [{xtype : 'textfield',fieldLabel : "传真",name : "apfax",width:120,allowBlank : false}]
				}]
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{
	          		columnWidth:.5,items: [{xtype : 'textfield',fieldLabel : "身份证号",name : "apcardno",width:120,allowBlank : false}]
				},{
					columnWidth:.5,items: [appassType]
				}]
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{
	          		columnWidth:.5,items: [{xtype : 'textfield',fieldLabel : "护照号",name : "appassno",width:120,allowBlank : false}]
				},{
					columnWidth:.5,items: [{xtype : 'textfield',fieldLabel : "护照有效期",name : "appassdate",width:120,allowBlank : false}]
				}]
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{columnWidth:.5,
					items: [{xtype : 'combo',fieldLabel : "行程类型",name : "aptriptype",width:120,
						store : aptriptypeStore,
						id : 'aptriptype',
						valueField:'returnValue',
						displayField:'displayText',
						mode:'local',
						editable:false,
						allowBlank : false,
						triggerAction : 'all',
						emptyText : '--请选择--'}]
				},{columnWidth:.5,
					items: [{xtype : 'combo',fieldLabel : "出发地",name : "apdeparture",width:120,
						store : apdepartureStore,
						id : 'apdeparture',
						valueField:'returnValue',
						displayField:'displayText',
						mode:'local',
						editable:false,
						allowBlank : false,
						triggerAction : 'all',
						emptyText : '--请选择--'}]
				}]
	  		},{xtype : 'combo',fieldLabel : "房间类型",name : "aproomtype",width:120,
						store : aproomtypeStore,
						id : 'aproomtype',
						valueField:'returnValue',
						displayField:'displayText',
						mode:'local',
						editable:false,
						allowBlank : false,
						triggerAction : 'all',
						emptyText : '--请选择--'				
	  		},
	  			{xtype : 'hidden',id : "apid"},
	  			{xtype : 'hidden',id : "cid", value : getCompanyId()},
	  			{xtype : 'hidden',id : "cname"},
	  			{xtype : 'hidden',id : "eid", value : getExhibitionId()},
	  			{xtype : 'hidden',id : "ename"},
	  			{xtype : 'hidden',id : "eno"}]
		})],
		buttons:[{
			text : '确定',
			id:'btn_add',
			icon : context + '/images/icons/save.gif',
			handler : function() {
				var form = Ext.getCmp("personEditWin").getComponent("personEditForm").getForm();
				
				form.baseParams = {
					"apVO.apid" : form.findField("apid").getValue(),
					"apVO.cid" : form.findField("cid").getValue(),
					"apVO.cname" : form.findField("cname").getValue(),
					"apVO.eid" : form.findField("eid").getValue(),
					"apVO.ename" : form.findField("ename").getValue(),
					"apVO.eno" : form.findField("eno").getValue(),
					
					"apVO.apaddress" : form.findField("apaddress").getValue(),
					"apVO.apname" : form.findField("apname").getValue(),
					"apVO.apspell" : form.findField("apspell").getValue(),
					"apVO.apsex" : form.findField("apsex").getValue(),
					"apVO.apmstate" : form.findField("apmstate").getValue(),
					"apVO.apduties" : form.findField("apduties").getValue(),
					"apVO.apbirthday" : form.findField("apbirthday").getValue(),
					"apVO.apborn" : form.findField("apborn").getValue(),
					"apVO.aptel" : form.findField("aptel").getValue(),
					
					"apVO.apmobile" : form.findField("apmobile").getValue(),
					"apVO.apfax" : form.findField("apfax").getValue(),
					"apVO.apcardno" : form.findField("apcardno").getValue(),
					"apVO.appasstype" : form.findField("appasstype").getValue(),
					"apVO.appassno" : form.findField("appassno").getValue(),
					"apVO.appassdate" : form.findField("appassdate").getValue(),
					"apVO.aptriptype" : form.findField("aptriptype").getValue(),
					"apVO.apdeparture" : form.findField("apdeparture").getValue(),
					"apVO.aproomtype" : form.findField("aproomtype").getValue()
				};
				form.submit({
					waitMsg : "请稍侯...",
					method : 'POST',
					url : context + submmitUrl,
					success : function(form, action) {
						Ext.getCmp("personGrid").getStore().reload();
						Ext.Msg.alert("信息",action.result.data);
						personEditWin.close();
						personEditWin=null;
					},
					failure : function(form, action) {
						Ext.Msg.alert("信息",action.result.data);
					}
				});
			}
		},{
			text : '关闭',
			icon : context + '/images/icons/delete.gif',
			handler : function() {
				personEditWin.close();
				personEditWin=null;
			}
		}]
	});
	personEditWin.show();
}