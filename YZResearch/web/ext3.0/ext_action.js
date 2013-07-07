Ext.extend.SearchAction = function(title, context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/images/icons/zoom.png',
		cls : 'x-btn-text-icon',
		text : '【' + title + '】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.SaveAction = function(title, context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/images/icons/save.gif',
		cls : 'x-btn-text-icon',
		text : '【' + title + '】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.AddAction = function(context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/ext3.0/resources/images/default/dd/drop-add.gif',
		cls : 'x-btn-text-icon',
		text : '【新增】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.UpdateAction = function(context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/images/icons/ico03.png',
		cls : 'x-btn-text-icon',
		text : '【修改】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.DeleteAction = function(context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/images/icons/delete.png',
		cls : 'x-btn-text-icon',
		text : '【删除】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.SubmitAction = function(context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/images/icons/up.png',
		cls : 'x-btn-text-icon',
		text : '【提交】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.AuditAction = function(context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/images/icons/delete.png',
		cls : 'x-btn-text-icon',
		text : '【审核】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.CanelAction = function(context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/images/icons/delete.png',
		cls : 'x-btn-text-icon',
		text : '【销审】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.ExpExcelAction = function(context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/images/icons/ico-excel.gif',
		cls : 'x-btn-text-icon',
		text : '【导出】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.SuspendAction = function(context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/images/icons/suspension.png',
		cls : 'x-btn-text-icon',
		text : '【停用】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.ActivityAction = function(context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/images/icons/activation.png',
		cls : 'x-btn-text-icon',
		text : '【启用】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.lockAction = function(context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/images/icons/lock.png',
		cls : 'x-btn-text-icon',
		text : '【锁定】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.releaeAction = function(context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/images/icons/lock_open.png',
		cls : 'x-btn-text-icon',
		text : '【解锁】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.ConfigureAction = function(title, context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/images/icons/wrench.png',
		cls : 'x-btn-text-icon',
		text : '【' + title + '】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.ExcelAction = function(title, context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/images/icons/ico-excel.gif',
		cls : 'x-btn-text-icon',
		text : '【' + title + '】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.DownAction = function(title, context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/images/icons/download.gif',
		cls : 'x-btn-text-icon',
		text : '【' + title + '】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.PrintAction = function(title, context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/images/icons/import.gif',
		cls : 'x-btn-text-icon',
		text : '【' + title + '】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.InAction = function(title, context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context + '/ext3.0/resources/images/default/dd/drop-add.gif',
		cls : 'x-btn-text-icon',
		text : '【' + title + '】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.VoyageAction = function(title, context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context+'/images/icons/flag_blue.png',
		cls : 'x-btn-text-icon',
		text : '【' + title + '】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}
Ext.extend.GroupAction = function(title, context,handlerFunc, renderFunc){
	renderFunc = renderFunc || function(e){};
	return new Ext.Action({
		icon : context+'/images/icons/Furniture House/Green Chalkboard.png',
		cls : 'x-btn-text-icon',
		text : '【' + title + '】',
		handler : handlerFunc,
		listeners:{
			"afterrender" : renderFunc
		}
	});
}