package gameStateManager;

import java.awt.Graphics;

import javax.swing.JPanel;

import map_Engine.Map;

public class GameStateManager {
	private GameState currentPlot;
	private int level;
	private Map loc;
	private JPanel context;
	
	public static final int TITLE = 0;
	public static final int CHAPTER1 = 1;
	public static final int CHAPTER2 = 2;
	public static final int CHAPTER3 = 3;
	//...
	
	public GameStateManager(JPanel p){
		level = TITLE;
		currentPlot = new StartScreen(this);
		context = p;
	}
	
	/**
	 * Previous level trigger advances it by giving it to this method
	 */
	public void nextState(int lvl, GameState newLevel){
		level = lvl;
		currentPlot = newLevel;
	}
	
	public int level(){
		return level;
	}
	
	public void setMap(Map l){
		loc = l;
	}
	
	public Map getMap(){
		return loc;
	}
	
	public JPanel getPanel(){
		return context;
	}
	
	public void draw(Graphics g){
		currentPlot.draw(g);
	}
	
	public void keyDown(int key){
		currentPlot.keyDown(key);
	}
}
