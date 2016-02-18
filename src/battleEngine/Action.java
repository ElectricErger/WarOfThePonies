package battleEngine;

import java.util.ArrayList;
import java.util.Random;

import battleEngine.Action.BasicAttack;
import battleObjects.Weapon;
import characters.BattleObject;

public abstract class Action {
	String name;
	BattleObject doer;
	BattleObject target;
	Weapon w; //Just temporary, we'll deal with this later
	boolean magic=false;
	//default is false, true if move is magical
	int cost=0;
	//magic effects will have an MP cost
	Random r;
	//element of randomness associated with damage, accuracy, crits, healing etc
	int type;
	//1=healing, 2=physical(single target), 3=multi, 4=buff/debuff, 5=magicatk
	public abstract void execute();
	public void setTarget(BattleObject defender){target=defender;}
	class BasicAttack extends Action {
		int damage=0;
		boolean hit=false;
		boolean crit=false;
		private BasicAttack(BattleObject attacker, BattleObject defender){
			doer=attacker;
			target=defender;
			name="Basic Attack";
			type=2;
		}
			private void accuracy(){
			int roll=r.nextInt(21);
			if(roll+doer.getAccuracy()>target.getDefence()) hit=true;
			//determines if a non-magical attack hits; 
			//defence should have a base level of about 10, modified depending on armor, and possibly speed/luck
			}

		private void critical(){
			int chance=r.nextInt(21);
			if((chance+doer.getLuck())*4>=80) crit=true;
			//not sure if this will be too many or too few crits
			//basically crit ~5% of time with a luck of 0, using a random number generator
		}
		private void damage(Weapon w){
			//get one random amount of damage for each point of attack
			//damage between 1 and max amount based on weapon, I suggest 4 if bare=hooved
			//should call weapon that the doer is using - battle object needs a weapon field eventually
			for(int a=doer.getAttack(); a>0; a--){
				int dmg=r.nextInt(w.damagedie())+1;
				damage=damage+dmg;
			}
			if(crit){
				int multiplier=2+(doer.getLevel()/4);
				damage=damage*multiplier;
			}
			
		}
		public void execute(){
			accuracy();
			if(hit){
				critical();
				damage(w);
				int start=target.getHP();
				start=start-damage;
				target.setHP(start);
				if(target.getHP()<=0) {
					target.setDefeated(true);
				}
				
			}
			
		}
	}
	class MultiAttack extends Action{
		BasicAttack[] attacks;
		int numberofTargets;
		private MultiAttack(int number, BattleObject[] targets, BattleObject attacker){	
			name="Multi Attack";
			//cost=some cost of using multi attack, add an int to constructor if MP cost is desired
			numberofTargets=number;
			attacks=new BasicAttack[numberofTargets];
			type=3;
			int index=0;
			for(BattleObject target:targets){
				if(index>attacks.length-1) break;
				else {BasicAttack a=new BasicAttack(attacker,target);
					attacks[index]=a;
					index++;}
			}
		}
		void setTargets(ArrayList<BattleObject> party){
			int index=0;
			for(BattleObject target:party){
				if(index>attacks.length-1) break;
				else {BasicAttack a=new BasicAttack(doer,target);
					attacks[index]=a;
					index++;}
			}
		}
		public void execute(){
			for(BasicAttack a: attacks){
				a.execute();
				/*int mp=doer.getMP();
				mp=mp-cost;
				doer.setMP(mp);
				Uncomment if Multiattack has MP cost*/
			}
		}
	//Multi attack could have an MP cost, or could only be given to characters of a certain class/level/weapon, or both...
	
	}
}
