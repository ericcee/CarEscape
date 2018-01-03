package rushhour;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.awt.geom.RoundRectangle2D;

public class GameGraphics {
	private Graphics2D graphics;
	private int width, height;
	private Image toDraw = null;
	
	public GameGraphics(int width, int height) {
		this.width = width;
		this.height = height;
		toDraw = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		graphics = (Graphics2D) toDraw.getGraphics();
	}
	
	public void drawCar(Car car){
		int blockw = width / (6);
		int blockh = height / (6);
		int drawPosX = blockw * car.getX() + (car.isRotation() ? 2:6);
		int drawPosY = blockh * car.getY() + (car.isRotation() ? 6:2);
		int drawWidth = blockw * (car.isRotation()?car.getLenght():1) - (car.isRotation()? 4:12);
		int drawHeight = blockh * (car.isRotation()?1:car.getLenght()) - (car.isRotation()? 12:4);
		
		if(car.isSelected()) {
			Color gfx = (Color.yellow);
			Random rnd = new Random();
			if(rnd.nextBoolean()) {
				for(int i = 0; i < rnd.nextInt(2);i++) gfx = gfx.brighter();
			}
			else{
				for(int i = 0; i < rnd.nextInt(2);i++) gfx = gfx.darker();
			}
			graphics.setColor(gfx);
		}
		
		else graphics.setColor(Color.white);
		graphics.fill(new RoundRectangle2D.Float(drawPosX, drawPosY, drawWidth, drawHeight, 15, 80)); // lights
		
		graphics.setColor(car.getColor());
		graphics.fill(new RoundRectangle2D.Float(drawPosX, drawPosY, drawWidth, drawHeight, 50, 75)); // car
		
		graphics.setStroke(new BasicStroke(2));
		graphics.setColor(Color.DARK_GRAY);
		graphics.draw(new RoundRectangle2D.Float(drawPosX, drawPosY, drawWidth, drawHeight, 15, 80)); //lights
		graphics.draw(new RoundRectangle2D.Float(drawPosX, drawPosY, drawWidth, drawHeight, 50, 75)); 
	}
	
	public void drawBackground(){
		graphics.setColor(Color.GRAY);
		graphics.fillRect(0, 0, this.width, this.height);
	}
	
	public Image getImage(){
		return toDraw;
	}
	
}
