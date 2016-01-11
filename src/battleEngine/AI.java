package battleEngine;

import java.util.ArrayList;

import characters.BattleObject;
import characters.Boss;

public class AI {
	BattleObject enemy;
	private AI(BattleObject a){
		enemy=a;
	}

	BattleObject lastAttacker(Round r){
		BattleObject target;
		int i=r.actions.size();
		do{
			i--;
			target=r.actions.get(i).doer;
		} while(r.actions.get(i).target!=enemy);
		return target;
	}
	ArrayList<Action> selectHeal(){
		ArrayList<Action> heals=new ArrayList();
		for(Action a:enemy.getAttacks()){
			if(a.type=="healing")heals.add(a);
		}
		return heals;
	}
	ArrayList<Action> selectNormal(){
		ArrayList<Action> attacks=new ArrayList<Action>();
		for (Action a:enemy.getAttacks()){
			if(a.type=="physical") attacks.add(a);
		}
		return attacks;
	}
	ArrayList<Action> selectMulti(){
		ArrayList<Action> attacks=new ArrayList<Action>();
		for (Action a:enemy.getAttacks()){
			if(a.type=="multi") attacks.add(a);
		}
		return attacks;
	}
	ArrayList<Action> selectSpell(){
		ArrayList<Action> attacks=new ArrayList<Action>();
		for (Action a:enemy.getAttacks()){
			if(a.type=="magicatk") attacks.add(a);
		}
		return attacks;
	}
	ArrayList<Action> sortByCost(ArrayList<Action> spells){
		Action a=spells.get(0);
		Action b=spells.get(1);
		ArrayList<Action> sorted= new ArrayList<Action>();
		sorted.add(a);
		if(a.cost<b.cost)sorted.add(0,b);
		else sorted.add(b);
		for(int d=2;d<spells.size()-1;d++){
			Action test=spells.get(d);
			int index=-1;
			Action compare=sorted.get(0);
			do{
				index++;
				compare=sorted.get(index);	
			}while(test.cost>compare.cost);
			sorted.add(index-1, test);
		}
		return sorted;
	}
	Action mostCostly(ArrayList<Action> spells){
		ArrayList<Action> sorted=sortByCost(spells);
		Action selection=sorted.get(0);
		for(int index=1;index<sorted.size();index++){
			if(selection.cost<=enemy.getMP()) break;
			else selection=sorted.get(index);
		}
		if(selection.cost>enemy.getMP()) selection=null;
		return selection;
	}
	Action leastCostly(ArrayList<Action> spells){
		ArrayList<Action> sorted=sortByCost(spells);
		Action selection=sorted.get(sorted.size()-1);
		return selection;
	}
	Action choose(Round r){
		Action selection=null;
		if (enemy.getHP()<=(enemy.getMaxHP()/3)){
			selection=mostCostly(selectHeal());
			if(selection==null){
				selection=mostCostly(selectNormal());
				selection.setTarget(lastAttacker(r));
			}
		}
		else{ 
			if(!(selectSpell().isEmpty())){
				selection=mostCostly(selectSpell());
			}
			else{
				
			}
		}
		
		if(selection==null){
			selection=mostCostly(selectNormal());
			selection.setTarget(lastAttacker(r));
		}
		return selection;
	}
	

}
