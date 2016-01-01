package characters;

import java.util.ArrayList;

public abstract class BattleObject extends WorldObject implements Comparable<BattleObject>{

	private String name;
	private BattleClass type;
	private int HP, MP; //Current values
	private int maxHP, maxMP;
	private int attack, defence;
	private int magicAttack, magicDefence;
	private int speed, accuracy, luck;
	private int level, exp, expToNextLevel;
	private ArrayList<Attack> moves;
	public int initiative=0;
	public boolean defeated=false;
	//Still needs weapon and armor
	
	public BattleObject(BattleClass c) {
		type = c;
		moves = new ArrayList<Attack>(); //Maybe the constructor will give it...
	}
	
	//Getters for when you enter a battle
	public int getHP(){ return HP; }
	public int getMP(){ return MP; }
	public int getMaxHP(){ return maxHP; }
	public int getMaxMP(){ return maxMP; }
	public int getAttack(){ return attack; }
	public int getDefence(){ return defence; }
	public int getmagicAttack(){ return magicAttack; }
	public int getmagicDefence(){ return magicDefence; }
	public int getSpeed(){ return speed; }
	public int getAccuracy(){ return accuracy; }
	public int getLuck(){ return luck; }
	public int getLevel(){ return level; }
	public int getExp(){ return exp; }
	public int getInit(){return initiative;}
	public int getExpToNextLevel(){ return expToNextLevel; }
	public BattleClass getType(){ return type; }
	public ArrayList<Attack> getAttacks(){ return moves; }
	public boolean getdefeated(){return defeated;}
	
	//During and after battles some things need to be updated
	public void setHP(int newHP){ HP = newHP; }
	public void setMP(int newMP){ MP = newMP; }
	public void setExp(int newExp){ exp = newExp; } //This is just the model, when you get EXP it should be the one calling these methods
	
	//When you level up
	public void setLevel(int newLevel){ level = newLevel; } //This isn't ++ because we can jump multiple levels I guess
	public void setExpToNextLevel(int newExp){ expToNextLevel = newExp; }
	public void setMaxHP(int newMaxHP){ maxHP = newMaxHP; }
	public void setMaxMP(int newMaxMP){ maxMP = newMaxMP; }
	public void setAttack(int newAttack){ attack = newAttack; }
	public void setDefence(int newDefence){ defence = newDefence; }
	public void setmagicAttack(int newMA){ magicAttack = newMA; }
	public void setmagicDefence(int newMD){ magicDefence = newMD; }
	public void setSpeed(int newSpeed){ speed = newSpeed; }
	public void setAccuracy(int newAccuracy){ accuracy = newAccuracy; }
	public void setLuck(int newLuck){ luck = newLuck; }
	public void addMove(Attack newMove){ moves.add(newMove); }
	public int compareTo(BattleObject compareto){
		int compareinit=((BattleObject)compareto).getInit();
		return compareinit-this.initiative;
	}
	//hopefully allows comparison/sorting by initiative
}
