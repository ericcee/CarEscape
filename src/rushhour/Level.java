package rushhour;

import java.util.ArrayList;

public class Level {
	private ArrayList<Map> maps = new ArrayList<Map>();
	private int LevelNr = 0;
	
	public Level(ArrayList<Map> maps, int levelNr){
		this.maps = maps;
		this.LevelNr = levelNr;
	}
	
	public Level(int levelNr){
		this.LevelNr = levelNr;
	}
	
	public void addMap(Map m){
		this.maps.add(m);
	}
	
	public Map getMap(int nr){
		Map map = null;
		for(Map m : maps) if(m.getNr()==nr) map = m;
		return map;
	}
	
	public Map getMapByIndex(int index){
		return maps.get(index);
	}
	
	public ArrayList<Map> getMaps(){
		return this.maps;
	}
	
	public int getLevelNr(){
		return LevelNr;
	}
	
	@Override
	public String toString(){
		return "Level "+LevelNr;
	}
}
