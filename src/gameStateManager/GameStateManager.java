package gameStateManager;

import java.awt.Graphics;


import characters.MainCharacter;
import mapEngine.Map;

public class GameStateManager {
	private GameState currentPlot;
	private int level;
	private Map loc;
	
	private MainCharacter player;
	
	public static final int TITLE = 0;
	public static final int INGAME = 1;
	public static final int GAMEOVER = 2;
	public static final int CREDITS = 3;
	//...
	
	public GameStateManager(){
		level = TITLE;
		currentPlot = new StartScreen(this);
	}
	
	/**
	 * Previous level trigger advances it by giving it to this method
	 */
	public void nextState(int lvl, GameState newState){
		level = lvl;
		currentPlot = newState;
	}
	
	public void setMainCharacter(MainCharacter c){
		player = c;
	}
	
	public void draw(Graphics g){
		currentPlot.draw(g);
	}
	
	public void keyDown(int key){
		currentPlot.keyDown(key);
	}
}
