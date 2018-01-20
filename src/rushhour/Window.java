package rushhour;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Window extends JFrame {
	
	private GameGraphics graph ;
	private boolean carGotSelected = false;
	private int startx = 0, starty = 0;
	private Car selectedCar = null;
	private Rushhour rh = new Rushhour();
	private int width = 512, height = 512;
	private Window Window = null;
	private int lastLpos = 0;
	private int top = 25;
	private Point oldpos = null;
	
	
	public Window(String title, Map map) throws HeadlessException {
		super(title+", "+map);
		this.setSize(width, height + top + 3);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		
		this.graph = new GameGraphics(512, 512);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		loadMap(map);
		Window = this;
		
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				map.reset();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
				if(carGotSelected){
					carGotSelected = false;
					if(!oldpos.equals(selectedCar.getCoords(0))) selectedCar.moveMade(); // only if car got moved
					selectedCar.setSelected(false);
					Window.setTitle("Rush Hour, Moves made: " + rh.getAllMoves()+", Max Moves: " +rh.getMaxMoves()+", "+map);
					drawField();
					Window.setCursor(Cursor.DEFAULT_CURSOR);
					if(rh.isPuzzleSolved()){
						infoBox("Moves made: " + rh.getAllMoves(), "Puzzle ended");
						map.reset();
						
						Map next = main.converter.getLevels().get(map.getLevel()-1).getMap(map.getNr()+1);
						if(next!=null) (new Window("Rushhour", next)).showWindow();
						
						Window.dispose();
					}
					
					selectedCar = null;
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				startx = e.getX();
				starty = e.getY() - top;
				
				for(Car c: rh.getCars()){
					
					boolean topok = (c.getWindowX(width) < startx && c.getWindowY(height) < starty);
					boolean hhok = ((c.getWindowX(width) + c.getWidth(width)) > startx && (c.getWindowY(height) + c.getHeight(height)) > starty);
					
					if( topok && hhok ){ // car got selected
						Window.setCursor(Cursor.MOVE_CURSOR);
						carGotSelected = true;
						selectedCar = c;
						oldpos = selectedCar.getCoords(0);
						if(selectedCar.isRotation()) lastLpos = startx;
						else lastLpos = starty;
						selectedCar.setSelected(true);
						drawField();
						break;
					}
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				int tx = e.getX();
				int ty = e.getY()-top;
				boolean carl = false;
				for(Car c: rh.getCars()){
					boolean topok = (c.getWindowX(width) < tx && c.getWindowY(height) < ty);
					boolean hhok = ((c.getWindowX(width) + c.getWidth(width)) > tx && (c.getWindowY(height) + c.getHeight(height)) > ty);
					if(topok && hhok) {
						carl=true;
					}
					
				}
				if(carl) {
					if(Window.getCursor() != new Cursor(Cursor.HAND_CURSOR)) Window.setCursor(Cursor.HAND_CURSOR);
				}
				else {
					if(Window.getCursor() != new Cursor(Cursor.DEFAULT_CURSOR)) Window.setCursor(Cursor.DEFAULT_CURSOR);
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				mouseController(e);
			}
		});
	}
	
	private void mouseController(MouseEvent e){
		
		if(carGotSelected){
			int lpos = 0;
			int divvar = 0;
			if(selectedCar.isRotation()){
				lpos = e.getX();
				divvar = width;
			}
			else {
				lpos = e.getY() - top;
				divvar = height;
			}
			
			if((lpos - lastLpos) > divvar/6 && !selectedCar.carBlockedRight(rh.getCars())){ // right
				lastLpos = lpos;
				selectedCar.moveCarForward();
				drawField();
			}
			else if((lpos - lastLpos) < -divvar/6 && !selectedCar.carBlockedLeft(rh.getCars())){ // left
				lastLpos = lpos;
				selectedCar.moveCarBackward();
				drawField();
			}
		}
	}
	
	
	public void showWindow(){
		this.setVisible(true);
		
		drawField();
	}
	
	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);
		drawField();
	}
	
	private void drawField(){
		graph.drawBackground();
		for(Car c : rh.getCars()){
			graph.drawCar(c);
		}
		this.getGraphics().drawImage(graph.getImage(), 0, top, null);
	}
	
	public void loadMap(Map m){
		rh.setMap(m);
	}
	
	private void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
