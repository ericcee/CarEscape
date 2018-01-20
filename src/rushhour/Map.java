package rushhour;

import java.util.ArrayList;

public class Map {
	private ArrayList<Car> cars = null;
	private int Nr = 0;
	private int MaxMoves = 0;
	private int level = 0;
	
	public Map(int Nr, int level, ArrayList<Car> cars, int MaxMoves){
		this.cars = cars;
		this.Nr = Nr;
		this.MaxMoves = MaxMoves;
		this.level = level;
	}

	public ArrayList<Car> getCars() {
		return cars;
	}

	public int getNr() {
		return Nr;
	}
	
	@Override
	public String toString(){
		return "Map " + Nr;
	}
	
	public void reset() {
		for(Car c : cars) c.reset();
	}
	
	public int getMaxMoves(){
		return MaxMoves;
	}
	
	public int getLevel(){
		return level;
	}
}
