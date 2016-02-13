package battleEngine;

import characters.BattleObject;

public abstract class Spells extends Action {
	private Spells(String name, int mp){
		this.name=name;
		magic=true;
		cost=mp;
	}
	private void updateMP(){
		int start=doer.getMP();
		doer.setMP(start-cost);
	}
	class Heal extends Spells{
		int health=0;
		int lower;
		int upper;
		//lower and upper bounds of amount spell can heal
		private Heal(String name, int mp, BattleObject caster, BattleObject target, int lower, int upper){
			super(name, mp);
			doer=caster;
			this.target=target;
			this.lower=lower;
			this.upper=upper;
			//type="healing";
		}

		private void amountHealed(){
			int amount=r.nextInt(upper-lower+2);
			amount=amount+lower;
			health=amount;
		}
		private void updateHP(){
			int hp=target.getHP();
			target.setHP(hp+health);
		}
		public void execute(){
			amountHealed();
			updateHP();
			updateMP();
		}
	}
}
