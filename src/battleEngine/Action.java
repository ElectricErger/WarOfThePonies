package battleEngine;

import java.util.Random;

import characters.BattleObject;

public abstract class Action {
	BattleObject doer;
	BattleObject target;
	//who does the attack target - may be self, fellow party members, or opponent of choice
	boolean magic=false;
	//default is false, true if move is magical
	int cost=0;
	//magic effects will have an MP cost
	Random r;
	//element of randomness associated with damage, accuracy, crits, healing etc
	class BasicAttack extends Action {
		int damage=0;
		boolean hit=false;
		boolean crit=false;
		private BasicAttack(BattleObject attacker, BattleObject defender){
			doer=attacker;
			target=defender;
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
				int dmg=r.nextInt(w.damagedie)+1;
				damage=damage+dmg;
			}
			if(crit){
				int multiplier=2+(doer.getLevel()/4);
				damage=damage*multiplier;
			}
			
		}
		private void update(){
			accuracy();
			if(hit){
				critical();
				damage(w);
				int start=target.getHP();
				start=start-damage;
				target.setHP(start);
				if(target.getHP()<=0) target.defeated=true;
				
			}
			
		}
	}
}
