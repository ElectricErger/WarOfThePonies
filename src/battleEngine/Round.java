package battleEngine;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import characters.BattleObject;
import characters.Boss;
import characters.MainCharacter;

public class Round {
	ArrayList<BattleObject> participants;
	ArrayList<Boss> opponents;
	ArrayList<BattleObject> party;
	//need a separate list of opponents and of protagonists
	
	private Round(ArrayList<Boss> e, ArrayList<BattleObject> p){
		party=p;
		opponents=e;
		ArrayList<BattleObject> part=new ArrayList();
		for(BattleObject main:p)part.add(main);
		for (Boss enemy:e)part.add(enemy);
		//since Boss is a subclass of BattleObject, as far as I can tell it should be okay to add, but Eclipse is whining??
		part=participants;
		
	}
	
	private ArrayList<BattleObject> order(){
		ArrayList<BattleObject> order=new ArrayList();
		for(BattleObject p:participants){
			Random r=new Random();
			int chance=r.nextInt(21);
			chance=chance+p.getSpeed();
			p.setInit(chance);
			order.add(p);
		}
		Collections.sort(order);
		return order;
	}
	int experience(ArrayList<Boss> enemies){
		//generates total exp value of battle
		int battleexp=0;
		for(Boss e:enemies){
			int n=e.getExp();
			battleexp=battleexp+n;
		}
		int divide=party.size();
		battleexp=battleexp/divide;
		return battleexp;
	}
	void award(int exp){
		for(BattleObject bob:participants){
			if(bob instanceof Boss==false){
				int current=bob.getExp();
				current=current+exp;
				bob.setExp(current);
				int needed=bob.getExpToNextLevel();
				needed=needed-exp;
				bob.setExpToNextLevel(needed);
			}
		}
	}
	//awards exp to each party member, regardless of whether or not they survived the battle
	void battle(){
		int exp=experience(opponents);
		ArrayList<BattleObject> order=order();
		while(party.isEmpty()==false&&opponents.isEmpty()==false){
			for(BattleObject fighter:order){
				boolean dead=fighter.getdefeated();
				if(dead){
					if(opponents.contains(fighter))opponents.remove(fighter);
					if(party.contains(fighter))party.remove(fighter);
				}
				else{
					battleMenu(fighter, g);
					//not sure where to create a graphics variable so this makes sense
					}
				}
			}
		for(BattleObject fighter: order){
			fighter.setInit(0);
			fighter.setdefeated(false);
		}
		award(exp);
	}
	void battleMenu(BattleObject fighter, Graphics g){
		if(fighter instanceof Boss ==false){
			String[] actions=new String[fighter.getAttacks().size()];
			int index=0;
			for(Action a:fighter.getAttacks()){
				String action=a.name+" MP cost "+a.cost;
				actions[index]=action;
				g.drawString(action, 0, 0+index*g.getFontMetrics().getHeight());
				//this is a placeholder menu, I wasn't sure where we wanted to position it, and have no idea how to actually do this
				index++;
			}
			//method for selecting an action
		}
		else{
			//need AI to make selection, drawing menu is not required, but creating an AI sure as hell is 	
		}
	}

}
