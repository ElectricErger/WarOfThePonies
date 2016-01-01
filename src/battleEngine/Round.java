package battleEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import characters.BattleObject;
import characters.Boss;
import characters.MainCharacter;

public class Round {
	ArrayList<BattleObject> participants;
	ArrayList<BattleObject> opponents;
	ArrayList<BattleObject> party;
	//need a separate list of opponents and of protagonists
	
	private Round(ArrayList<BattleObject> e, ArrayList<BattleObject> p){
		party=p;
		opponents=e;
		ArrayList<BattleObject> part=p;
		for(BattleObject b:e){part.add(b);}
		
	}
	
	private ArrayList<BattleObject> order(){
		ArrayList<BattleObject> order=new ArrayList();
		for(BattleObject p:participants){
			Random r=new Random();
			int chance=r.nextInt(21);
			chance=chance+p.getSpeed();
			p.initiative=chance;
			order.add(p);
		}
		Collections.sort(order);
		return order;
	}
	//need method to generate experience points
	//separate method to award experience - exp should probably be generated before the battle while there is an easy list of opponents being fought to use to generate it
	void battle(){
		ArrayList<BattleObject> order=order();
		while(party.isEmpty()==false&&opponents.isEmpty()==false){
			for(BattleObject fighter:order){
				boolean dead=fighter.getdefeated();
				if(dead){
					if(opponents.contains(fighter))opponents.remove(fighter);
					if(party.contains(fighter))party.remove(fighter);
				}
				else{
					//need to take an action, somehow
				}
			}
		}
		//
	}

}
