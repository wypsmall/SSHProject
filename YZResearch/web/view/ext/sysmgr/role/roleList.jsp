<%@ page contentType="text/html;charset=GBK"%>
<html>
	<head>
	<%
		String context = request.getContextPath();
	%>
	<link rel="stylesheet" type="text/css" href="<%=context%>/ext3.0/resources/css/ext-all.css" />
	<script type="text/javascript" src="<%=context%>/ext3.0/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext-all.js"></script>
	<script language="javascript" src="<%=context%>/ext3.0/ext_extend.js"></script>
	<script type="text/javascript" src="<%=context%>/view/ext/sysmgr/role/addRole.js"></script>
	<script type="text/javascript" src="<%=context%>/view/ext/sysmgr/role/editRole.js"></script>
	<script type="text/javascript" src="<%=context%>/view/ext/sysmgr/role/deleteRole.js"></script>
	<style type="text/css">
		a:link,a:visited{
			text-decoration: none;
			color: #15428b;
		}
		a:hover,a:active{
			text-decoration: none;
			color: #005ffb;
		}
	</style>
</head>
<script type="text/javascript">
var context = "<%=context%>";
Ext.onReady(function(){ 
	Ext.BLANK_IMAGE_URL = "<%=context%>/images/s.gif";
	Ext.QuickTips.init(); 
	//查询
	var actionSearch= new Ext.Action({
		icon : context + '/images/icons/zoom.png',
		cls : 'x-btn-text-icon',
		text : '【查询】',
		handler : function() {
			  Ext.getCmp("roleGrid").getStore().reload(); 
		},
		listeners:{"render" : function(e){checkRole(e,"IMTI_SYS_ROLE_VIEW");}}
	});
	var actionADD = new Ext.Action({
		text : '【新增角色】',
		icon: context + '/images/icons/accept.png',
		handler : function() {
			openAddWin();
		},
		listeners:{"render" : function(e){checkRole(e,"IMTI_SYS_ROLE_INSERT");}}
	});
	var actionUpdate = new Ext.Action({
		text : '【修改角色】',
		icon: context + '/images/icons/ico03.png',
		handler : function() {
			var array = q_sm.getSelections();
			if (array.length == 1) {
				modifyWin();
			} else if(array.length == 0){
				Ext.Msg.alert('信息', '请选择你要修改的行！');
			} else if(array.length > 1){
				Ext.Msg.alert('信息', '请选择一条要修改的行！');
			}
		},
		listeners:{"render" : function(e){checkRole(e,"IMTI_SYS_ROLE_UPDATE");}}
	});
	var actionDelete = new Ext.Action({
		text : '【删除角色】',
		icon: context + '/images/icons/delete.png',
		handler : function() {
			var array = q_sm.getSelections();
			if (array.length != 0) {
				Ext.Msg.confirm('信息', '你确定要删除吗？', function(btn) {
					if (btn == 'yes') {
						deleteRole();
					}
				});
			} else {
				Ext.Msg.alert('信息', '请选择你要删除的行！');
			}
		},
		listeners:{"render" : function(e){checkRole(e,"IMTI_SYS_ROLE_DELETE");}}
	});
	
	var myReader = new Ext.data.JsonReader({
		root : "rows" 
	}, [{name : 'id'},{name : 'roleName'},{name : 'roleCode'},{name : 'roleMemo'},{name : 'ztId'}]);
	q_sm = new Ext.grid.CheckboxSelectionModel();	
	store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :context + '/sysmgr/sysmgr.do?method=getRoleList&rsCode=IMTI_SYS_ROLE_VIEW',
			method : "POST"
		}),
		reader : myReader
	});
	grid = new Ext.grid.GridPanel({
		renderTo:"center",
		title:"<center>角色列表</center>",
		autoWidth:true,
		id: "roleGrid",
		height:520,
		sm : q_sm,
		store:store,
		viewConfig : {
			forceFit : 'true'
	    },
	    columns:[q_sm,{
			header :"角色名称",
			autoWidth :true,
			dataIndex :'roleName',
			sortable :true
		},{
			header :"角色编码",
			autoWidth :true,
			dataIndex :'roleCode',
			sortable :true
		},{
			header :"备注",
			autoWidth :true,
			dataIndex :'roleMemo',
			sortable :true
		}],
		tbar : ['角色：',{xtype : 'textfield',width : 100,id : 'roleName'},'->',actionSearch, actionADD, actionUpdate, actionDelete,'&nbsp;'],
		listeners:{
	    	"rowdblclick" : function(grid, rowIndex, e){
	    		modifyWin();
	         }
	     }
	});		
	grid.getStore().on("beforeload", function() {
		 var roleName=Ext.get('roleName').dom.value;
    	this.baseParams = {
    		roleName: encodeURIComponent(roleName)
    	};
    }); 
});

function modifyWin(){
	var recordId = grid.selModel.selections.keys;
	if (recordId == '' || recordId == null) {
		Ext.MessageBox.alert('提示', '请选择数据记录');
		return ;
	}else if(recordId.toString().indexOf(",")!=-1){
		Ext.MessageBox.alert('提示', '每次只能修改一条记录!');
		return ;
	}
	role_id = recordId;
	openEditWin();
	Ext.getCmp("editForm").getForm().loadRecord(store.getById(recordId));
}
</script>
<body>
<div id="center"></div>
</body>
</html>