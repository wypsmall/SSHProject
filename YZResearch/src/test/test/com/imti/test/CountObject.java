package test.com.imti.test;

public class CountObject {

	private String warehouseId;
	private String merchandiseId;
	private String countNum = "0";
	
	public String getStock(){
		return merchandiseId + "_" + warehouseId;
	}
	public CountObject(String warehouseId, String merchandiseId, String countNum){
		this.warehouseId = warehouseId;
		this.merchandiseId = merchandiseId;
		this.countNum = countNum;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getMerchandiseId() {
		return merchandiseId;
	}
	public void setMerchandiseId(String merchandiseId) {
		this.merchandiseId = merchandiseId;
	}
	public String getCountNum() {
		return countNum;
	}
	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}
	
}
