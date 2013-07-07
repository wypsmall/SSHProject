<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
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
<title>��֯����</title>
</head>
<body>
<script type="text/javascript">
var context = "<%=context%>";
var q_viewPort = null;//ҳ�沼��
var q_treePanel = null;//��Դ��
var q_OrgId = null;//��ѡ�е���ԴID
var q_orgName = null;//��ѡ�е���Դ����
var q_store = null;//��Դ����
var q_grid = null;//��ԴGrid
var q_formPanel = null;//����ģ��
var q_sm = null;
var addAction = null;
var modifyAction = null;
var delAction = null;
var addWin = null;
var modifyOrgWin = null;
var modifyOrg_formPanel = null;
var type= "1";
//radiobox��д
Ext.override(Ext.form.RadioGroup, { 
getValue: function(){ var v; 
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

Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = "<%=context%>/images/s.gif";
	Ext.QuickTips.init();
	addAction = new Ext.Action({
		icon : '<%=context%>/ext3.0/resources/images/default/dd/drop-add.gif',
		cls : 'x-btn-text-icon',
		text : '��������֯������',
		handler : function() {
			if (q_OrgId == null || q_OrgId == "") {
				Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;��ѡ����֯������&nbsp;");;
				return;
			}
			addOrg();
		}
	});
	modifyAction = new Ext.Action({
		icon : '<%=context%>/images/icons/ico03.png',
		text : '���޸���֯������',
		handler : function() {
			var array = q_sm.getSelections();
			if (array.length == 1) {
				modifyOrg();
			} else if(array.length == 0){
				Ext.Msg.alert('��Ϣ', '��ѡ����Ҫ�޸ĵ��У�');
			} else if(array.length > 1){
				Ext.Msg.alert('��Ϣ', '��ѡ��һ��Ҫ�޸ĵ��У�');
			} 
		}
	});
	delAction = new Ext.Action({
		icon : '<%=context%>/images/icons/delete.png',
		cls : 'x-btn-text-icon',
		text : '��ɾ����֯������',
		handler : function() {
			var array = q_sm.getSelections();
			if (array.length != 0) {
				Ext.Msg.confirm('��Ϣ', '��ȷ��Ҫɾ����', function(btn) {
					if (btn == 'yes') {
						deleteOrg();
					}
				});
			} else {
				Ext.Msg.alert('��Ϣ', '��ѡ����Ҫɾ�����У�');
			}
		}
	});
	q_sm = new Ext.grid.CheckboxSelectionModel();
	q_store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : '<%=context%>/sysmgr/sysmgr.do?method=getOrgListByParentId&rsCode=IMTI_SYS_ORG',
			method : 'POST'
		}),
		reader : new Ext.data.JsonReader({
					totalProperty : 'total',
					root : 'rows',
					id : "orgId"
				}, [{
							name : 'orgId'
						}, {
							name : 'orgName'
						}, {
							name : 'orgCode'
						}, {
							name : 'orgParentId'
						}, {
							name : 'memo'
						},{
							name : 'type'
						},{
							name : 'ztId'
						},{
							name : 'ownerCompany'
						},{
							name : 'displayIndex'
						}]),
		autoLoad : true
	});
	// ��֯�����б�
	colM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),q_sm, {
				header : "id",
				hidden : true,
				dataIndex : "orgId"
			}, {
				header : "orgParentId",
				hidden : true,
				dataIndex : "orgParentId"
			}, {
				header : "��������",
				dataIndex : "orgName",
				sortable : true
			}, {
				header : "��������",
				dataIndex : "type",
				renderer :toType,
				sortable :true
			},{
				header : "��������",
				dataIndex : "orgCode"
			}, {
				header : "����",
				dataIndex : "memo",
				sortable : true
			}
			
	]);

	q_grid = new Ext.grid.EditorGridPanel({
		id : 'gridId',
		region : 'center',
		title : "<center>��֯�����б�</center>",
		fram : true,
		cm : colM,
		height : 250,
		width : 350,
		tbar : ['->',addAction, modifyAction, delAction],
		store : q_store,
		sm : q_sm,
		viewConfig : {
			forceFit : 'true'
		},
		listeners:{
                "rowdblclick" : function(grid, rowIndex, e){
                	modifyOrg();
                }
            }
	});
	
	q_grid.getStore().on("beforeload", function() {
    	this.baseParams = {
    		parentId: q_OrgId
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
		icon : '<%=context%>/images/icons/house.png',
		text : "<%=imtiSysName%>",
		leaf : false
	});
	
	/*
	*��Դ��
	*/
	q_treePanel = new Ext.tree.TreePanel({
		region : "west",
		root : root,
		width : 250,
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
			dataUrl : '<%=context%>/sysmgr/sysmgr.do?method=getOrgTree&rsCode=IMTI_SYS_ORG'
		}),
		listeners : {
			click : function(node, e) {
				q_OrgId = node.id;
				q_orgName = node.text;
				q_grid.getStore().reload();
				q_grid.setTitle("��������");
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
	var form = modifyOrgWin.getComponent("modifyOrgForm").getForm();
	form.reset();
}
function toType(val){
  if(val=='1'){return "��ͨ����";
  }else if(val=='2'){return "��������";
  }else if(val=='3'){return "��������";
  }else {return "��������";}
 }
</script>
<script type="text/javascript" src="<%=context%>/view/ext/sysmgr/org/addOrg.js"></script>
<script type="text/javascript" src="<%=context%>/view/ext/sysmgr/org/modifyOrg.js"></script>
<script type="text/javascript" src="<%=context%>/view/ext/sysmgr/org/deleteOrg.js"></script>
<div id="center"></div>
</body>
</html>