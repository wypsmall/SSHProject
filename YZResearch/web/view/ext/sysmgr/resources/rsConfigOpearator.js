var operatorWin = null;//操作框
var addOpeAction = null;//新增操作
var delOpeAction = null;//删除操作
var rsIdWithOperator = null;//加载操作的资源ID
var rsNameWithOperator = null;//加载操作的资源名称
var q_formPanel = null;//操作表单
var ope_storeData = null;//操作列表
var ope_grid = null;//操作列表
function configRsOperator() {
	var sm = q_grid.getSelectionModel();
	var record = sm.getSelected();
	rsIdWithOperator = record.get("id"); //资源唯一标识
	rsNameWithOperator = record.get("name");
/**********************************保存操作******************************************/	
	addOpeAction = new Ext.Action({
		icon : context + '/images/icons/save.gif',
		text : '【保存操作】',
		handler : function() {
			var form = q_formPanel.getForm();
			var name = form.findField("name").getValue();
			var code = form.findField("code").getValue();
			if(name == null || name==""){
				Ext.Msg.alert('信息', '请填写操作名称！');
				return;
			}
			if(code == null || code==""){
				Ext.Msg.alert('信息', '请填写操作编码！');
				return;
			}
			if(rsIdWithOperator == null || rsIdWithOperator==""){
				Ext.Msg.alert('信息', '资源不能为空！');
				return;
			}
			form.baseParams = {
				name : name,
				code : code,
				url : form.findField("url").getValue(),
				memo : form.findField("memo").getValue()
			}
			form.submit({
				waitMsg : "请稍侯...",
				method : 'POST',
				url :context + '/sysmgr/resources.do?method=saveRsOpe&rsCode=IMTI_SYS_RESOURCE',
				success : function(form, action) {
					Ext.Msg.alert("信息","保存成功");
					ope_grid.getStore().reload();
					form.reset();
				},
				failure : function(form, action) {
					Ext.Msg.alert("信息",action.result.data);
				},
				params:{
					rsId : rsIdWithOperator
				}
			});	
		}
	});
/****************************修改操作（加载数据到窗口）*********************************************/	
	updOpeAction = new Ext.Action({
		icon : context + '/images/icons/ico03.png',
		text : '【修改操作】',
		handler : function() {
			fillModifyWin();
		}
	});
	function fillModifyWin(){
		var record = ope_grid.getSelectionModel().getSelected();
		var id = record.get("id"); //唯一标识
		var code = record.get("code");//编码
		var name = record.get("name");//父资源（父模块）
		var memo = record.get("memo");//备注
		var displayIndex = record.get("displayIndex");//顺序号
		
		var mf = q_formPanel.getForm();
		mf.findField("id").setValue(id);
		mf.findField("name").setValue(name);
		mf.findField("memo").setValue(memo);
		mf.findField("code").setValue(code);
		mf.findField("displayIndex").setValue(displayIndex);
	}
/****************************删除操作***************************************************/	
	delOpeAction = new Ext.Action({
		icon : context + '/images/icons/delete.png',
		text : '【删除操作】',
		handler : function() {
			var array = ope_grid.getSelectionModel().getSelections();
			if (array.length != 0) {
				Ext.Msg.confirm('信息', '你确定要删除吗？', function(btn) {
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
								Ext.Msg.alert("信息", "&nbsp;&nbsp;&nbsp;删除成功！&nbsp;");
								ope_grid.getStore().reload();
							},
							failure : function(form, action) {
								Ext.Msg.alert("信息", "&nbsp;&nbsp;&nbsp;删除失败！&nbsp;");
							}
						});
					}
				});
			} else {
				Ext.Msg.alert('信息', '请选择你要删除的行！');
			}
		}
	});

/********************************显示窗口***********************************************/
	if (!operatorWin) {
		createOperatorWin();
	}
	operatorWin.setTitle(rsNameWithOperator + '--操作列表');
	ope_grid.getStore().reload();
	operatorWin.show();
	
	function createOperatorWin() {
		var mainPanel = createMainPanel();
		operatorWin = new Ext.Window({
			mode: true,
			width: 600,
			closeAction: "hide",
			title : '' + rsNameWithOperator + '--操作列表',
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
						fieldLabel : "名称(必填)",
						name : "name",
						maxLength:32,
						allowBlank : false
					}]
				},{
					columnWidth:.63,
					items: [{
						xtype : 'textfield',
						fieldLabel : "编号(必填)",
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
						fieldLabel : "顺序号(必填)",
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
				fieldLabel : "备注",
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
		//加载操作列表
		ope_grid = new Ext.grid.GridPanel({
	           height:235,
	           width : 580,
	           store: ope_storeData,
	           autoScroll : true,
	           columns:[new Ext.grid.RowNumberer(),{	
	           		header : "操作名称",
	           		autoWith : true,
	           		dataIndex :'name',
	           		width : 100,
	           		sortable :true
	           	},{
					header :"操作编号",
					autoWidth :true,
					width : 100,
					dataIndex :'code',
					sortable :true
				},{
					header :"资源ID",
					autoWidth :true,
					hidden : true,
					dataIndex :'rsId',
					sortable :true
				},{
					header :"顺序号",
					autoWidth :true,
					width : 80,
					dataIndex :'displayIndex',
					sortable :true
				},{
					header :"操作URL",
					autoWidth :true,
					dataIndex :'url',
					sortable :true
				},{
					header :"备注",
					autoWidth :true,
					dataIndex :'memo',
					sortable :true
				},{
					header :"唯一标识",
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