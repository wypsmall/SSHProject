// **********************beginɾ����֯����*************//
function deleteOrg() {
	var array = q_sm.getSelections();
	var i = 0;
	var orgIds = null;
	while (i != array.length) {
		if (orgIds == null) {
			orgIds = array[i].get("orgId");
		} else {
			orgIds = orgIds + "," + array[i].get("orgId");
		}
		i++;
	}
	Ext.Ajax.request({
		url : context + "/sysmgr/sysmgr.do?method=deleteOrg&rsCode=IMTI_SYS_ORG&orgIds=" + orgIds,
		success : function(form, action) {
			Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;ɾ���ɹ���&nbsp;");
			q_store.reload();
		},
		failure : function(response, options) {
			Ext.Msg.alert("��Ϣ", "&nbsp;&nbsp;&nbsp;ɾ��ʧ�ܣ�&nbsp;");
		}
	});
}