package rushhour;

import java.util.ArrayList;

public class Map {
	private ArrayList<Car> cars = null;
	private int Nr = 0;
	
	public Map(int Nr, ArrayList<Car> cars){
		this.cars = cars;
		this.Nr = Nr;
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
}
