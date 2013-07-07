function groupEmployee(){
	var record=Ext.getCmp("employeeGrid").getSelectionModel().getSelected();
	if (record) {
		if(record.get("orgId")!=""){
			Ext.Msg.show({
				title: '��ʾ',
				msg: '��ְԱ�Ѿ�����',
				modal: false,
				width:200,
				icon: Ext.Msg.INFO,
				buttons: {yes:'ȷ��'}
			});
		}else {
			var store_detail = new Ext.data.Store({
				proxy :new Ext.data.HttpProxy( {
					url : context + '/yzsystem/hr/hrmgr.do?method=getOrgList&rsCode=EMPMGR_PAGE_GROUP',
					method :'POST'
				}),
		   	 	reader :new Ext.data.JsonReader({
					totalProperty : 'total',
					root : 'rows'
					}, [{name : 'orgId'},{name : 'orgName'},{name : 'orgParentId'},{name : 'orgParentName'}]),
				autoLoad : true
			});
			var colM_detail = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({width:25}),{
					header :"��������",dataIndex :'orgParentId',width : 100,sortable :true,
						renderer : function(value, cellmeta, record, rowIndex,columnIndex, store) {
								if(value=="root"){
									return record.get("orgName");
								}else {
									return record.get("orgParentName");
								}
						}
					},{ header : "����",dataIndex : "orgParentId",width:100,
						renderer : function(value, cellmeta, record, rowIndex,columnIndex, store) {
							if(value=="root"){
								return 'δ����';
							}else {
								return record.get("orgName");
							}
						}
				}]);
			var addWin_detail = new Ext.Window({
				width : 300,
				height : 235,
				id : 'addWin_detail',
				title : record.get("employeeName") + '['+record.get("employeeNo")+ ']<font color="red">----ѡ�����ݣ�˫����ɷ���----</font>',
				items : [new Ext.grid.GridPanel({
					id: "contentGrid_detail",
					autoScroll:true,
					bodyStyle :'width:99.8%',
					height:200,
					store: store_detail,
				    cm :colM_detail,
					listeners:{
			            "rowdblclick" : function(grid, rowIndex, e){
			            	e.stopEvent() ;
			            	var orgRecord=Ext.getCmp("contentGrid_detail").getSelectionModel().getSelected();
			            	Ext.Ajax.request({
			        			url : context + '/yzsystem/hr/hrmgr.do?method=groupEmployee&rsCode=EMPMGR_PAGE_GROUP',
			        			params : {
			        				"orgId" : orgRecord.get("orgId"),
			        				"employeeId" : record.get("employeeId")
			        			},
			        			success : function(resp) {
			        				var responseObj = Ext.util.JSON.decode(resp.responseText);
			        				
			        				Ext.Msg.show({
			        					title: '�ر�',
			        					msg: responseObj.data,
			        					modal: false,
			        					width:200,
			        					icon: Ext.Msg.INFO,
			        					buttons: {yes:'��', no: '��'},
			        					fn: function(btn){
			        			            if(btn == 'yes'){
			        			            	if(responseObj.success == false){
						        					return;
						        				}else {
						        					
						        				}
						        				addWin_detail.close();
								            	addWin_detail = null;
			        			            }
			        					}
			        				});
			        			},
			        			failure : function(form, action) {
			        				Ext.Msg.show({
			        					title: '������ʾ',
			        					msg: '����ʧ��',
			        					modal: false,
			        					width:200,
			        					icon: Ext.Msg.ERROR,
			        					buttons: {yes:'ȷ��'}
			        				});
			        			}
			        		});
			            }
			        }
				})],
				closable : true
			});
		    addWin_detail.show();
		}
	}else {
		Ext.Msg.show({
			title: '��ʾ',
			msg: '��ѡ��ְԱ����',
			modal: false,
			width:200,
			icon: Ext.Msg.INFO,
			buttons: {yes:'ȷ��'}
		});
	}
}