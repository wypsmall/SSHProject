function deleteUser() {
	var array = q_sm.getSelections();
	var i = 0;
	var userIds = null;
	while (i != array.length) {
		if (userIds == null) {
			userIds = array[i].get("id");
		} else {
			userIds = userIds + "," + array[i].get("id");
		}
		i++;
	}
	Ext.Ajax.request({
		url : context + "/sysmgr/sysmgr.do?method=deleteUser&rsCode=IMTI_SYS_USR&userIds=" + userIds,
		success : function(form, action) {
			Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;ɾ���ɹ���&nbsp;");
			q_store.reload();
		},
		failure : function(response, options) {
			Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;ɾ��ʧ�ܣ�&nbsp;");
		}
	});
}