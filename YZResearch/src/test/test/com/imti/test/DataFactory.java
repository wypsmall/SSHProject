package test.com.imti.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFactory {

	@SuppressWarnings("unchecked")
	public static List<Merchandise> getMerchandise(){
		
		List<Merchandise> merchaiseList = new ArrayList<Merchandise>();
		Merchandise merchaise1 = new Merchandise("1", "书籍");
		Merchandise merchaise2 = new Merchandise("2", "电脑");
		Merchandise merchaise3 = new Merchandise("3", "空调");
		Merchandise merchaise4 = new Merchandise("4", "茅台");
		Merchandise merchaise5 = new Merchandise("5", "矿泉水");
		Merchandise merchaise6 = new Merchandise("6", "月饼");
		Merchandise merchaise7 = new Merchandise("7", "风扇");
		Merchandise merchaise8 = new Merchandise("8", "空调");
		Merchandise merchaise9 = new Merchandise("9", "北京烤鸭");
		Merchandise merchaise10 = new Merchandise("10", "手机");
		merchaiseList.add(merchaise1);
		merchaiseList.add(merchaise2);
		merchaiseList.add(merchaise3);
		merchaiseList.add(merchaise4);
		merchaiseList.add(merchaise5);
		merchaiseList.add(merchaise6);
		merchaiseList.add(merchaise7);
		merchaiseList.add(merchaise8);
		merchaiseList.add(merchaise9);
		merchaiseList.add(merchaise10);
		
		return merchaiseList;
	}
	public static List<Warehouse> getWarehouse(){
		List<Warehouse> warehouseList = new ArrayList<Warehouse>();
		Warehouse warehouse1 = new Warehouse("1", "一号仓库");
		Warehouse warehouse2 = new Warehouse("2", "二号仓库");
		Warehouse warehouse3 = new Warehouse("3", "三号仓库");
		Warehouse warehouse4 = new Warehouse("4", "四号仓库");
		Warehouse warehouse5 = new Warehouse("5", "五号仓库");
		warehouseList.add(warehouse1);
		warehouseList.add(warehouse2);
		warehouseList.add(warehouse3);
		warehouseList.add(warehouse4);
		warehouseList.add(warehouse5);
		return warehouseList;
	}
	public static Map<String, String> getStock(){
		Map<String, String> stockMap = new HashMap<String, String>();
		
		CountObject count11 = new CountObject("1","1","1");
		CountObject count12 = new CountObject("1","2","2");
		CountObject count13 = new CountObject("1","3","3");
		CountObject count14 = new CountObject("1","4","4");
		CountObject count15 = new CountObject("1","5","5");
		CountObject count16 = new CountObject("1","6","6");
		CountObject count17 = new CountObject("1","7","7");
		CountObject count18 = new CountObject("1","8","8");
		CountObject count19 = new CountObject("1","9","9");
		CountObject count110 = new CountObject("1","10","10");
		
		CountObject count21 = new CountObject("2","1","1");
		CountObject count22 = new CountObject("2","2","2");
		CountObject count23 = new CountObject("2","3","3");
		CountObject count24 = new CountObject("2","4","4");
		CountObject count25 = new CountObject("2","5","5");
		CountObject count26 = new CountObject("2","6","6");
		CountObject count27 = new CountObject("2","7","7");
		CountObject count28 = new CountObject("2","8","8");
		CountObject count29 = new CountObject("2","9","9");
		CountObject count210 = new CountObject("2","10","10");
		
		CountObject count31 = new CountObject("3","1","1");
		CountObject count32 = new CountObject("3","2","2");
		CountObject count33 = new CountObject("3","3","3");
		CountObject count34 = new CountObject("3","4","4");
		CountObject count35 = new CountObject("3","5","5");
		CountObject count36 = new CountObject("3","6","6");
		CountObject count37 = new CountObject("3","7","7");
		CountObject count38 = new CountObject("3","8","8");
		CountObject count39 = new CountObject("3","9","9");
		CountObject count310 = new CountObject("3","10","10");
		
		CountObject count51 = new CountObject("5","1","1");
		CountObject count52 = new CountObject("5","2","2");
		CountObject count53 = new CountObject("5","3","3");
		CountObject count54 = new CountObject("5","4","4");
		CountObject count55 = new CountObject("5","5","5");
		CountObject count56 = new CountObject("5","6","6");
		CountObject count57 = new CountObject("5","7","7");
		CountObject count58 = new CountObject("5","8","8");
		CountObject count59 = new CountObject("5","9","9");
		CountObject count510 = new CountObject("5","10","10");
		
		stockMap.put(count11.getStock(), count11.getCountNum());
		stockMap.put(count12.getStock(), count12.getCountNum());
		stockMap.put(count13.getStock(), count13.getCountNum());
		stockMap.put(count14.getStock(), count14.getCountNum());
		stockMap.put(count15.getStock(), count15.getCountNum());
		stockMap.put(count16.getStock(), count16.getCountNum());
		stockMap.put(count17.getStock(), count17.getCountNum());
		stockMap.put(count18.getStock(), count18.getCountNum());
		stockMap.put(count19.getStock(), count19.getCountNum());
		stockMap.put(count110.getStock(), count110.getCountNum());
		stockMap.put(count21.getStock(), count21.getCountNum());
		stockMap.put(count22.getStock(), count22.getCountNum());
		stockMap.put(count23.getStock(), count23.getCountNum());
		stockMap.put(count24.getStock(), count24.getCountNum());
		stockMap.put(count25.getStock(), count25.getCountNum());
		stockMap.put(count26.getStock(), count26.getCountNum());
		stockMap.put(count27.getStock(), count27.getCountNum());
		stockMap.put(count28.getStock(), count28.getCountNum());
		stockMap.put(count29.getStock(), count29.getCountNum());
		stockMap.put(count210.getStock(), count210.getCountNum());
		stockMap.put(count31.getStock(), count31.getCountNum());
		stockMap.put(count32.getStock(), count32.getCountNum());
		stockMap.put(count33.getStock(), count33.getCountNum());
		stockMap.put(count34.getStock(), count34.getCountNum());
		stockMap.put(count35.getStock(), count35.getCountNum());
		stockMap.put(count36.getStock(), count36.getCountNum());
		stockMap.put(count37.getStock(), count37.getCountNum());
		stockMap.put(count38.getStock(), count38.getCountNum());
		stockMap.put(count39.getStock(), count39.getCountNum());
		stockMap.put(count310.getStock(), count310.getCountNum());
		stockMap.put(count51.getStock(), count51.getCountNum());
		stockMap.put(count52.getStock(), count52.getCountNum());
		stockMap.put(count53.getStock(), count53.getCountNum());
		stockMap.put(count54.getStock(), count54.getCountNum());
		stockMap.put(count55.getStock(), count55.getCountNum());
		stockMap.put(count56.getStock(), count56.getCountNum());
		stockMap.put(count57.getStock(), count57.getCountNum());
		stockMap.put(count58.getStock(), count58.getCountNum());
		stockMap.put(count59.getStock(), count59.getCountNum());
		stockMap.put(count510.getStock(), count510.getCountNum());
		return stockMap;
	}
	public static String getCharacters(int index){
		String baseChar = "C";
		if(index == 0){
			return "C";
		}else if(index == 1){
			return "D";
		}else if(index == 2){
			return "E";
		}else if(index == 3){
			return "F";
		}else if(index == 4){
			return "G";
		}else if(index == 5){
			return "H";
		}else if(index == 6){
			return "I";
		}else if(index == 7){
			return "J";
		}else if(index == 8){
			return "K";
		}else if(index == 9){
			return "L";
		}else if(index == 10){
			return "M";
		}else if(index == 11){
			return "N";
		}else if(index == 12){
			return "O";
		}else if(index == 13){
			return "P";
		}else if(index == 14){
			return "Q";
		}else if(index == 15){
			return "R";
		}else if(index == 16){
			return "S";
		}else if(index == 17){
			return "T";
		}else if(index == 18){
			return "U";
		}else if(index == 19){
			return "V";
		}else if(index == 20){
			return "W";
		}else if(index == 12){
			return "X";
		}else if(index == 13){
			return "Y";
		}else if(index == 14){
			return "Z";
		}
		return "A";
	}
	public static Map getSheetContent() {
		// TODO Auto-generated method stub
		return null;
	}
}
