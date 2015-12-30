package gameStateManager;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class gamePlay extends GameState{

	public gamePlay(GameStateManager g){
		super(g);
		//Create a map
		//Create main character
		
		
		//For now we are testing the text engine
	}
	
	@Override
	public void draw(Graphics g) {
		
	}

	@Override
	public void keyDown(int key) {
		switch(key){
		case KeyEvent.VK_UP:
			break;
		case KeyEvent.VK_DOWN:
			break;
		case KeyEvent.VK_LEFT:
			break;
		case KeyEvent.VK_RIGHT:
			break;
		case KeyEvent.VK_SPACE:
			break;
		case KeyEvent.VK_BACK_SPACE:
			break;
		}
	}
	
}
