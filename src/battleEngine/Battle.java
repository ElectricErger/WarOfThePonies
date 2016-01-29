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
	private ArrayList<BattleObject> party;
	private ArrayList<BattleObject> enemies;
	
	public void draw(Graphics g){
		
	}
	
	public void startBattle(ArrayList<BattleObject> party, ArrayList<BattleObject> enemies, Map location){
		this.party=party;
		this.enemies=enemies;
		inBattle=true;
		initiativeOrder=new BattleObject[enemies.size()+party.size()-2];
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
	}
	public void endBattle(){
		int battleExp=0;
		for(BattleObject enemy: enemies){
			battleExp=battleExp+enemy.getExp();
		}
		battleExp=battleExp/party.size();
		for(BattleObject p:party){
			p.setExp(p.getExp()+battleExp);
			p.setExpToNextLevel(p.getExpToNextLevel()-battleExp);
		}
	}
	public void selectAction(ArrayList<Action> menu, int i){
		menu.get(i).execute();
	}
	public BattleObject nextAttacker(int a){
		BattleObject next=initiativeOrder[a+1];
		return next;
	}
	public void round(BattleObject current){
		if(enemies.contains(current)){
			AI enemy=new AI(current);
			Action attack=enemy.choose(current.getAttacks(), party);
			attack.execute();
			int a=0;
			for(BattleObject b: initiativeOrder){
				if(b==initiativeOrder[a])break;
				else a++;
			}
			round(nextAttacker(a));
		}
		else {
			Menu menu=new Menu(current);
			//Make a stupid menu to select from
		}
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
