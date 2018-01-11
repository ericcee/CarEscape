package rushhour;

import java.util.ArrayList;

public class Rushhour {
	private Map thisMap = null;

	public ArrayList<Car> getCars() {
		return thisMap.getCars();
	}

	public void addCar(Car car) {
		this.thisMap.getCars().add(car);
	}
	
	public int getAllMoves(){
		int moves = 0;
		for(Car c : thisMap.getCars()){
			moves+=c.getMoves();
		}
		return moves;
	}
	
	public boolean isPuzzleSolved(){
		boolean reurncode = false;
		for(Car c : thisMap.getCars()){
			if(c.isExitCar()){
				if(c.getX()+c.getCarWidth(1) >= 6) reurncode = true;
			}
		}
		return reurncode;
	}
	public void setMap(Map map){
		thisMap = map;
	}
	public int getMaxMoves(){
		return thisMap.getMaxMoves();
	}
}
