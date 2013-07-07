package test.com.imti.test;

public class Merchandise {

	private String merchandiseId;
	private String merchandiseName;
	
	public Merchandise(String merchandiseId, String merchandiseName){
		this.merchandiseId = merchandiseId;
		this.merchandiseName = merchandiseName;
	}
	
	public String getMerchandiseId() {
		return merchandiseId;
	}
	
	public void setMerchandiseId(String merchandiseId) {
		this.merchandiseId = merchandiseId;
	}
	public String getMerchandiseName() {
		return merchandiseName;
	}
	public void setMerchandiseName(String merchandiseName) {
		this.merchandiseName = merchandiseName;
	}
	
}
