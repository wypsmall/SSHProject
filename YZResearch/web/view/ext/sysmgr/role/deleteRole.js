function deleteRole() {
	var array = q_sm.getSelections();
	var i = 0;
	var roleIds = null;
	while (i != array.length) {
		if (roleIds == null) {
			roleIds = array[i].get("id");
		} else {
			roleIds = roleIds + "," + array[i].get("id");
		}
		i++;
	}
	Ext.Ajax.request({
		url : context + "/sysmgr/sysmgr.do?method=deleteRole&rsCode=IMTI_SYS_ROLE&roleIds=" + roleIds,
		success : function(form, action) {
			Ext.Msg.alert("信息", "&nbsp;&nbsp;&nbsp;删除成功！&nbsp;");
			store.reload();
		},
		failure : function(response, options) {
			Ext.Msg.alert("信息", "&nbsp;&nbsp;&nbsp;删除失败！&nbsp;");
		}
	});
}