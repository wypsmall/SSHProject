<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.imti.framework.component.vopomapping.utils.PropertyUtils"%>
<html>
<head>
	<%
		String context = request.getContextPath();
	%>
	<link rel="stylesheet" type="text/css" href="<%=context%>/ext3.0/resources/css/ext-all.css" />
	<script type="text/javascript" src="<%=context%>/ext3.0/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=context%>/ext3.0/ext-all.js"></script>
<title>用户列表</title>
</head>
<body>
<script type="text/javascript">
var context = "<%=context%>";
var q_viewPort = null;//页面布局
var q_treePanel = null;//资源树
var q_OrgId = null;//被选中的资源ID
var q_orgName = null;//被选中的资源名称
var q_store = null;//资源数据
var q_grid = null;//资源Grid
var q_formPanel = null;//新增模块
var q_sm = null;
var addWin = null;
var modifyUser_formPanel = null;
Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = "<%=context%>/images/blank.gif";
	Ext.QuickTips.init();
	//新增
	var addAction = new Ext.Action({
		icon : '<%=context%>/ext3.0/resources/images/default/dd/drop-add.gif',
		cls : 'x-btn-text-icon',
		text : '【新增用户】',
		handler : function() {
			//弹出框
			var iHeight = 200;
			var iWidth  = 500;
			var iTop = (window.screen.availHeight-30-iHeight)/2; 
			var iLeft = (window.screen.availWidth-10-iWidth)/2;
			var  url="customerUserAdd.jsp?rsCode=JP_CUS_USER_ADD&type=add";
			var styleStr = "height="+iHeight+",width="+iWidth+",top="+iTop+",left="+iLeft+","+"toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no";
			var OpenWindow =window.open(url,"新增用户",styleStr);
			try{
		  		OpenWindow.focus();
			}catch(e){}
		},
		listeners:{
            "render" : function(e){
            	//通过ajax请求，判断是否有此操作权限permissionVerify
            	Ext.Ajax.request({
					url :context + '/sysmgr/resources.do?method=permissionVerify&rsCode=JP_CUS_USER_ADD',
					success : function(response, options) {
		            	var responseObject = Ext.util.JSON.decode(response.responseText);
						if(responseObject.data){
							e.show();
						}else {
							e.hide();
						}
					},
					failure : function(response, options) {
						//没有通过权限验证时，隐藏按钮
						e.hide();
					}
				});	
				
            }
        }
	});
	//删除
	var delAction = new Ext.Action({
		icon : '<%=context%>/images/icons/delete.png',
		cls : 'x-btn-text-icon',
		text : '【删除用户】',
		handler : function() {
			var array = q_sm.getSelections();
			if (array.length != 0) {
				Ext.Msg.confirm('信息', '你确定要删除吗？', function(btn) {
					if (btn == 'yes') {
						deleteUser();
					}
				});
			} else {
				Ext.Msg.alert('信息', '请选择你要删除的行！');
			}
		},
		listeners:{
            "render" : function(e){
            	//通过ajax请求，判断是否有此操作权限permissionVerify
            	Ext.Ajax.request({
					url :context + '/sysmgr/resources.do?method=permissionVerify&rsCode=JP_CUS_USER_DEL',
					success : function(response, options) {
		            	var responseObject = Ext.util.JSON.decode(response.responseText);
						if(responseObject.data){
							e.show();
						}else {
							e.hide();
						}
					},
					failure : function(response, options) {
						//没有通过权限验证时，隐藏按钮
						e.hide();
					}
				});	
            }
        }
	});
	
	//账号暂停
	var stopAction = new Ext.Action({
		icon : '<%=context%>/images/icons/suspension.png',
		cls : 'x-btn-text-icon',
		text : '【账号暂停】',
		handler : function() {
			var array = q_sm.getSelections();
			if (array.length != 0) {
				Ext.Msg.confirm('信息', '你确定要停用该账号吗？', function(btn) {
					if (btn == 'yes') {
						suspensionUser();
					}
				});
			} else {
				Ext.Msg.alert('信息', '请选择你要停用的账号！');
			}
		},
		listeners:{
            "render" : function(e){
            	//通过ajax请求，判断是否有此操作权限permissionVerify
            	Ext.Ajax.request({
					url :context + '/sysmgr/resources.do?method=permissionVerify&rsCode=JP_CUS_USER_STOP',
					success : function(response, options) {
		            	var responseObject = Ext.util.JSON.decode(response.responseText);
						if(responseObject.data){
							e.show();
						}else {
							e.hide();
						}
					},
					failure : function(response, options) {
						//没有通过权限验证时，隐藏按钮
						e.hide();
					}
				});	
            }
        }
	});
	
	//账号激活
	var activeAction = new Ext.Action({
		icon : '<%=context%>/images/icons/activation.png',
		cls : 'x-btn-text-icon',
		text : '【账号激活】',
		handler : function() {
			var array = q_sm.getSelections();
			if (array.length != 0) {
				Ext.Msg.confirm('信息', '你确定要激活该账号吗？', function(btn) {
					if (btn == 'yes') {
						activationUser();
					}
				});
			} else {
				Ext.Msg.alert('信息', '请选择你要激活的账号！');
			}
		},
		listeners:{
            "render" : function(e){
            	//通过ajax请求，判断是否有此操作权限permissionVerify
            	Ext.Ajax.request({
					url :context + '/sysmgr/resources.do?method=permissionVerify&rsCode=JP_CUS_USER_ACT',
					success : function(response, options) {
		            	var responseObject = Ext.util.JSON.decode(response.responseText);
						if(responseObject.data){
							e.show();
						}else {
							e.hide();
						}
					},
					failure : function(response, options) {
						//没有通过权限验证时，隐藏按钮
						e.hide();
					}
				});	
            }
        }
	});
	 
	
	q_sm = new Ext.grid.CheckboxSelectionModel();
	q_store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : '<%=context%>/jplms/adapter/sysmgr.do?method=getUserListByLogId&rsCode=JP_CUS_USER',
			method : 'POST'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			root : 'rows',
			id : "id"
		}, [{
			name : 'id'
		}, {
			name : 'loginId'
		}, {
			name : 'userNick'
		}, {
			name : 'userName'
		}, {
			name : 'valid'
		}, {
			name : 'orgId'
		}, {
			name : 'loginType'
		}, {
			name : 'orgName'
		}]),
		autoLoad : true
	});
	// 用户列表
	colM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),q_sm, {
				header : "id",
				hidden : true,
				dataIndex : "id"
			},{
				header : "loginType",
				hidden : true,
				dataIndex : "loginType"
			}, {
				header : "账号",
				sortable : true,
				dataIndex : "loginId"
			}, {
				header : "用户昵称",
				dataIndex : "userNick",
				sortable : true
			}, {
				header : "用户姓名",
				dataIndex : "userName"
			}, {
				header : "角色",
				dataIndex : "roleName",
				hidden : true,
				sortable : true,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					if(value == ""){
						return '<a href="#" onclick="modifyUser()">配置角色</a>' + '&nbsp;&nbsp;&nbsp;&nbsp;'
					}
					return value;
				}
			}, {
				header : "账号状态",
				dataIndex : "valid",
				rowspan : 2,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					if(value=="1"){
						return ' 激活';
					}
					return '<font color="red">暂停</font>';
				}
			}
	]);
	
	//搜索按钮
	var actionButton = new Ext.Action({
        text: '【搜索】',
        icon : '<%=context%>/images/icons/zoom.png',
		cls : 'x-btn-text-icon',
        handler: function(){
			q_grid.getStore().load();
        }
    });

	q_grid = new Ext.grid.EditorGridPanel({
		id : 'gridId',
		region : 'center',
		title : "<center>用户列表</center>",
		fram : true,
		cm : colM,
		height : 250,
		width : 400,
		tbar : ['登陆账号：',{
					xtype : 'textfield',
					width : 80,
					id : 'loginId'
				},actionButton, addAction, 
				stopAction, activeAction],
		bbar:  new Ext.PagingToolbar({
			lastText : '前一页',
			pageSize : 20,
			store : q_store,
			displayInfo : true,
			displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
			emptyMsg : "<font color='red'>-----没有记录-----</font>",
			prevText : "上一页",
			nextText : "下一页",
			refreshText : "刷新",
			lastText : "最后页",
			firstText : "第一页",
			beforePageText : "当前页",
			afterPageText : "共{0}页"
		}),
		store : q_store,
		sm : q_sm,
		viewConfig : {
			forceFit : 'true'
		},
		listeners:{
            "rowdblclick" : function(grid, rowIndex, e){
            	modifyUser();
            }
        }
	});
	
	q_grid.getStore().on("beforeload", function() {
    	this.baseParams = {
    		loginId : encodeURIComponent(Ext.getCmp("loginId").getValue())
    	};
    }); 
	//整个页面布局
	q_viewPort = new Ext.Viewport({
		layout : 'border',
		items : [q_grid]
	});
	
	
});	
</script>
<div id="center"></div>
</body>
</html>