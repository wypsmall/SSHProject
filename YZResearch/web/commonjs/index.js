/**********************�޸�����************************************/
function resetPassWord(){
		var resetPassWordWin = new Ext.Window({
			width: 300,
			height: 180,
			title: '�޸�����',
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
						fieldLabel : '������',
						allowBlank : false,
						inputType: "password",
						anchor : '90%',
						name : 'oldPassword'
					},{  
                 				xtype: 'textfield',      
                 				fieldLabel: '������',
                 				inputType: "password",
                 				allowBlank : false,
						anchor : '90%',
						name : 'newPassword'      
            				},{  
                				xtype: 'textfield',     
                				inputType: "password",
                				fieldLabel: 'ȷ��������',
                				allowBlank : false,
						anchor : '90%',
						name : 'loginPassword'      
             				} ]
			}),
			buttons :[{
				text : '����',
				icon : context + '/images/icons/save.gif',
				scope : this,
				handler : function() {
					var form = resetPassWordWin.getComponent("myForm").getForm();
					var oldPassword = form.findField("oldPassword").getValue();
					var newPassword = form.findField("newPassword").getValue();
					var loginPassword = form.findField("loginPassword").getValue();
					
					if(oldPassword == null || oldPassword==""){
						Ext.Msg.alert('��Ϣ', '����������룡');
						return;
					}
					if(newPassword == null || newPassword==""){
						Ext.Msg.alert('��Ϣ', '�����������룡');
						return;
					}
					if(loginPassword == null || loginPassword==""){
						Ext.Msg.alert('��Ϣ', '������ȷ�����룡');
						return;
					}
					
					if(loginPassword != newPassword){
						Ext.Msg.alert('��Ϣ', 'ȷ������������������벻��ͬ��');
						return;
					}
					Ext.Msg.progress("��ʾ", "�������ڱ��棬���Ժ�...");
					form.submit({
						waitMsg : "���Ժ�...",
						method : 'POST',
						url : context+'/login.do?method=resetPassWord',
						success : function(form, action){
							Ext.Msg.hide();
							form.reset();
							Ext.Msg.alert("��ʾ","�����޸ĳɹ���");
							resetPassWordWin.hide();
						},
						failure : function(form, action) {
							Ext.Msg.alert("��Ϣ",action.result.data);
						}
					});
				}
			}, {
				text : '�ر�',
				icon : context + '/images/icons/rollback.png',
				handler : function() {
					resetPassWordWin.close();
				}
			}]
		});
	resetPassWordWin.show();
}
/**********************�˳�ϵͳ************************************/
function logout(){
	Ext.Msg.confirm('��Ϣ', '��ȷ��Ҫ�˳�ϵͳ��', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : context + "/login.do?method=doLogout",
				success : function(form, action) {
					window.location.href=context +'/login.jsp';
				},
				failure : function(form, action) {
					Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;���紫����ϣ����Ѿ��˳���ϵͳ��&nbsp;");
				}
			});
		}
	});
}
/****************************����***********************************************/
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
//�Ӵ��ڵ���ʵ��
//window.parent.openExtWin(title, "activityStatus", "${ctx}/gsmwp/activity/activityStatus.jsp?createDate="+record.get("createDate")+"&activityId="+record.get("id"), 1000, 548);