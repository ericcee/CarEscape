package rushhour;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Car {
	private int yorig, xorig, x, y;
	private int lenght;
	private boolean rotation;
	private boolean isExitCar;
	private Color color;
	private Color oldColor;
	private int movesMade;
	private boolean selected;
	
	

	public Car(int x, int y, int lenght, boolean rotation, Color color) {
		super();
		this.x = x;
		this.y = y;
		xorig = x;
		yorig = y;
		this.lenght = lenght;
		this.rotation = rotation;
		this.color = color;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWindowX(int width){
		return (this.x) * (width/6);
	}
	
	public int getWindowY(int height){
		return (this.y) * (height/6);
	}
	
	public int getLenght() {
		return lenght;
	}
	
	public void setLenght(int lenght) {
		this.lenght = lenght;
	}
	
	public boolean isRotation() {
		return rotation;
	}
	
	public void setRotation(boolean rotation) {
		this.rotation = rotation;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		oldColor = this.color;
		this.color = color;
	}
	
	public int getWidth(int width){
		return (width/6) * (this.isRotation()?this.getLenght():1);
	}
	
	public int getHeight(int height){
		
		return (height/6) * (this.isRotation()?1:this.getLenght());
	}
	
	public int getCarWidth(int add){
		return (this.isRotation()?this.getLenght()-1+add:0);
	}
	
	public int getCarHeight(int add){
		return (this.isRotation()?0:this.getLenght()-1+add);
	}
	
	public Point getCoords(int add){
		return new Point(rotation?x+add:x, rotation?y:y+add);
	}
	
	public Point getCoordsWIthLenght(int add){
		return new Point(x+getCarWidth(add), y+getCarHeight(add));
	}
	
	public boolean carBlockedRight(ArrayList<Car> cars){
		boolean blocked = false;
		
		for(Car c : cars)  {
			if(c==this) continue;
			if(inLine(this, c, true)) blocked = true;
		}
		if(rotation && this.getX()+this.getCarWidth(0)>=5) blocked = true;
		else if(!rotation && this.getY()+this.getCarHeight(0)>=5) blocked = true;
		return blocked;
	}
	
	public boolean carBlockedLeft(ArrayList<Car> cars){
		boolean blocked = false;
		
		for(Car c : cars)  {
			if(c==this) continue;
			if(inLine(this, c, false)) blocked = true;
		}
		if(rotation && this.getX()<=0) blocked = true;
		else if(!rotation && this.getY()<=0) blocked = true;
		return blocked;
	}
	
	public void moveCarForward(){
		if(rotation) setX(getX()+1);
		else setY(getY()+1);
	}
	
	public void moveCarBackward(){
		if(rotation) setX(getX()-1);
		else setY(getY()-1);
	}
	
	private boolean inLine(Car A, Car B, boolean lf) {
		Point A2 = null;
		if(lf) A2 =  A.getCoordsWIthLenght(1);
		else A2 =  A.getCoords(-1);
		Point B1 = B.getCoords(0);
		Point B2 = B.getCoordsWIthLenght(0);
		return Line2D.linesIntersect(A2.getX(), A2.getY(), A2.getX(), A2.getY(), B1.getX(), B1.getY(), B2.getX(), B2.getY());
	}
	
	public boolean isExitCar() {
		return isExitCar;
	}

	public void setExitCar(boolean isExitCar) {
		this.isExitCar = isExitCar;
	}
	
	public int getMoves(){
		return movesMade;
	}
	
	public void moveMade(){
		movesMade++;
	}
	
	public Color getOldColor() {
		return oldColor;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void reset() {
		this.x = xorig;
		this.y = yorig;
		this.movesMade = 0;
	}
}
