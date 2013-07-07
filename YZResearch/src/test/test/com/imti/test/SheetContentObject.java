package test.com.imti.test;

import java.util.ArrayList;
import java.util.List;

public class SheetContentObject {

	private String warehouseId;
	private String warehouseName;
	private List<Object> detailList = new ArrayList<Object>();
	
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public List<Object> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<Object> detailList) {
		this.detailList = detailList;
	}
	
}
