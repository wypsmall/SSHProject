 function checkRole(e,rsCode){
	    //ͨ��ajax�����ж��Ƿ��д˲���Ȩ��permissionVerify
	 if(parent && parent.permissionMap){
		 /*��������ҳ�Ѿ����������е�Ȩ��*/
		 if(parent.permissionMap.get(rsCode)){
			 e.show();
			 return true;
		 }else {
			 e.hide();
			 return false;
		 }
	 }else {
		 /*ǰ̨û�л���Ȩ��ʱ��ʵʱ�����̨��֤Ȩ��*/
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
				//û��ͨ��Ȩ����֤ʱ�����ذ�ť
				e.hide();
				return false;
			}
		});	
	 }
 }
//���ڿؼ� ����
Date.dayNames = ["��", "һ", "��", "��", "��", "��", "��"];
Date.monthNames=["1��","2��","3��","4��","5��","6��","7��","8��","9��","10��","11��","12��"];
if (Ext.DatePicker) {
        Ext.apply(Ext.DatePicker.prototype, {
            todayText: "����",
            minText: "��������С����֮ǰ",
            maxText: "�������������֮��",
            disabledDaysText: "",
            disabledDatesText: "",
            monthNames: Date.monthNames,
            dayNames: Date.dayNames,
            nextText: '���� (Control+Right)',
            prevText: '���� (Control+Left)',
            monthYearText: 'ѡ��һ���� (Control+Up/Down ���ı���)',
            todayTip: "{0} (Spacebar)",
            okText: "ȷ��",
            cancelText: "ȡ��",
            format: "y��m��d��"
        });
 }
 
 //��д����ȡֵ
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
 //��дradiogroup,checkboxgroupȡֵ   
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
	var new_year = year;    /*ȡ��ǰ�����*/ 
 	var new_month = month++;/*ȡ��һ���µĵ�һ�죬������㣨���һ�첻�̶���*/    
 	if(month>12){   
  		new_month -=12;        /*�·ݼ�  */  
  		new_year++;            /*����� */   
 	}   
 	var new_date = new Date(new_year,new_month,1);                //ȡ���굱���еĵ�һ��   
 	return (new Date(new_date.getTime()-1000*60*60*24)).getDate();//��ȡ�������һ������   
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
 		alert("�������ԣ�");
 		return ;
 	}
	var array = q_sm.getSelections();
	if (array.length != 0) {
		Ext.Msg.show({
			title:'��ʾ',
			msg:'���Ƿ�ȷ�ϴ˲�����  ',
			icon:Ext.MessageBox.QUESTION,
			width:200,
			buttons: {yes:'��', no: '��'},
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
	        					title: '��ʾ',
	        					msg: responseObj.data,
	        					modal: false,
	        					width:200,
	        					icon: Ext.Msg.INFO,
	        					buttons: {yes:'ȷ��'}
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
	        					title: '������ʾ',
	        					msg: '����ʧ��',
	        					modal: false,
	        					width:200,
	        					icon: Ext.Msg.ERROR,
	        					buttons: {yes:'ȷ��'}
	        				});
	        			}
	        		});
	            }
	        }
		});
	} else {
		alert('��ѡ����Ҫ�������У�');
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