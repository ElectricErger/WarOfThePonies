package battleEngine;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import mapEngine.Map;
import characters.BattleObject;

public class Battle {
	
	private boolean inBattle;
	private BattleObject[] initiativeOrder;
	private BattleObject[] party;
	private BattleObject[] enemies;
	private BattleObject current;
	private int currentIndex;
	
	public void draw(Graphics g){
		
	}
	
	public void startBattle(BattleObject[] party, BattleObject[] enemies, Map location){
		this.party=party;
		this.enemies=enemies;
		inBattle=true;
		initiativeOrder=new BattleObject[enemies.length+party.length-2];
		int i=0;
		for(BattleObject b:party){
			initiativeOrder[i]=b;
			i++;
		}
		for(BattleObject b:enemies){
			initiativeOrder[i]=b;
			i++;
		}
		Arrays.sort(initiativeOrder);
		current=initiativeOrder[0];
		currentIndex=0;
	}
	public void endBattle(){
		int battleExp=0;
		for(BattleObject enemy: enemies){
			battleExp=battleExp+enemy.getExp();
		}
		battleExp=battleExp/party.length;
		for(BattleObject p:party){
			p.setExp(p.getExp()+battleExp);
			p.setExpToNextLevel(p.getExpToNextLevel()-battleExp);
		}
		inBattle=false;
	}
	private boolean isDefeated(BattleObject[] bo){
		for(BattleObject a:bo){
			boolean defeat=a.getdefeated();
			if (defeat=false) return false;
		}
		return true;
	}
	public void check(){
		if(isDefeated(enemies))endBattle();
		else{
			if(isDefeated(party)){
				//call game over
			}
			else nextAttacker();
		}
	}
	public void nextAttacker(){
		currentIndex++;
		current=initiativeOrder[currentIndex];
		round();
	}
	public void runAI(){
		AI enemy=new AI(current);
		Action attack=enemy.choose(current.getAttacks(), party);
		attack.execute();
		check();
		
	}
	public void runPlayer(){
		//UI
		attack.execute();
		check();
	}
	public void round(){
		for(BattleObject b:enemies){
			if(current==b){
				runAI();
				break;
			}
		}
		for(BattleObject b:party){
			if(current==b){
				runPlayer();
				break;
			}
		}
		nextAttacker();
	}
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

	public void keyUp(int key) {
		// TODO Auto-generated method stub
		
	}
}
