// **********************beginɾ��ģ��*************//
function deleteModule() {
	var array = q_sm.getSelections();
	var i = 0;
	var rsIds = null;
	while (i != array.length) {
		if (rsIds == null) {
			rsIds = array[i].get("id");
		} else {
			rsIds = rsIds + "," + array[i].get("id");
		}
		i++;
	}

	Ext.Ajax.request({
		url : context + "/sysmgr/resources.do?method=deleteRsModule&rsCode=IMTI_SYS_RESOURCE&rsIds=" + rsIds,
		success : function(form, action) {
			Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;ɾ���ɹ���&nbsp;");
			q_store.reload();
		},
		failure : function(form, action) {
			Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;ɾ��ʧ�ܣ�&nbsp;");
		}
	});

}