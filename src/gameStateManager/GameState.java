package gameStateManager;

import java.awt.Graphics;

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	public GameState(GameStateManager g){
		gsm = g;
	}
	
	public abstract void draw(Graphics g);
	public abstract void keyDown(int key);	
	public void centerString(String s){}
	private void nextPoint(){ //To be made
		//gsm.nextState(null, null);
	}
	
	protected GameStateManager getGameStateManager(){
		return gsm;
	}

	public abstract void keyUp(int key);
}
