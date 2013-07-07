var operatorWin = null;//������
var addOpeAction = null;//��������
var delOpeAction = null;//ɾ������
var rsIdWithOperator = null;//���ز�������ԴID
var rsNameWithOperator = null;//���ز�������Դ����
var q_formPanel = null;//������
var ope_storeData = null;//�����б�
var ope_grid = null;//�����б�
function configRsOperator() {
	var sm = q_grid.getSelectionModel();
	var record = sm.getSelected();
	rsIdWithOperator = record.get("id"); //��ԴΨһ��ʶ
	rsNameWithOperator = record.get("name");
/**********************************�������******************************************/	
	addOpeAction = new Ext.Action({
		icon : context + '/images/icons/save.gif',
		text : '�����������',
		handler : function() {
			var form = q_formPanel.getForm();
			var name = form.findField("name").getValue();
			var code = form.findField("code").getValue();
			if(name == null || name==""){
				Ext.Msg.alert('��Ϣ', '����д�������ƣ�');
				return;
			}
			if(code == null || code==""){
				Ext.Msg.alert('��Ϣ', '����д�������룡');
				return;
			}
			if(rsIdWithOperator == null || rsIdWithOperator==""){
				Ext.Msg.alert('��Ϣ', '��Դ����Ϊ�գ�');
				return;
			}
			form.baseParams = {
				name : name,
				code : code,
				url : form.findField("url").getValue(),
				memo : form.findField("memo").getValue()
			}
			form.submit({
				waitMsg : "���Ժ�...",
				method : 'POST',
				url :context + '/sysmgr/resources.do?method=saveRsOpe&rsCode=IMTI_SYS_RESOURCE',
				success : function(form, action) {
					Ext.Msg.alert("��Ϣ","����ɹ�");
					ope_grid.getStore().reload();
					form.reset();
				},
				failure : function(form, action) {
					Ext.Msg.alert("��Ϣ",action.result.data);
				},
				params:{
					rsId : rsIdWithOperator
				}
			});	
		}
	});
/****************************�޸Ĳ������������ݵ����ڣ�*********************************************/	
	updOpeAction = new Ext.Action({
		icon : context + '/images/icons/ico03.png',
		text : '���޸Ĳ�����',
		handler : function() {
			fillModifyWin();
		}
	});
	function fillModifyWin(){
		var record = ope_grid.getSelectionModel().getSelected();
		var id = record.get("id"); //Ψһ��ʶ
		var code = record.get("code");//����
		var name = record.get("name");//����Դ����ģ�飩
		var memo = record.get("memo");//��ע
		var displayIndex = record.get("displayIndex");//˳���
		
		var mf = q_formPanel.getForm();
		mf.findField("id").setValue(id);
		mf.findField("name").setValue(name);
		mf.findField("memo").setValue(memo);
		mf.findField("code").setValue(code);
		mf.findField("displayIndex").setValue(displayIndex);
	}
/****************************ɾ������***************************************************/	
	delOpeAction = new Ext.Action({
		icon : context + '/images/icons/delete.png',
		text : '��ɾ��������',
		handler : function() {
			var array = ope_grid.getSelectionModel().getSelections();
			if (array.length != 0) {
				Ext.Msg.confirm('��Ϣ', '��ȷ��Ҫɾ����', function(btn) {
					if (btn == 'yes') {
						var i = 0;
						var opeIds = null;
						while (i != array.length) {
							if (opeIds == null) {
								opeIds = array[i].get("id");
							} else {
								opeIds = opeIds + "," + array[i].get("id");
							}
							i++;
						}
						Ext.Ajax.request({
							url : context + "/sysmgr/resources.do?method=deleteOprator&rsCode=IMTI_SYS_RESOURCE_CON_RS_OPE&opeIds=" + opeIds,
							success : function(form, action) {
								Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;ɾ���ɹ���&nbsp;");
								ope_grid.getStore().reload();
							},
							failure : function(form, action) {
								Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;ɾ��ʧ�ܣ�&nbsp;");
							}
						});
					}
				});
			} else {
				Ext.Msg.alert('��Ϣ', '��ѡ����Ҫɾ�����У�');
			}
		}
	});

/********************************��ʾ����***********************************************/
	if (!operatorWin) {
		createOperatorWin();
	}
	operatorWin.setTitle(rsNameWithOperator + '--�����б�');
	ope_grid.getStore().reload();
	operatorWin.show();
	
	function createOperatorWin() {
		var mainPanel = createMainPanel();
		operatorWin = new Ext.Window({
			mode: true,
			width: 600,
			closeAction: "hide",
			title : '' + rsNameWithOperator + '--�����б�',
			height: 400,
			items: mainPanel
		});
	}
	
	function createMainPanel() {
		createOpeGrid();
		var ope_formPanel = createOpePanel();
		var panel = new Ext.Panel({
			layout: "form",
			border: false,
			items: [{
				border: false,
				items: [ope_formPanel,ope_grid]
			}]
		});
		return panel;
	}
	function createOpePanel(){
		q_formPanel = new Ext.FormPanel({
			labelAlign : 'right',
			width : 600,
			buttonAlign : 'center',
			bodyStyle : 'padding:5px',
			frame : true,
			layout: "form",
			id : 'myForm',
			labelWidth : 80,
			items : [{
				xtype : 'hidden',
				anchor: '100%',
                name: "id"
			},{
				layout:'column',
				border:true,
		        defaults:{
			       	layout: 'form',
			       	border:false,
			       	columnWidth:.5
		        },
        		items:[{
	          		columnWidth:.37,
	            	items: [{
						xtype : 'textfield',
						fieldLabel : "����(����)",
						name : "name",
						maxLength:32,
						allowBlank : false
					}]
				},{
					columnWidth:.63,
					items: [{
						xtype : 'textfield',
						fieldLabel : "���(����)",
						name : "code",
						maxLength:32,
						width : 250,
						allowBlank : false
					}]
				}]
      		}, {
				layout:'column',
				border:true,
		        defaults:{
			       	layout: 'form',
			       	border:false,
			       	columnWidth:.5
		        },
        		items:[{
	          		columnWidth:.37,
	            	items: [{
						xtype : 'numberfield',
						fieldLabel : "˳���(����)",
						name : "displayIndex",
						maxLength:32,
						allowBlank : false
					}]
				},{
					columnWidth:.63,
					items: [{
						xtype : 'textfield',
						fieldLabel : "URL",
						name : "url",
						width : 250,
						maxLength:32,
						readOnly : false
					}]
				}]
      		}, {
				xtype : 'textarea',
				fieldLabel : "��ע",
				name : "memo",
				width : 465,
				height : 45,
				maxLength:32,
				readOnly : false
			}]
		});
		return q_formPanel;
	}
	function createOpeGrid() {
		ope_storeData = new Ext.data.JsonStore({
			url: context + '/sysmgr/resources.do?method=getOperatorList&rsCode=IMTI_SYS_RESOURCE' ,
			fields: ["id", "code","name", "rsId","displayIndex","url","memo"],
			root: "rows",
			totalProperty: "total"
		});
		//���ز����б�
		ope_grid = new Ext.grid.GridPanel({
	           height:235,
	           width : 580,
	           store: ope_storeData,
	           autoScroll : true,
	           columns:[new Ext.grid.RowNumberer(),{	
	           		header : "��������",
	           		autoWith : true,
	           		dataIndex :'name',
	           		width : 100,
	           		sortable :true
	           	},{
					header :"�������",
					autoWidth :true,
					width : 100,
					dataIndex :'code',
					sortable :true
				},{
					header :"��ԴID",
					autoWidth :true,
					hidden : true,
					dataIndex :'rsId',
					sortable :true
				},{
					header :"˳���",
					autoWidth :true,
					width : 80,
					dataIndex :'displayIndex',
					sortable :true
				},{
					header :"����URL",
					autoWidth :true,
					dataIndex :'url',
					sortable :true
				},{
					header :"��ע",
					autoWidth :true,
					dataIndex :'memo',
					sortable :true
				},{
					header :"Ψһ��ʶ",
					hidden : true,
					autoWidth :true,
					dataIndex :'id',
					sortable :true
				}],
	           tbar : [addOpeAction, updOpeAction, delOpeAction],
	           listeners:{
		            "rowdblclick" : function(grid, rowIndex, e){
		            	fillModifyWin();
		            }
		        }
	    }); 
	    ope_grid.getStore().on("beforeload", function() {
	    	this.baseParams = {
	    		rsId: rsIdWithOperator
	    	};
	    }); 	
	}
	
}