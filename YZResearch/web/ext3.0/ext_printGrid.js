function downloadViewData(grid) {
	try {
		var xls = new ActiveXObject("Excel.Application");
   	} catch (e) {
    	alert("Ҫ��ӡ�ñ������밲װExcel���ӱ�������ͬʱ�������ʹ�á�ActiveX �ؼ��������������������ִ�пؼ��� �������������˽���������÷�����");
    	return "";
   	}
   	var cm = grid.getColumnModel();
   	var colCount = cm.getColumnCount();
   	xls.visible = true; // ����excelΪ�ɼ�
   	var xlBook = xls.Workbooks.Add;
   	var xlSheet = xlBook.Worksheets(1);
   	var temp_obj = [];
   	// ֻ����û�����ص���(isHidden()Ϊtrue��ʾ����,������Ϊ��ʾ)
   	// ��ʱ����,������е�ǰ��ʾ�е��±�
   	for (i = 2; i < colCount; i++) {
   		if (cm.isHidden(i) == true) {
    	} else {
     		temp_obj.push(i);
    	}
   	}
   	for (i = 1; i <= temp_obj.length; i++) {
    	// ��ʾ�е��б���
    	//alert(cm.getColumnHeader(temp_obj[i - 1]));
    	xlSheet.Cells(1, i).Value = cm.getColumnHeader(temp_obj[i - 1]);
   	}
   	var store = grid.getStore();
   	var recordCount = store.getCount();
   	var view = grid.getView();
   	for (i = 1; i <= recordCount; i++) {
    	for (j = 1; j <= temp_obj.length; j++) {
     		// EXCEL���ݴӵڶ��п�ʼ,��row = i + 1;
     		xlSheet.Cells(i + 1, j).Value = view.getCell(i - 1, temp_obj[j- 1]).innerText;
    	}
   	}
   	xlSheet.Columns.AutoFit;
   	xls.ActiveWindow.Zoom = 75
   	xls.UserControl = true; // ����Ҫ,����ʡ��,��Ȼ������� ��˼��excel�����û�����
   	xls = null;
   	xlBook = null;
   	xlSheet = null;
}
function printDataNoCheckBox(grid) {
	try {
		var xls = new ActiveXObject("Excel.Application");
   	} catch (e) {
    	alert("Ҫ��ӡ�ñ������밲װExcel���ӱ�������ͬʱ�������ʹ�á�ActiveX �ؼ��������������������ִ�пؼ��� �������������˽���������÷�����");
    	return "";
   	}
   	var cm = grid.getColumnModel();
   	var colCount = cm.getColumnCount();
   	xls.visible = true; // ����excelΪ�ɼ�
   	var xlBook = xls.Workbooks.Add;
   	var xlSheet = xlBook.Worksheets(1);
   	var temp_obj = [];
   	// ֻ����û�����ص���(isHidden()Ϊtrue��ʾ����,������Ϊ��ʾ)
   	// ��ʱ����,������е�ǰ��ʾ�е��±�
   	for (i = 1; i < colCount; i++) {
   		if (cm.isHidden(i) == true) {
    	} else {
     		temp_obj.push(i);
    	}
   	}
   	for (i = 1; i <= temp_obj.length; i++) {
    	// ��ʾ�е��б���
    	//alert(cm.getColumnHeader(temp_obj[i - 1]));
    	xlSheet.Cells(1, i).Value = cm.getColumnHeader(temp_obj[i - 1]);
   	}
   	var store = grid.getStore();
   	var recordCount = store.getCount();
   	var view = grid.getView();
   	for (i = 1; i <= recordCount; i++) {
    	for (j = 1; j <= temp_obj.length; j++) {
     		// EXCEL���ݴӵڶ��п�ʼ,��row = i + 1;
     		xlSheet.Cells(i + 1, j).Value = view.getCell(i - 1, temp_obj[j- 1]).innerText;
    	}
   	}
   	xlSheet.Columns.AutoFit;
   	xls.ActiveWindow.Zoom = 75
   	xls.UserControl = true; // ����Ҫ,����ʡ��,��Ȼ������� ��˼��excel�����û�����
   	xls = null;
   	xlBook = null;
   	xlSheet = null;
}

