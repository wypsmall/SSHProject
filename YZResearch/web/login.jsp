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
			fieldLabel: '�ʻ�', 
			name: 'loginId',
			allowBlank:false, 
			value : 'admin',
			width : 180,
			blankText:'�ʻ�����Ϊ��' 
		},{ 
			xtype : 'textfield',
			inputType:'password', 
			fieldLabel: '����', 
			name: 'password', 
			width : 180,
			value : '123456',
			allowBlank:false, 
			blankText:'���벻��Ϊ��' 
		},{ 
			xtype : 'textfield', 
			fieldLabel: '����', 
			name: 'ztId', 
			value : '10001',
			width : 180,
			allowBlank:false, 
			blankText:'���ײ���Ϊ��' 
		},{
			layout:'column',
	        defaults:{layout: 'form'},
    		items:[{
          		columnWidth:.60,items: [{
            			xtype : 'textfield',
            			fieldLabel : "��֤��",
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
			text: '��½', 
			type: 'submit', 
			handler:function(){ 
				doSubmmit();
			} 
		},{ 
			text: 'ȡ��', 
			handler:function(){Ext.getCmp("loginForm").form.reset();}//���ñ� 
		}] 
	});

	//���崰�� 
	var win = new Ext.Window({ 
		id:'win', 
		title:'�û���½',
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

	//�ύ
	function doSubmmit(){
		if(simple.form.isValid()){//��֤�Ϸ���ʹ�ü��ؽ����� 
			Ext.MessageBox.show({ 
				title: '���Ե�', 
				msg: '���ڼ���...', 
				progressText: '', 
				width:300, 
				progress:true, 
				closable:false, 
				animEl: 'loding' 
			}); 
			//���ƽ����ٶ� 
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
					Ext.Msg.alert("��Ϣ",action.result.data);
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
