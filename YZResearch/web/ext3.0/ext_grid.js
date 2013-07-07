Ext.extend.PagingToolbar = function(store, pageSize){
	pageSize = pageSize || 20;
	return new Ext.PagingToolbar({
		lastText : 'ǰһҳ',
		pageSize : pageSize,
		store : store,
		displayInfo : true,
		displayMsg : '��ʾ�� {0} ���� {1} ����¼���� {2} ��',
		emptyMsg : "<font color='red'>-----û�м�¼-----</font>",
		prevText : "��һҳ",
		nextText : "��һҳ",
		refreshText : "ˢ��",
		lastText : "���ҳ",
		firstText : "��һҳ",
		beforePageText : "��ǰҳ",
		afterPageText : "��{0}ҳ"
	})
}