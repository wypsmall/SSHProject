/**********************修改密码************************************/
function resetPassWord(){
		var resetPassWordWin = new Ext.Window({
			width: 300,
			height: 180,
			title: '修改密码',
			plain : true,
			closable : true,
			closeAction : 'hide',
			resizable : false,
			buttonAlign : 'center',
			layout: "fit",
			items :new Ext.form.FormPanel({
				labelAlign : 'left',
				bodyStyle: "padding-top: 5px",
				id : 'myForm',
				frame : true,
				layout: "form",
				labelWidth : 100,
				items : [{
						xtype : 'textfield',
						fieldLabel : '旧密码',
						allowBlank : false,
						inputType: "password",
						anchor : '90%',
						name : 'oldPassword'
					},{  
                 				xtype: 'textfield',      
                 				fieldLabel: '新密码',
                 				inputType: "password",
                 				allowBlank : false,
						anchor : '90%',
						name : 'newPassword'      
            				},{  
                				xtype: 'textfield',     
                				inputType: "password",
                				fieldLabel: '确认新密码',
                				allowBlank : false,
						anchor : '90%',
						name : 'loginPassword'      
             				} ]
			}),
			buttons :[{
				text : '保存',
				icon : context + '/images/icons/save.gif',
				scope : this,
				handler : function() {
					var form = resetPassWordWin.getComponent("myForm").getForm();
					var oldPassword = form.findField("oldPassword").getValue();
					var newPassword = form.findField("newPassword").getValue();
					var loginPassword = form.findField("loginPassword").getValue();
					
					if(oldPassword == null || oldPassword==""){
						Ext.Msg.alert('信息', '请输入旧密码！');
						return;
					}
					if(newPassword == null || newPassword==""){
						Ext.Msg.alert('信息', '请输入新密码！');
						return;
					}
					if(loginPassword == null || loginPassword==""){
						Ext.Msg.alert('信息', '请输入确认密码！');
						return;
					}
					
					if(loginPassword != newPassword){
						Ext.Msg.alert('信息', '确认密码与输入的新密码不相同！');
						return;
					}
					Ext.Msg.progress("提示", "数据正在保存，请稍侯...");
					form.submit({
						waitMsg : "请稍侯...",
						method : 'POST',
						url : context+'/login.do?method=resetPassWord',
						success : function(form, action){
							Ext.Msg.hide();
							form.reset();
							Ext.Msg.alert("提示","密码修改成功！");
							resetPassWordWin.hide();
						},
						failure : function(form, action) {
							Ext.Msg.alert("信息",action.result.data);
						}
					});
				}
			}, {
				text : '关闭',
				icon : context + '/images/icons/rollback.png',
				handler : function() {
					resetPassWordWin.close();
				}
			}]
		});
	resetPassWordWin.show();
}
/**********************退出系统************************************/
function logout(){
	Ext.Msg.confirm('信息', '你确定要退出系统吗？', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : context + "/login.do?method=doLogout",
				success : function(form, action) {
					window.location.href=context +'/login.jsp';
				},
				failure : function(form, action) {
					Ext.Msg.alert("信息", "&nbsp;&nbsp;&nbsp;网络传输故障，您已经退出了系统！&nbsp;");
				}
			});
		}
	});
}
/****************************窗口***********************************************/
var subWin = null;
function openExtWin(title, winId, url, width, height, _subWin) {
	var win = Ext.getCmp(winId);
	if (!win) {
		win = new Ext.Window({
			title: title,
			id: winId,
			width: width,
			modal: true,
			autoDestroy: true,
			frame:true,
			autoScroll: false,
			height: height,
			html: "<iframe id='"+winId+"Ifm' scrolling='auto' width='"+(width-13)+"' height='"+(height-5)+"' src='"+url+"' frameborder='0'></iframe>"
		});
	}
	subWin = _subWin
	win.show();
}

function refreshGrid(){
	subWin.grid.getStore().reload();
}

function closeExtWin(winId) {
	var win = Ext.getCmp(winId);
	if (win) {
		win.close();
	}
}
//子窗口调用实例
//window.parent.openExtWin(title, "activityStatus", "${ctx}/gsmwp/activity/activityStatus.jsp?createDate="+record.get("createDate")+"&activityId="+record.get("id"), 1000, 548);