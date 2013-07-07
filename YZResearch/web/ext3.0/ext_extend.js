 function checkRole(e,rsCode){
	    //通过ajax请求，判断是否有此操作权限permissionVerify
	 if(parent && parent.permissionMap){
		 /*现在在首页已经缓存了所有的权限*/
		 if(parent.permissionMap.get(rsCode)){
			 e.show();
			 return true;
		 }else {
			 e.hide();
			 return false;
		 }
	 }else {
		 /*前台没有缓存权限时，实时请求后台验证权限*/
		 Ext.Ajax.request({
			url :context + '/sysmgr/resources.do?method=permissionVerify&rsCode='+rsCode,
			success : function(response, options) {
		          	var responseObject = Ext.util.JSON.decode(response.responseText);
				if(responseObject.data){
					e.show();
					return true;
				}else {
					e.hide();
					return false;
				}
			},
			failure : function(response, options) {
				//没有通过权限验证时，隐藏按钮
				e.hide();
				return false;
			}
		});	
	 }
 }
//日期控件 汉化
Date.dayNames = ["日", "一", "二", "三", "四", "五", "六"];
Date.monthNames=["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"];
if (Ext.DatePicker) {
        Ext.apply(Ext.DatePicker.prototype, {
            todayText: "今天",
            minText: "日期在最小日期之前",
            maxText: "日期在最大日期之后",
            disabledDaysText: "",
            disabledDatesText: "",
            monthNames: Date.monthNames,
            dayNames: Date.dayNames,
            nextText: '下月 (Control+Right)',
            prevText: '上月 (Control+Left)',
            monthYearText: '选择一个月 (Control+Up/Down 来改变年)',
            todayTip: "{0} (Spacebar)",
            okText: "确定",
            cancelText: "取消",
            format: "y年m月d日"
        });
 }
 
 //重写方便取值
 /***
Ext.override(Ext.form.RadioGroup,{
getValue: function() {var v;
this.items.each(function(item) {
if ( item.getValue() ) {
 v = item.getRawValue();
 return false;}
 });
 return v;}})
 **/
 //重写radiogroup,checkboxgroup取值   
 Ext.override(Ext.form.BasicForm,{        
     findField : function(id){                
         var field = this.items.get(id);                
         if(!field){        
             this.items.each(function(f){        
                 if(f.isXType('radiogroup')||f.isXType('checkboxgroup')){        
                     f.items.each(function(c){        
                        if(c.isFormField && (c.dataIndex == id || c.id == id || c.getName() == id)){        
                            field = c;        
                            return false;        
                        }        
                    });        
                }        
                                        
               if(f.isFormField && (f.dataIndex == id || f.id == id || f.getName() == id)){        
                    field = f;        
                   return false;        
               }        
            });        
        }        
        return field || null;        
  }}        
) 
function getFormatDate(type){
	var curDate = new Date();
	var curMonth = ((curDate.getMonth()+1)>12?12:(curDate.getMonth()+1)) + "";
	if(curMonth.length==1){
		curMonth = "0"+curMonth;
	}
	if(type=="start"){
		return curDate.getYear() + "-" + curMonth + "-01";
	}else if(type=="end"){
		return getLastDay(curDate.getYear(),curMonth);
	}
}
function getLastDay(year,month){   
	var new_year = year;    /*取当前的年份*/ 
 	var new_month = month++;/*取下一个月的第一天，方便计算（最后一天不固定）*/    
 	if(month>12){   
  		new_month -=12;        /*月份减  */  
  		new_year++;            /*年份增 */   
 	}   
 	var new_date = new Date(new_year,new_month,1);                //取当年当月中的第一天   
 	return (new Date(new_date.getTime()-1000*60*60*24)).getDate();//获取当月最后一天日期   
}
function getCurMonth(){
	var curDate = new Date();
	var curMonth = ((curDate.getMonth()+1)>12?12:(curDate.getMonth()+1)) + "";
	if(curMonth.length==1){
		curMonth = "0"+curMonth;
	}
	return curDate.getYear() + "-" + curMonth;
}
function getCurDate(){
	var curDate = new Date();
	var curMonth = ((curDate.getMonth()+1)>12?12:(curDate.getMonth()+1)) + "";
	if(curMonth.length==1){
		curMonth = "0"+curMonth;
	}
	var curDay = curDate.getDate()>9?curDate.getDate():("0"+curDate.getDate());
	return curDate.getYear() + "-" + curMonth + "-" + curDay;
}
function operatePkAction(q_sm,pk,statusName,statusValue, msg, url, paramName,acMethod, rsCode, callBackFunc, uri){

 	if(arguments.length < 10){
 		alert("参数不对！");
 		return ;
 	}
	var array = q_sm.getSelections();
	if (array.length != 0) {
		Ext.Msg.show({
			title:'提示',
			msg:'您是否确认此操作？  ',
			icon:Ext.MessageBox.QUESTION,
			width:200,
			buttons: {yes:'是', no: '否'},
			fn: function(btn){
	            if(btn == 'yes'){
	            	var i = 0;
	        		var tempParams = "";
	        		while (i != array.length) {
	        			if(array[i].get(pk) == "" || array[i].get(pk) == 'undefined'){
	        				i++;
	        				continue;
	        			}
	        			if(statusName != ""){
	        				var statusFlag = array[i].get(statusName);
	        				if(statusValue != statusFlag){
	        					alert(msg);
	        					return ;
	        				}
	        			}
	        			if (tempParams == "") {
	        				tempParams = array[i].get(pk);
	        			} else {
	        				tempParams = tempParams + "," + array[i].get(pk);
	        			}
	        			i++;
	        		}
	        		url = url + '?' + paramName + '=' + tempParams;
	        		if(uri != undefined && null != uri && uri != "" ){
	        			url = url + '&' + uri;
	        		}
	        		Ext.Ajax.request({
	        			url : url,
	        			params : {
	        				method : acMethod,
	        				rsCode : rsCode
	        			},
	        			success : function(resp) {
	        				var responseObj = Ext.util.JSON.decode(resp.responseText);
	        			 	Ext.Msg.show({
	        					title: '提示',
	        					msg: responseObj.data,
	        					modal: false,
	        					width:200,
	        					icon: Ext.Msg.INFO,
	        					buttons: {yes:'确定'}
	        				});
	        				if(responseObj.success == false){
	        					return;
	        				}
	        				if(callBackFunc){
	        					callBackFunc();
	        				}
	        			},
	        			failure : function(form, action) {
	        				Ext.Msg.show({
	        					title: '错误提示',
	        					msg: '操作失败',
	        					modal: false,
	        					width:200,
	        					icon: Ext.Msg.ERROR,
	        					buttons: {yes:'确定'}
	        				});
	        			}
	        		});
	            }
	        }
		});
	} else {
		alert('请选择你要操作的行！');
	}
 }
 function getSelectedRow(q_sm,field){
	var array = q_sm.getSelections();
	var i = 0;
	var ids = "";
	while (i != array.length) {
		if (ids == "") {
			ids = array[i].get(field);
		} else {
			ids = ids + "," + array[i].get(field);
		}
		i++;
	}
	return ids;
}
 function getExtValue(extId){
 	if(Ext.getCmp(extId)){
 		return Ext.getCmp(extId).getValue();
 	}
 	return;
 }