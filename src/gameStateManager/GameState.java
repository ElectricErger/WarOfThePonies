package gameStateManager;

import java.awt.Graphics;

public abstract class GameState {
	
	public abstract void draw(Graphics g);
	public abstract void keyDown(int key);	
	public void centerString(String s){}
}
