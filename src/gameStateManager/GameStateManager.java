package gameStateManager;

import java.awt.Graphics;


import characters.MainCharacter;
import mapEngine.Map;

public class GameStateManager {
	private GameState currentPlot;
	
	public static final int TITLE = 0;
	public static final int INGAME = 1;
	public static final int GAMEOVER = 2;
	public static final int CREDITS = 3;
	//...
	
	public GameStateManager(){
		currentPlot = new StartScreen(this);
	}
	
	/**
	 * Previous level trigger advances it by giving it to this method
	 */
	public void nextState(GameState newState){
		currentPlot = newState;
	}
	
	public void draw(Graphics g){ currentPlot.draw(g); }
	public void keyDown(int key){ currentPlot.keyDown(key); }
	public void keyUp(int key){ currentPlot.keyUp(key); }
}
