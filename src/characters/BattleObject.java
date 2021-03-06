package characters;

import java.util.ArrayList;

import mapEngine.Map;
import battleEngine.Action;
import battleObjects.Item;

public abstract class BattleObject extends WorldObject implements Comparable<BattleObject>{

	private String name;
	private BattleClass type;
	private int HP, MP; //Current values
	private int maxHP, maxMP;
	private int attack, defence;
	private int magicAttack, magicDefence;
	private int speed, accuracy, luck;
	private int level, exp, expToNextLevel;
	private ArrayList<Action> moves;
	private int initiative=0;
	private boolean defeated=false;
	private ArrayList<Item> inventory;
	//Still needs weapon and armor
	
	public BattleObject(String imgString, BattleClass c, Map m) {
		super(imgString, m);
		type = c;
		moves = new ArrayList<Action>(); //Maybe the constructor will give it...
	}
	
	//Getters for when you enter a battle
	public int getHP(){ return HP; }
	public int getMP(){ return MP; }
	public int getMaxHP(){ return maxHP; }
	public int getMaxMP(){ return maxMP; }
	public int getAttack(){ return attack; }
	public int getDefence(){ return defence; }
	public int getMagicAttack(){ return magicAttack; }
	public int getMagicDefence(){ return magicDefence; }
	public int getSpeed(){ return speed; }
	public int getAccuracy(){ return accuracy; }
	public int getLuck(){ return luck; }
	public int getLevel(){ return level; }
	public int getExp(){ return exp; }
	public int getInit(){return initiative;}
	public int getExpToNextLevel(){ return expToNextLevel; }
	public BattleClass getType(){ return type; }
	public ArrayList<Action> getAttacks(){ return moves; }
	//changed to Action array which encompasses attacks/spells/defence/and eventually item use;
	public boolean getDefeated(){return defeated;}
	public ArrayList<Item> getInventory(){return inventory;}
	
	//During and after battles some things need to be updated
	public void setInit(int init){initiative=init;}
	public void setHP(int newHP){ HP = newHP; }
	public void setMP(int newMP){ MP = newMP; }
	public void setDefeated(boolean newdefeated){defeated=newdefeated;}
	public void setExp(int newExp){ exp = newExp; } //This is just the model, when you get EXP it should be the one calling these methods
	
	//When you level up
	public void setLevel(int newLevel){ level = newLevel; } //This isn't ++ because we can jump multiple levels I guess
	public void setExpToNextLevel(int newExp){ expToNextLevel = newExp; }
	public void setMaxHP(int newMaxHP){ maxHP = newMaxHP; }
	public void setMaxMP(int newMaxMP){ maxMP = newMaxMP; }
	public void setAttack(int newAttack){ attack = newAttack; }
	public void setDefence(int newDefence){ defence = newDefence; }
	public void setMagicAttack(int newMA){ magicAttack = newMA; }
	public void setMagicDefence(int newMD){ magicDefence = newMD; }
	public void setSpeed(int newSpeed){ speed = newSpeed; }
	public void setAccuracy(int newAccuracy){ accuracy = newAccuracy; }
	public void setLuck(int newLuck){ luck = newLuck; }
	public void addMove(Action newMove){ moves.add(newMove); }
	@Override
	public int compareTo(BattleObject compareto){
		int compareInit=((BattleObject)compareto).getInit();
		return compareInit-this.initiative;
	}
	//hopefully allows comparison/sorting by initiative
}
