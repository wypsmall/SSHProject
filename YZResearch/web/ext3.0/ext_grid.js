Ext.extend.PagingToolbar = function(store, pageSize){
	pageSize = pageSize || 20;
	return new Ext.PagingToolbar({
		lastText : '前一页',
		pageSize : pageSize,
		store : store,
		displayInfo : true,
		displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
		emptyMsg : "<font color='red'>-----没有记录-----</font>",
		prevText : "上一页",
		nextText : "下一页",
		refreshText : "刷新",
		lastText : "最后页",
		firstText : "第一页",
		beforePageText : "当前页",
		afterPageText : "共{0}页"
	})
}