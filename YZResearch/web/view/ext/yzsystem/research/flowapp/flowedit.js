function loadFlowEditData(type){ //1-�鿴��2-���
	var record = null;
	if(type == 1)
		record = Ext.getCmp("submitGrid").getSelectionModel().getSelected();
	else if(type == 2)
		record = Ext.getCmp("replyGrid").getSelectionModel().getSelected();
		
	if (record) {
		openFlowEditWin(type);
		Ext.getCmp("flowEditForm").getForm().loadRecord(record);
	}
}
/*
 * ��������
 */
function openFlowEditWin(type){ //0-������1-�鿴��2-���
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


	//״̬
	var flowStatusData=[['1','�����'],['2','���ͨ��'],['3','����']];
	var flowStatusStore = new Ext.data.SimpleStore({
		fields : ['returnValue','displayText'],
		data : flowStatusData
	});
	var userCombox = null;
	var isEdit = false;
	if(type == 0) {
		isEdit = false;
		userCombox = new Ext.ux.UserComboBox('flwAuditorId'); 
	} else {
		isEdit = true;
		userCombox = new Ext.form.TextField({fieldLabel : "�����",name : "flwAuditorName",width:120,readOnly : isEdit})
	}
	userCombox.width = 150;
	var flowEditWin = new Ext.Window({
		width : 650,
		height : 300,
		id : 'flowEditWin',
		iconCls : 'window-edit',
		buttonAlign : 'center',
		title : '���������',
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
			id : 'flowEditForm',
			labelWidth :100,
			items : [{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{
	          		columnWidth:.5,items: [{xtype : 'textfield',fieldLabel : "���",name : "flwCode",width:120,allowBlank : false,readOnly : isEdit}]
				},{
					columnWidth:.5,items: [{xtype : 'textfield',fieldLabel : "����",name : "flwTitle",width:120,allowBlank : false,readOnly : isEdit}]
				}]
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{columnWidth:.5,
					items: [{xtype : 'combo',fieldLabel : "״̬",name : "flwStatus",width:120,
						store : flowStatusStore,
						id : 'flwStatus',
						valueField:'returnValue',
						displayField:'displayText',
						mode:'local',
						editable:false,
						allowBlank : false,
						triggerAction : 'all',
						emptyText : '--��ѡ��--'}]
				},{columnWidth:.5,
					items: [userCombox]
				}]
	  		},{xtype : 'textarea', fieldLabel : "��������",id : "flwRemark",width:400,readOnly : isEdit
	  		},{xtype : 'textarea', fieldLabel : "��������",id : "flwReply",width:400,readOnly : !isEdit
	  		},
	  			{xtype : 'hidden',id : "flwId"},
	  			{xtype : 'hidden',id : "flwProposerId"},
	  			{xtype : 'hidden',id : "flwProposerName"},
	  			{xtype : 'hidden',id : "flwAuditorName"},
	  			{xtype : 'hidden',id : "flwReply"}]
		})],
		buttons:[{
			text : 'ȷ��',
			id:'btn_add',
			icon : context + '/images/icons/save.gif',
			handler : function() {
				var form = Ext.getCmp("flowEditWin").getComponent("flowEditForm").getForm();
				
				form.baseParams = {
					"flowAppVO.flwId" : form.findField("flwId").getValue(),
					"flowAppVO.flwCode" : form.findField("flwCode").getValue(),
					"flowAppVO.flwTitle" : form.findField("flwTitle").getValue(),
					"flowAppVO.flwRemark" : form.findField("flwRemark").getValue(),
					"flowAppVO.flwProposerId" : form.findField("flwProposerId").getValue(),
					"flowAppVO.flwProposerName" : form.findField("flwProposerName").getValue(),
					
					"flowAppVO.flwAuditorId" : form.findField("flwAuditorId").getValue(),
					"flowAppVO.flwAuditorName" : form.findField("flwAuditorName").getValue(),
					"flowAppVO.flwReply" : form.findField("flwReply").getValue(),
					"flowAppVO.flwStatus" : form.findField("flwStatus").getValue()

				};
				form.submit({
					waitMsg : "���Ժ�...",
					method : 'POST',
					url : context + submmitUrl,
					success : function(form, action) {
						Ext.getCmp("replyGrid").getStore().reload();
						Ext.Msg.alert("��Ϣ",action.result.data);
						flowEditWin.close();
						flowEditWin=null;
					},
					failure : function(form, action) {
						Ext.Msg.alert("��Ϣ",action.result.data);
					}
				});
			}
		},{
			text : '�ر�',
			icon : context + '/images/icons/delete.gif',
			handler : function() {
				flowEditWin.close();
				flowEditWin=null;
			}
		}]
	});
	flowEditWin.show();
}