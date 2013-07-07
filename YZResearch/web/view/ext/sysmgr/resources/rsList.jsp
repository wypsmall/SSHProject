<%@ page language="java" contentType="text/html; charset=GB2312"%>
<%@ page import="java.util.*,com.imti.framework.component.vopomapping.utils.PropertyUtils"%>
<html>
<head>
<%
	String context = request.getContextPath();
	String imtiSysName=PropertyUtils.getProperty("imti.sys.name","����ƽ̨");
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
<title>ģ�鶨��</title>
</head>
<body>
<script type="text/javascript">
var context = "<%=context%>";
var q_viewPort = null;//ҳ�沼��
var q_treePanel = null;//��Դ��
var q_moduleId = null;//��ѡ�е���ԴID
var q_moduleName = null;//��ѡ�е���Դ����
var q_store = null;//��Դ����
var q_grid = null;//��ԴGrid
var q_formPanel = null;//����ģ��
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
		text : '������ģ�顿',
		handler : function() {
			if (q_moduleId == null || q_moduleId == "") {
				Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;��ѡ��ģ�飡&nbsp;");;
				return;
			}
			addModule();
		}
	});
	modifyAction = new Ext.Action({
		icon : '<%=context%>/images/icons/ico03.png',
		cls : 'x-btn-text-icon',
		text : '���޸�ģ�顿',
		handler : function() {
			modifyRsModule();
		}
	});
	delAction = new Ext.Action({
		icon : '<%=context%>/images/icons/delete.png',
		cls : 'x-btn-text-icon',
		text : '��ɾ��ģ�顿',
		handler : function() {
			var array = q_sm.getSelections();
			if (array.length != 0) {
				Ext.Msg.confirm('��Ϣ', 'ɾ����Դʱ������Դһ��ɾ���Ҳ��ɻָ�����ȷ��Ҫɾ����', function(btn) {
					if (btn == 'yes') {
						deleteModule();
					}
				});
			} else {
				Ext.Msg.alert('��Ϣ', '��ѡ����Ҫɾ�����У�');
			}
		}
	});
	configAction = new Ext.Action({
		icon : '<%=context%>/images/icons/wrench.png',
		text : '�����ò�����',
		handler : function() {
			var array = q_sm.getSelections();
			if (array.length == 1) {
				configRsOperator();
			} else if(array.length == 0){
				Ext.Msg.alert('��Ϣ', '��ѡ����Ҫ���ò�������Դ��');
			} else {
				Ext.Msg.alert('��Ϣ', '��ѡ��һ����Դ��');
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
	// ģ���б�
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
				header : "ģ������",
				dataIndex : "name",
				sortable : true
			}, {
				header : "ģ�����",
				dataIndex : "code"
			}, {
				header : "ģ��˳��",
				width : 62,
				dataIndex : "displayIndex",
				sortable : true
			}, {
				header : "ģ��URL",
				width : 200,
				dataIndex : "url"
			}
	]);

	q_grid = new Ext.grid.EditorGridPanel({
		id : 'gridId',
		region : 'center',
		title : "<center>ģ�鶨��</center>",
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
	    
	// -----beginģ�����б���--------//
	var openAction = new Ext.Action({
		text : '��ȫ��չ����',
		handler : function() {
			openAll();
		}
	});

	var closeAction = new Ext.Action({
		text : '��ȫ����£��',
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
	*��Դ��
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
				q_grid.setTitle("ģ�鶨��");
			}
		}
	});
	
	
	//����ҳ�沼��
	q_viewPort = new Ext.Viewport({
		layout : 'border',
		items : [q_treePanel, q_grid]
	});
	
	
});	

// ----�����б�--//
function openAll() {
	q_treePanel.expandAll();
}
// ----�ر����б�--//
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