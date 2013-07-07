<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.imti.framework.common.constant.SessionConstant,com.imti.sysmgr.vo.UserVO;"%>
<%
	String context = request.getContextPath();
	UserVO user = (UserVO)session.getAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER);
	String userName = user.getUserName();
	String orgName = user.getOrgName();
	String sessionId=session.getId();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css">
	img{border:0;behavior: url(iepngfix.htc);}
</style>	
<link rel="stylesheet" type="text/css" href="<%=context%>/ext3.0/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=context%>/ext3.0/resources/css/ext-extend.css" />
<script type="text/javascript" src="<%=context%>/ext3.0/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=context%>/ext3.0/ext-all.js"></script>
<script type="text/javascript" src="<%=context%>/commonjs/index.js"></script>
<script type="text/javascript" src="<%=context%>/commonjs/map.js"></script>
<script type="text/javascript">
var context = "<%=context%>";
Ext.BLANK_IMAGE_URL = 'images/index/s.gif';
Ext.MessageBox.buttonText.yes = '确定';
Ext.MessageBox.buttonText.no = '取消';
var resetPassWordWin = null;//修改密码对话框
Ext.QuickTips.init();
//生成标签页
var tab = new Ext.TabPanel({
	region:'center',
	deferredRender:false,
	activeTab:0,
	resizeTabs:true, // turn on tab resizing
	minTabWidth: 50,
	enableTabScroll:true,
	items:[{
		title:'系统说明',
		html:'<iframe scrolling="auto" frameborder="0" src="<%=context%>/projectInfo.html" width="100%" height="100%"></iframe>',
		el:'center'
	}]			
});


Ext.onReady(function(){
var tb = new Ext.Toolbar();
var levelOneMenu=new Array();
var levelTwoMenu=new Array();
var levelThreeMenu=new Array();
initPermission("<%=context %>/sysmgr/resources.do?method=getPermissionCode&rsCode=IMTI_SYS_RESOURCE");/*加载权限信息*/
/************************************************创建工具栏中动态菜单项（一级菜单） 开始***********************************/

function setOneLevel(){
	Ext.Ajax.request({
	  	url : context + "/sysmgr/resources.do?rsCode=IMTI_SYS_RESOURCE",
	  	params:{method : 'getLevelMenue',parentId : 'root'},
	  	success:function(request){
		  	var res = Ext.decode(request.responseText);
		  	var levelOne = res.data;
		  	for(var i = 0; i < levelOne.length; i++){
		  		levelOneMenu.push({
		  			id : levelOne[i].id ,
					text : levelOne[i].name,
					icon : context + "/" + levelOne[i].imgUrl,
					listeners : {
						'click' : function() {
							setLevelTwoMenu(this.id, this.text);
						}
					}
		  		});
		  	}
		  	setToolBarMenu();
	  	},
	  	failure:function(){
		  	Ext.Msg.alert('出错了','对不起，获取一级菜单失败');
	  	}
	});
}
setOneLevel();

/************************************************创建工具栏中动态菜单项（一级菜单） 结束***********************************/
/************************************************创建工具栏中的菜单项 开始***********************************/
function setToolBarMenu(){
	tb.render('toolbar');
    tb.add({
        text:'<%=userName%>',
        icon:'<%=context%>/images/icons/user.gif'
    },'-');
	tb.add(levelOneMenu);
	tb.add('-','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;', '->',{
        text: '修改密码',
        iconCls: 'bmenu',  // <-- icon
        icon:'<%=context%>/images/icons/key.gif',
        handler : function() {
			resetPassWord();
		}
    },{
        text: '退出',
        iconCls: 'bmenu',  // <-- icon
        icon:'<%=context%>/images/icons/door_in.png',
        handler : function() {
			logout();
		}
    });
    tb.doLayout();
    
    //触发一级菜单事件，用于首次进入时显示第二级菜单
	var isShow = false;
	if(levelOneMenu.length > 0 && !isShow){
		var id = levelOneMenu[0].id;
		var m = Ext.getCmp(id);
		if(m != null){
			m.fireEvent('click');
			isShow=true;
		}
	}
}    
/************************************************创建工具栏中的菜单项 结束***********************************/

/************************************************创建左边工具栏（二级菜单） 开始***********************************/
function setLevelTwoMenu(parentMenuId, parentMenuName){
	levelTwoMenu.length = 0;//清除原有的内容
	Ext.Ajax.request({
	  	url : context + "/sysmgr/resources.do?rsCode=IMTI_SYS_RESOURCE",
	  	params:{method : 'getLevelMenue',parentId : parentMenuId},
		success:function(request){
		  	var res = Ext.decode(request.responseText);
		  	var levelTwo = res.data;
		  	for(var i = 0; i < levelTwo.length; i++){
		  		levelTwoMenu.push({
		  			id : levelTwo[i].rsId ,
		  			xtype : 'panel',
		  			title : levelTwo[i].rsName,
					border:false,
					html:'<div id="mc' + levelTwo[i].rsId + '" style="overflow:auto;width:100%;height:100%"></div>' ,
					iconCls : levelTwo[i].rsImgUrl,
					listeners : {
						afterrender : setLevelThreeMenu("mc"+levelTwo[i].rsId,levelTwo[i].children),
						expand: setLevelThreeMenu("mc"+levelTwo[i].rsId,levelTwo[i].children)
					}
		  		});
		  	}
		  	var westPanel=Ext.getCmp('west-panel');
		  	if(westPanel.collapsed){
		  		westPanel.expand(true);//使用expand方法会出现收缩按钮不见的问题，暂时不知如何解决
		  	}
		  	westPanel.setTitle(parentMenuName);//修改菜单标题
		  	Ext.get("menu-panel_ele").dom.innerHTML="";//清除原有内容
		  	var np=new Ext.Panel({
	              	renderTo:"menu-panel_ele",
				 	id:'west-menu',
					split:true,
					border:false,
					width: '100%',
			    	height:Ext.getDom("menu-panel_ele").clientHeight,
					margins:'0 0 0 0',
					layout:'accordion',
					layoutConfig:{
						animate:true,
						activeOnTop:false
					},
					items : levelTwoMenu
			});
			Ext.getCmp("top-panel").doLayout();
	  	},
	  	failure:function(){
		  	Ext.Msg.alert('出错了','对不起，获取二级菜单失败');
	  	}
	});
}
/************************************************创建左边工具栏（二级菜单） 结束***********************************/

/************************************************创建左边工具栏（三级菜单） 开始***********************************/
function setLevelThreeMenu(parentMenuId,menue){
	return function(){
		if(menue.length == 0){
			return ;
		}
	  	var rootNode = new Ext.tree.TreeNode();
	  	for(var i = 0; i < menue.length; i++){
		  	var url = menue[i].rsUrl;
		  	rootNode.appendChild(new Ext.tree.TreeNode({
			  	id : 'treeNode_' + menue[i].rsId,
			  	iconCls : menue[i].rsImgUrl,
		  		text : menue[i].rsName,
		  		leaf : true,
		  		listeners:{
		  			'click' : handlerLevelThreeNode(menue[i].rsId,menue[i].rsName,menue[i].rsUrl, menue[i].rsImgUrl)
		  		}
		  	}));
	  	}
		Ext.get(parentMenuId).dom.innerHTML="";//清除原有内容
		var np=new Ext.tree.TreePanel({
			id : 'tree_' + parentMenuId,
            renderTo : parentMenuId,
            frame : false,
            border : false,
			width : '100%',
			autoScroll:true,
		    height : menue.length * 30,
			margins : '0 0 0 0',
			rootVisible : false,
			lines : false,
			root : rootNode
		});
		Ext.getCmp("top-panel").doLayout();
	}
}
function handlerLevelThreeNode(id,name,url,iconCls){
	return function(node, event) {  
    	var n = tab.getComponent('tab_'+id);  
        var htmlIFrame = '<iframe scrolling="no" frameborder="0" src="' + context + url+'" width="100%" height="100%"></iframe>';
        if(url.indexOf("http://") != -1){
        	if(url.indexOf("?") != -1){
         		htmlIFrame = '<iframe scrolling="no" frameborder="0" src="'+url+'&sessionId=<%=sessionId%>" width="100%" height="100%"></iframe>';
         	}else{
         		htmlIFrame = '<iframe scrolling="no" frameborder="0" src="'+url+'?sessionId=<%=sessionId%>" width="100%" height="100%"></iframe>';
         	}
        }
        if (!n) { //判断是否已经打开该面板  
           n = tab.add({  
               'id' : 'tab_' + id,  
               'title' : name,  
               iconCls : iconCls,
               closable : true,  //通过html载入目标页  
               html : htmlIFrame
            });  
        }  
       tab.setActiveTab(n);  
  	}  
}
/************************************************创建左边工具栏（三级菜单） 结束***********************************/
	var viewport = new Ext.Viewport({
		layout :'border',
		id :'top-panel',
		items : [new Ext.BoxComponent( {
					region :'north',
					el :'north',
					icon :'images/north.gif',
					height :80,
					collapsible :true,//是否允许折起
					tbar :tb
				}),new Ext.BoxComponent( {
					region :'south',
					el :'south',
					height :15
				}),{
					region :'west',
					layout :'fit',
					id :'west-panel',
					split :true,
					width :200,
					minSize :175,
					maxSize :400,
					margins :'0 0 0 0',
					layout :'accordion',
					title :'二级菜单',
					collapsible :true,
					layoutConfig : {
						animate :true
					},
					html :'<div id="menu-panel_ele" style="overflow:auto;width:100%;height:100%"></div>'
				},tab,{
					region :'east',
					layout :'fit',
					id :'east-panel',
					split :true,
					width :2,
					minSize :100,
					maxSize :150,
					margins :'0 0 0 0',
					layout :'accordion',
					title :'',
					collapsible :true,
					collapsed : false,
					layoutConfig : {
						animate :true
					},
					html :''
				}]
	});
	/*
	setTimeout(function(){
		Ext.getCmp("east-panel").collapse(true);
	},100);
	*/
});
</script>
</head>
<body>
<div id="loading-mask"></div>
<div id="loading">
	<div class="loading-indicator"></div>
	<div id="north" height="30%">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td  height="56" background="images/index/top_center.jpg" >&nbsp;</td>
			</tr>
		</table>
	</div>
	<div id="west"></div>
	<div id="south" class="south" style="height:25px; line-height:25px;" align="center">
		<font size="2">版权所有：imti技术中心</font>
	</div>
</div>
<div id="center"></div>
<div id="container" style="POSITION: absolute;top: 51px ">
    <div id="toolbar"></div>
</div>		
</body>
</html>
