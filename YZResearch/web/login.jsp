<%@ page contentType="text/html;charset=GBK"%>
<%
	String context = request.getContextPath();
%>
<html>
<head>
<title></title>
<style type="text/css">
	img{border:0;behavior: url(iepngfix.htc);}
</style>	
<link rel="stylesheet" type="text/css" href="<%=context%>/ext3.0/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=context%>/ext3.0/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=context%>/ext3.0/ext-all.js"></script>
<script type="text/javascript">
var context = "<%=context%>";
Ext.BLANK_IMAGE_URL = 'images/index/s.gif';

Ext.QuickTips.init();

Ext.onReady(function(){
	Ext.form.Field.prototype.msgTarget = 'side'; 
	var simple = new Ext.FormPanel({ 
		labelAlign : 'right',
		buttonAlign : 'right',
		frame : true,
		region : 'center',
		id : 'loginForm',
		labelWidth :60,
		items: [{ 
			
			xtype : 'textfield',
			fieldLabel: '帐户', 
			name: 'loginId',
			allowBlank:false, 
			value : 'admin',
			width : 180,
			blankText:'帐户不能为空' 
		},{ 
			xtype : 'textfield',
			inputType:'password', 
			fieldLabel: '密码', 
			name: 'password', 
			width : 180,
			value : '123456',
			allowBlank:false, 
			blankText:'密码不能为空' 
		},{ 
			xtype : 'textfield', 
			fieldLabel: '帐套', 
			name: 'ztId', 
			value : '10001',
			width : 180,
			allowBlank:false, 
			blankText:'帐套不能为空' 
		},{
			layout:'column',
	        defaults:{layout: 'form'},
    		items:[{
          		columnWidth:.60,items: [{
            			xtype : 'textfield',
            			fieldLabel : "验证码",
            			allowBlank: false,
            			name : "randomCode",
            			width : 80
            		}]
			},{columnWidth:.40,labelWidth :1,
				items: [{xtype:'panel',labelWidth :1,labelAlign : 'left',
					html:'<img onClick="getVerifyCodeImg(this)" id="verifyCodeImg" src="'+context + '/randomCode.GIF'+'"/>'}]
			}]
  		}],
        keys:[{
        	key: [10,13],
          	fn: doSubmmit
       	}],
		buttons: [{ 
			text: '登陆', 
			type: 'submit', 
			handler:function(){ 
				doSubmmit();
			} 
		},{ 
			text: '取消', 
			handler:function(){Ext.getCmp("loginForm").form.reset();}//重置表单 
		}] 
	});

	//定义窗体 
	var win = new Ext.Window({ 
		id:'win', 
		title:'用户登陆',
		//iconCls : 'x-window-bc',  
		layout:'fit',  
		width:300, 
		height:200, 
		plain:true, 
		bodyStyle:'padding:5px;', 
		maximizable:false,
		closeAction:'close', 
		closable:false,
		buttonAlign:'right', 
		items:simple
	}); 
	win.show();

	//提交
	function doSubmmit(){
		if(simple.form.isValid()){//验证合法后使用加载进度条 
			Ext.MessageBox.show({ 
				title: '请稍等', 
				msg: '正在加载...', 
				progressText: '', 
				width:300, 
				progress:true, 
				closable:false, 
				animEl: 'loding' 
			}); 
			//控制进度速度 
			var f = function(v){ 
				return function(){ 
					var i = v/11; 
					Ext.MessageBox.updateProgress(i, ''); 
				}; 
			}; 
			for(var i = 1; i < 13; i++){ 
				setTimeout(f(i), i*150); 
			} 
			simple.form.doAction('submit',{ 
				url:'<%=context%>/login.do?method=doLogin', 
				method:'post',
				success:function(form,action){ 
					if(parent){
						parent.location.href='<%=context %>/index.jsp';
					}else{
						window.location.href='<%=context %>/index.jsp';
					}
				}, 
				failure : function(form, action) {
					getVerifyCodeImg(Ext.get("verifyCodeImg").dom);
					Ext.Msg.alert("信息",action.result.data);
				} 
			}); 
		} 
	}
});
function getVerifyCodeImg(obj){
	obj.src='<%=context %>/randomCode.GIF?'+Math.random(); 
}
</script>
</head>
<%if(session.getAttribute("RANDOM_CODE_VERIFY")!=null){
	session.setAttribute("RANDOM_CODE_VERIFY",null);	
}%>
<body>
<div id="center"></div>
</body>
</html>
