// **********************begin删除组织机构*************//
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
			Ext.Msg.alert("信息", "&nbsp;&nbsp;&nbsp;删除成功！&nbsp;");
			q_store.reload();
		},
		failure : function(response, options) {
			Ext.Msg.alert("信息", "&nbsp;&nbsp;&nbsp;删除失败！&nbsp;");
		}
	});
}