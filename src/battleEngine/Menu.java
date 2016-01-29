package battleEngine;

import java.util.ArrayList;

import battleObjects.Item;
import battleObjects.Weapon;
import characters.BattleObject;

public class Menu {
	ArrayList<Action> normal;
	//non-magic moves
	ArrayList<Action> magic;
	//magic moves
	ArrayList<Item> items;
	//usable items
	ArrayList<Item> change;
	//to equip stuff
	
	public Menu(BattleObject character){
		ArrayList<Action> attacks=character.getAttacks();
		for(Action a:attacks){
			if(a.type==1)magic.add(a);
			if(a.type==2)normal.add(a);
			if(a.type==3)normal.add(a);
			if(a.type==4)magic.add(a);
			if(a.type==5)magic.add(a);
		}
		ArrayList<Item> inv=character.getInventory();
		for(Item i:inv){
			if(i.getType()==1)items.add(i);
			if(i.getType()==2)change.add(i);
		}
	}

}
