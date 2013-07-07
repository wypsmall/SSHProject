function loadEmployeeEditData(){
	var record=Ext.getCmp("employeeGrid").getSelectionModel().getSelected();
	if (record) {
		submmitUrl="/yzsystem/hr/hrmgr.do?method=updateHrEmployee&rsCode=EMPMGR_PAGE_UPDATE";
		openEmployeeEditWin();
		Ext.getCmp("employeeEdiForm").getForm().loadRecord(record);
	}
}
/*
 * ��������
 */
function openEmployeeEditWin(){
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


	var employeeSexType=new Ext.form.RadioGroup({ 
		id : 'employeeSex', 
		fieldLabel : '�Ա�', 
		vertical : true, 
		width:80,
		columns : 2, 
		items : [{boxLabel:'��', name:'employeeSex',inputValue:'1',checked:true,width:30}, 
				 {boxLabel:'Ů', name:'employeeSex',inputValue:'2',width:30}]
	});
	var hasBuyShebaoType=new Ext.form.RadioGroup({ 
		id : 'hasBuyShebao', 
		fieldLabel : '�����籣', 
		vertical : true, 
		width:80,
		columns : 2, 
		items : [{boxLabel:'��', name:'hasBuyShebao',inputValue:'0',checked:true,width:30}, 
				 {boxLabel:'��', name:'hasBuyShebao',inputValue:'1',width:30}]
	});
	var employeeBirthType=new Ext.form.RadioGroup({ 
		id : 'employeeBirthType', 
		vertical : true, 
		width:100,
		columns : 2, 
		items : [{labelWidth :10,boxLabel:'ũ��', name:'employeeBirthType',inputValue:'1',checked:true,width:50}, 
				 {labelWidth :10,boxLabel:'����', name:'employeeBirthType',inputValue:'2',width:50}]
	});
	//״̬
	var statusData=[['1','ʵϰ��'],['2','������'],['3','��ʽְԱ'],['4','��ְ']];
	var statusStore = new Ext.data.SimpleStore({
		fields : ['returnValue','displayText'],
		data : statusData
	});
	var accountTypeData=[['1','��ط�ũ'],['2','���ũҵ'],['3','���ط�ũ'],['4','����ũҵ']];
	var accountTypeStore = new Ext.data.SimpleStore({
		fields : ['returnValue','displayText'],
		data : accountTypeData
	});
	
	var employeeEditWin = new Ext.Window({
		width : 650,
		height : 360,
		id : 'employeeEditWin',
		iconCls : 'window-edit',
		buttonAlign : 'center',
		title : 'ְԱ����',
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
			id : 'employeeEdiForm',
			labelWidth :50,
			items : [{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{
	          		columnWidth:.25,items: [{xtype : 'textfield',fieldLabel : "����",name : "employeeName",width:90,allowBlank : false}]
				},{columnWidth:.25,labelWidth :50,
					items: [{xtype : 'textfield',fieldLabel : "����",name : "employeeNo",width:90,allowBlank : false}]
				},{columnWidth:.25,labelWidth :50,
					items: [employeeSexType]
				},{columnWidth:.25,labelWidth :50,
					items: [{xtype : 'textfield',fieldLabel : "���֤",name : "identification",width:100}]
				}]
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{columnWidth:.25,labelWidth :50,
					items: [{xtype : 'datefield',fieldLabel : "����",name : "employeeBirth",width:90,format:'Y-m-d'}]
				},{columnWidth:.25,labelWidth :10,
					items: [employeeBirthType]
				},{columnWidth:.25,labelWidth :60,
					items: [hasBuyShebaoType]
				},{columnWidth:.25,labelWidth :50,
					items: [{xtype : 'combo',fieldLabel : "��&nbsp;&nbsp;��",name : "accountType",width:100,
						store : accountTypeStore,
						id : 'accountType',
						valueField:'returnValue',
						displayField:'displayText',
						mode:'local',
						editable:false,
						allowBlank : false,
						triggerAction : 'all',
						width : 100,
						emptyText : '--��ѡ��--'}]
				}]
	  		},{xtype : 'textfield',fieldLabel : "סַ",id : "homeAddress",width:565
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{columnWidth:.25,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "ְλ",name : "employeePostName",width:90}]
				},{columnWidth:.25,labelWidth :60,
					items: [{xtype : 'combo',fieldLabel : "��ְ״̬",name : "employeeState",width:90,
						store : statusStore,
						id : 'employeeState',
						valueField:'returnValue',
						displayField:'displayText',
						mode:'local',
						editable:false,
						allowBlank : false,
						triggerAction : 'all',
						width : 90,
						emptyText : '--��ѡ��--'}]
				},{columnWidth:.25,labelWidth :60,
					items: [{xtype : 'datefield',fieldLabel : "��ְ����",name : "entryJobDate",width:90,format:'Y-m-d'}]
				},{columnWidth:.25,labelWidth :60,
					items: [{xtype : 'datefield',fieldLabel : "��ְ����",name : "offJobDate",width:90,format:'Y-m-d'}]
				}]
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{columnWidth:.25,labelWidth :60,
					items: [{xtype : 'datefield',fieldLabel : "��ͬ����",name : "contractStartDate",width:90,format:'Y-m-d'}]
				},{columnWidth:.20,labelWidth :20,
					items: [{xtype : 'datefield',fieldLabel : "��",name : "contractEndDate",width:90,format:'Y-m-d'}]
				},{columnWidth:.3,labelWidth :70,
					items: [{xtype : 'textfield',fieldLabel : "������ϵ��",name : "contact",width:100}]
				},{columnWidth:.25,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "��ϵ�绰",name : "contactTel",width:90}]
				}]
	  		},{xtype : 'textfield',fieldLabel : "��ע",name : "memo",width:565
	  		},{
				layout:'column',
		        defaults:{layout: 'form'},
	    		items:[{columnWidth:.25,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "��ҵԺУ",name : "graduateSch",width:90}]
				},{columnWidth:.25,labelWidth :60,
					items: [{xtype : 'datefield',fieldLabel : "��ҵʱ��",name : "graduateDate",width:90,format:'Y-m'}]
				},{columnWidth:.25,labelWidth :60,
					items: [{xtype : 'textfield',fieldLabel : "ѧ��",name : "education",width:90}]
				},{columnWidth:.25,labelWidth :50,
					items: [{xtype : 'textfield',fieldLabel : "רҵ",name : "profession",width:100}]
				}]
	  		},{xtype : 'hidden',id : "employeeId"},{xtype : 'hidden',id : "orgId"},
	  		{xtype : 'hidden',id : "orgName"},{xtype : 'hidden',id : "orgParentId"},{xtype : 'hidden',id : "groupName"}]
		})],
		buttons:[{
			text : 'ȷ��',
			id:'btn_add',
			icon : context + '/images/icons/save.gif',
			handler : function() {
				var form = Ext.getCmp("employeeEditWin").getComponent("employeeEdiForm").getForm();
				
				form.baseParams = {
					"emp.employeeId" : form.findField("employeeId").getValue(),
					"emp.groupName" : form.findField("groupName").getValue(),
					"emp.orgParentId" : form.findField("orgParentId").getValue(),
					"emp.orgName" : form.findField("orgName").getValue(),
					"emp.orgId" : form.findField("orgId").getValue(),
					"emp.employeeName" : form.findField("employeeName").getValue(),
					"emp.employeeNo" : form.findField("employeeNo").getValue(),
					"emp.identification" : form.findField("identification").getValue(),
					"emp.employeeSex" : form.findField("employeeSex").getValue(),
					"emp.employeeBirth" : form.findField("employeeBirth").getValue(),
					"emp.employeeBirthType" : form.findField("employeeBirthType").getValue(),
					"emp.hasBuyShebao" : form.findField("hasBuyShebao").getValue(),
					"emp.accountType" : form.findField("accountType").getValue(),
					"emp.homeAddress" : form.findField("homeAddress").getValue(),
					"emp.employeePostName" : form.findField("employeePostName").getValue(),
					"emp.employeeState" : form.findField("employeeState").getValue(),
					"emp.entryJobDate" : form.findField("entryJobDate").getValue(),
					"emp.offJobDate" : form.findField("offJobDate").getValue(),
					"emp.contractStartDate" : form.findField("contractStartDate").getValue(),
					"emp.contractEndDate" : form.findField("contractEndDate").getValue(),
					"emp.contact" : form.findField("contact").getValue(),
					"emp.contactTel" : form.findField("contactTel").getValue(),
					"emp.memo" : form.findField("memo").getValue(),
					"emp.graduateSch" : form.findField("graduateSch").getValue(),
					"emp.graduateDate" : form.findField("graduateDate").getValue(),
					"emp.education" : form.findField("education").getValue(),
					"emp.profession" : form.findField("profession").getValue()
				};
				form.submit({
					waitMsg : "���Ժ�...",
					method : 'POST',
					url : context + submmitUrl,
					success : function(form, action) {
						Ext.getCmp("employeeGrid").getStore().reload();
						Ext.Msg.alert("��Ϣ",action.result.data);
						employeeEditWin.close();
						employeeEditWin=null;
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
				employeeEditWin.close();
				employeeEditWin=null;
			}
		}]
	});
	employeeEditWin.show();
}