package rushhour;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;



public class Converter {
	private ArrayList<Level> levels = new ArrayList<Level>();
	private InputStream csv = null;
	
	public Converter(InputStream csv) throws NumberFormatException, IOException{
		this.csv = csv;
		reload();
	}
	
	public void reload() throws NumberFormatException, IOException{
		levels = new ArrayList<Level>();
		BufferedReader br = new BufferedReader(new InputStreamReader(csv)) ;
		String line;
		while ((line = br.readLine()) != null) {
			if(line=="") continue;
			Map thm = null;
			ArrayList<Car> cars = new ArrayList<Car>();
			String[] columns = line.split(",");
			int Nr = Integer.parseInt(columns[0]);
			int Level = Integer.parseInt(columns[1]);
			int MaxMoves = Integer.parseInt(columns[2]);
			
			Car startcar = getCarFromCoords(Integer.parseInt(columns[3]), Integer.parseInt(columns[4]), true);
			cars.add(startcar);
			
			for(int i = 5; i < 35; i+=2){
				int pos1 = Integer.parseInt(columns[i]);
				int pos2 = Integer.parseInt(columns[i+1]);
				if(pos1 == -1) break;
				cars.add(getCarFromCoords(pos1, pos2, false));
			}
			
			thm = new Map(Nr, Level, cars, MaxMoves);
			Level thl = getLevel(Level);
			thl.addMap(thm);
		}
	}
	
	private Level getLevel(int Level){
		Level rele = null;
		for(Level l : levels) if(l.getLevelNr() == Level) rele = l; // level exists
		if(rele==null) {
			rele = new Level(Level); // new level creation
			levels.add(rele);
		}
		return rele;
	}
	
	private Car getCarFromCoords(int pos1, int pos2, boolean isStartCar){
		Point startPos = new Point((int)pos1%6, (int)pos1/6);
		Point endPos = new Point((int)pos2%6,  (int)pos2/6);
		boolean Rotation = startPos.getX() == endPos.getX();
		int Lenght = 0;
		if(Rotation) Lenght = (int) (endPos.getY() - startPos.getY());
		else Lenght = (int) (endPos.getX() - startPos.getX());
		
		Lenght++;
		
		Car c = new Car((int)startPos.getX(), (int)startPos.getY(), Lenght, !Rotation, isStartCar?Color.RED:Color.CYAN);
		c.setExitCar(isStartCar);
		return c;
	}
	
	public ArrayList<Level> getLevels(){
		return levels;
	}
}
