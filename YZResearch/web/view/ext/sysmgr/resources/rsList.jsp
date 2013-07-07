<%@ page language="java" contentType="text/html; charset=GB2312"%>
<%@ page import="java.util.*,com.imti.framework.component.vopomapping.utils.PropertyUtils"%>
<html>
<head>
<%
	String context = request.getContextPath();
	String imtiSysName=PropertyUtils.getProperty("imti.sys.name","管理平台");
%>
<link rel="stylesheet" type="text/css" href="<%=context%>/ext3.0/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=context%>/ext3.0/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=context%>/ext3.0/ext-all.js"></script>
<script type="text/javascript" src="<%=context%>/view/ext/sysmgr/resources/rsAddModule.js"></script>
<script type="text/javascript" src="<%=context%>/view/ext/sysmgr/resources/rsDelModule.js"></script>
<script type="text/javascript" src="<%=context%>/view/ext/sysmgr/resources/rsModifyModule.js"></script>
<script type="text/javascript" src="<%=context%>/view/ext/sysmgr/resources/rsConfigOpearator.js"></script>
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
<title>模块定义</title>
</head>
<body>
<script type="text/javascript">
var context = "<%=context%>";
var q_viewPort = null;//页面布局
var q_treePanel = null;//资源树
var q_moduleId = null;//被选中的资源ID
var q_moduleName = null;//被选中的资源名称
var q_store = null;//资源数据
var q_grid = null;//资源Grid
var q_formPanel = null;//新增模块
var q_sm = null;
var addAction = null;
var modifyAction = null;
var delAction = null;
var modifyModule_formPanel = null;
var configAction = null;
var isLevelTwo = false;
Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = "<%=context%>/images/s.gif";
	Ext.QuickTips.init();
	addAction = new Ext.Action({
		icon : '<%=context%>/ext3.0/resources/images/default/dd/drop-add.gif',
		cls : 'x-btn-text-icon',
		text : '【新增模块】',
		handler : function() {
			if (q_moduleId == null || q_moduleId == "") {
				Ext.Msg.alert("信息", "&nbsp;&nbsp;&nbsp;请选择模块！&nbsp;");;
				return;
			}
			addModule();
		}
	});
	modifyAction = new Ext.Action({
		icon : '<%=context%>/images/icons/ico03.png',
		cls : 'x-btn-text-icon',
		text : '【修改模块】',
		handler : function() {
			modifyRsModule();
		}
	});
	delAction = new Ext.Action({
		icon : '<%=context%>/images/icons/delete.png',
		cls : 'x-btn-text-icon',
		text : '【删除模块】',
		handler : function() {
			var array = q_sm.getSelections();
			if (array.length != 0) {
				Ext.Msg.confirm('信息', '删除资源时，子资源一起删除且不可恢复，你确定要删除吗？', function(btn) {
					if (btn == 'yes') {
						deleteModule();
					}
				});
			} else {
				Ext.Msg.alert('信息', '请选择你要删除的行！');
			}
		}
	});
	configAction = new Ext.Action({
		icon : '<%=context%>/images/icons/wrench.png',
		text : '【配置操作】',
		handler : function() {
			var array = q_sm.getSelections();
			if (array.length == 1) {
				configRsOperator();
			} else if(array.length == 0){
				Ext.Msg.alert('信息', '请选择你要配置操作的资源！');
			} else {
				Ext.Msg.alert('信息', '请选择一条资源！');
			}
		}
	});
	q_sm = new Ext.grid.CheckboxSelectionModel();
	q_store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : '<%=context%>/sysmgr/resources.do?method=toRsList&rsCode=IMTI_SYS_RESOURCE',
			method : 'POST'
		}),
		reader : new Ext.data.JsonReader({
					totalProperty : 'total',
					root : 'rows',
					id : "id"
				}, [{
							name : 'id'
						}, {
							name : 'parentId'
						}, {
							name : 'name'
						}, {
							name : 'code'
						}, {
							name : 'level'
						}, {
							name : 'displayIndex'
						}, {
							name : 'imgUrl'
						}, {
							name : 'url'
						}, {
							name : 'validFlag'
						}, {
							name : 'memo'
						}, {
							name : 'ztId'
						}]),
		autoLoad : true
	});
	// 模块列表
	colM = new Ext.grid.ColumnModel([q_sm, {
				header : "id",
				hidden : true,
				dataIndex : "id"
			}, {
				header : "parentId",
				hidden : true,
				dataIndex : "parentId"
			}, {
				header : "level",
				hidden : true,
				dataIndex : "level"
			}, {
				header : "validFlag",
				hidden : true,
				dataIndex : "validFlag"
			}, {
				header : "memo",
				hidden : true,
				dataIndex : "memo"
			}, {
				hidden : true,
				dataIndex : "imgUrl"
			}, {
				header : "模块名称",
				dataIndex : "name",
				sortable : true
			}, {
				header : "模块代码",
				dataIndex : "code"
			}, {
				header : "模块顺序",
				width : 62,
				dataIndex : "displayIndex",
				sortable : true
			}, {
				header : "模块URL",
				width : 200,
				dataIndex : "url"
			}
	]);

	q_grid = new Ext.grid.EditorGridPanel({
		id : 'gridId',
		region : 'center',
		title : "<center>模块定义</center>",
		fram : true,
		cm : colM,
		height : 250,
		width : 400,
		tbar : ['->', addAction, modifyAction, delAction, configAction],
		store : q_store,
		sm : q_sm,
		viewConfig : {
			forceFit : 'true'
		},
		listeners:{
            "rowdblclick" : function(grid, rowIndex, e){
            	modifyRsModule();
            }
        }
	});
	
	q_grid.getStore().on("beforeload", function() {
    	this.baseParams = {
    		pid: q_moduleId
    	};
    }); 
	    
	// -----begin模块树列表部分--------//
	var openAction = new Ext.Action({
		text : '【全部展开】',
		handler : function() {
			openAll();
		}
	});

	var closeAction = new Ext.Action({
		text : '【全部收拢】',
		handler : function() {
			closeAll();
		}
	});
	var root = new Ext.tree.AsyncTreeNode({
		id : "root",
		icon : '<%=context%>/images/icons/icon2_063.png',
		text : "<%=imtiSysName%>",
		leaf : false
	});
	
	/*
	*资源树
	*/
	q_treePanel = new Ext.tree.TreePanel({
		region : "west",
		width : 170,
		tbar : [openAction, closeAction],
		region : 'west',
		xtype : 'treepanel',
		id : 'tree',
		collapsible : true,
		split : true,
		useArrows : true,
		autoScroll : true,
		animate : true,
		containerScroll : true,
		loader : new Ext.tree.TreeLoader({
			dataUrl : '<%=context%>/sysmgr/resources.do?method=getRsTree&rsCode=IMTI_SYS_RESOURCE'
		}),
		root : root,
		listeners : {
			click : function(node, e) {
				if(node.getDepth() == 2){
					configAction.show();
				}else {
					configAction.hide();
				}
				q_moduleId = node.id;
				q_moduleName = node.text;
				q_grid.getStore().reload();
				q_grid.setTitle("模块定义");
			}
		}
	});
	
	
	//整个页面布局
	q_viewPort = new Ext.Viewport({
		layout : 'border',
		items : [q_treePanel, q_grid]
	});
	
	
});	

// ----打开树列表--//
function openAll() {
	q_treePanel.expandAll();
}
// ----关闭树列表--//
function closeAll() {
	q_treePanel.collapseAll();
}
function reset() {
	var form = addWin.getComponent("myForm").getForm();
	form.reset();
}
function modifyModuleReset() {
	var form = modifyModuleWin.getComponent("modifyModuleForm").getForm();
	form.reset();
}
</script>
<div id="center"></div>
</body>
</html>