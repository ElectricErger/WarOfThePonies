package battleEngine;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import battleEngine.Action.BasicAttack;
import battleEngine.Action.MultiAttack;
import battleEngine.Spells.Heal;
import characters.BattleObject;
import characters.Boss;
import characters.MainCharacter;

public class Round {
	ArrayList<BattleObject> participants;
	ArrayList<Boss> opponents;
	ArrayList<BattleObject> party;
	
	private Round(ArrayList<Boss> e, ArrayList<BattleObject> p){
		party=p;
		opponents=e;
		ArrayList<BattleObject> part=new ArrayList();
		for(BattleObject main:p)part.add(main);
		for (Boss enemy:e)part.add(enemy);
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
			if(!(bob instanceof Boss)){
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
					Action selection;
					if(!(fighter instanceof Boss)){
						battleMenu(fighter);
						//method for selection action, returns Action selection
						selection=fighter.getAttacks().get(0);//placeholder
					}
					else{
						selection=fighter.getAttacks().get(0);//placeholder
						//AI method for selection action
					}
					execute(selection);					
				}
			}
		for(BattleObject fighter: order){
			fighter.setInit(0);
			fighter.setdefeated(false);
			}
		}
		award(exp);
	}
	void battleMenu(BattleObject fighter){
		//creates string array of possible actions and their costs, should be printed to player to choose from
		String[] actions=new String[fighter.getAttacks().size()];
		int index=0;
		for(Action a:fighter.getAttacks()){
			String action=a.name+" MP cost "+a.cost;
			actions[index]=action;
			index++;
		}
	}
	void execute(Action selection){
		//does whatever the selected action is supposed to do
		if(selection instanceof BasicAttack)((BasicAttack)selection).update();
		if(selection instanceof Heal)((Heal)selection).healingSpell();
		if(selection instanceof MultiAttack)((MultiAttack)selection).update(((MultiAttack) selection).attacks);
	}

}
