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
Ext.MessageBox.buttonText.yes = 'ȷ��';
Ext.MessageBox.buttonText.no = 'ȡ��';
var resetPassWordWin = null;//�޸�����Ի���
Ext.QuickTips.init();
//���ɱ�ǩҳ
var tab = new Ext.TabPanel({
	region:'center',
	deferredRender:false,
	activeTab:0,
	resizeTabs:true, // turn on tab resizing
	minTabWidth: 50,
	enableTabScroll:true,
	items:[{
		title:'ϵͳ˵��',
		html:'<iframe scrolling="auto" frameborder="0" src="<%=context%>/projectInfo.html" width="100%" height="100%"></iframe>',
		el:'center'
	}]			
});


Ext.onReady(function(){
var tb = new Ext.Toolbar();
var levelOneMenu=new Array();
var levelTwoMenu=new Array();
var levelThreeMenu=new Array();
initPermission("<%=context %>/sysmgr/resources.do?method=getPermissionCode&rsCode=IMTI_SYS_RESOURCE");/*����Ȩ����Ϣ*/
/************************************************�����������ж�̬�˵��һ���˵��� ��ʼ***********************************/

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
		  	Ext.Msg.alert('������','�Բ��𣬻�ȡһ���˵�ʧ��');
	  	}
	});
}
setOneLevel();

/************************************************�����������ж�̬�˵��һ���˵��� ����***********************************/
/************************************************�����������еĲ˵��� ��ʼ***********************************/
function setToolBarMenu(){
	tb.render('toolbar');
    tb.add({
        text:'<%=userName%>',
        icon:'<%=context%>/images/icons/user.gif'
    },'-');
	tb.add(levelOneMenu);
	tb.add('-','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;', '->',{
        text: '�޸�����',
        iconCls: 'bmenu',  // <-- icon
        icon:'<%=context%>/images/icons/key.gif',
        handler : function() {
			resetPassWord();
		}
    },{
        text: '�˳�',
        iconCls: 'bmenu',  // <-- icon
        icon:'<%=context%>/images/icons/door_in.png',
        handler : function() {
			logout();
		}
    });
    tb.doLayout();
    
    //����һ���˵��¼��������״ν���ʱ��ʾ�ڶ����˵�
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
/************************************************�����������еĲ˵��� ����***********************************/

/************************************************������߹������������˵��� ��ʼ***********************************/
function setLevelTwoMenu(parentMenuId, parentMenuName){
	levelTwoMenu.length = 0;//���ԭ�е�����
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
		  		westPanel.expand(true);//ʹ��expand���������������ť���������⣬��ʱ��֪��ν��
		  	}
		  	westPanel.setTitle(parentMenuName);//�޸Ĳ˵�����
		  	Ext.get("menu-panel_ele").dom.innerHTML="";//���ԭ������
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
		  	Ext.Msg.alert('������','�Բ��𣬻�ȡ�����˵�ʧ��');
	  	}
	});
}
/************************************************������߹������������˵��� ����***********************************/

/************************************************������߹������������˵��� ��ʼ***********************************/
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
		Ext.get(parentMenuId).dom.innerHTML="";//���ԭ������
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
        if (!n) { //�ж��Ƿ��Ѿ��򿪸����  
           n = tab.add({  
               'id' : 'tab_' + id,  
               'title' : name,  
               iconCls : iconCls,
               closable : true,  //ͨ��html����Ŀ��ҳ  
               html : htmlIFrame
            });  
        }  
       tab.setActiveTab(n);  
  	}  
}
/************************************************������߹������������˵��� ����***********************************/
	var viewport = new Ext.Viewport({
		layout :'border',
		id :'top-panel',
		items : [new Ext.BoxComponent( {
					region :'north',
					el :'north',
					icon :'images/north.gif',
					height :80,
					collapsible :true,//�Ƿ���������
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
					title :'�����˵�',
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
		<font size="2">��Ȩ���У�imti��������</font>
	</div>
</div>
<div id="center"></div>
<div id="container" style="POSITION: absolute;top: 51px ">
    <div id="toolbar"></div>
</div>		
</body>
</html>
