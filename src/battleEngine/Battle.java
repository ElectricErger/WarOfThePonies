package battleEngine;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import mapEngine.Map;
import characters.BattleObject;

public class Battle {
	
	private boolean inBattle;
	
	
	public void draw(Graphics g){
		
	}
	
	public void startBattle(ArrayList<BattleObject> party, Map location){}
	
	public boolean inBattle(){ return inBattle; }
	
	public void keyDown(int key){
		switch(key){
		case KeyEvent.VK_UP:
			upResponse();
			break;
		case KeyEvent.VK_DOWN:
			downResponse();
			break;
		case KeyEvent.VK_LEFT:
			leftResponse();
			break;
		case KeyEvent.VK_RIGHT:
			rightResponse();
			break;
		case KeyEvent.VK_SPACE:
			forwardResponse();
			break;
		case KeyEvent.VK_BACK_SPACE:
			backwardResponse();
			break;
		}
	}

	private void upResponse(){}
	private void downResponse(){}
	private void leftResponse(){}
	private void rightResponse(){}
	private void forwardResponse(){}
	private void backwardResponse(){}
}
