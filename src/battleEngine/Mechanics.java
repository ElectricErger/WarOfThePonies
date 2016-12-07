package battleEngine;

import java.awt.Font;
import java.util.Random;

import org.newdawn.slick.TrueTypeFont;

import characters.Character;

public class Mechanics {
	Character pc;
	Character[] enemies;
	Random r;

	private int attack1(Character enemy){
		int accuracy=r.nextInt(20);
		int dmg=0;
		if(enemy.getDefence()<accuracy){
			for(int i=0;i<=pc.getDamageDie(); i++){
				dmg+=r.nextInt(4)+1;
			}
		}
		return dmg;
	}
	private void heal2(int cost){
		pc.setMP(pc.getMP()-cost);
		int healed=r.nextInt(5)+5;
		pc.setHP(pc.getHP()+healed);
	}
	private void run3(){
		TrueTypeFont message=new TrueTypeFont(new Font("Arial",Font.PLAIN, 14),false);
		message.drawString(0, 0, "You Ran Away!");
	}
	
	private void updateHP(Character target,int damage){
		target.setHP(target.getHP()-damage);
	}
	public void attack(int selection, Character enemy){
		if(selection==0) {
			int dmg=attack1(enemy);
			updateHP(enemy,dmg);
		}
		else if (selection==1){
			heal2(10);
		}
		else if (selection==2){
			run3();
		}
	}
}
