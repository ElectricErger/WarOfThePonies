package battleEngine;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import mapEngine.Map;
import characters.BattleObject;

public class Battle extends BasicGameState{
	
	private boolean inBattle;
	private BattleObject[] initiativeOrder;
	private BattleObject[] party;
	private BattleObject[] enemies;
	private BattleObject current;
	private int currentIndex;
	private static final int id=4;
	private Map map;
	

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		startBattle(party, enemies, map);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, org.newdawn.slick.Graphics draw) throws SlickException {
		draw.setColor(Color.blue);
		draw.fillRect(0, 0, 50, 200);
		Font font=new Font("Arial", Font.PLAIN, 20);
		TrueTypeFont write=new TrueTypeFont(font,true);
		int i=0;
		for(BattleObject b: initiativeOrder){
			if(b==current){
				write.drawString(5, (write.getHeight(b.getName())+5)*i, b.getName(), Color.white);
			}
			else write.drawString(5, (write.getHeight(b.getName())+5)*i, b.getName(), Color.black);
			i++;		
		}
		drawParty(container);
		
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		check();
		
		
	}

	@Override
	public int getID() {
		return id;
	}
	public void drawParty(GameContainer container){
		int i=0;
		for(BattleObject b:party){
			Image sprite=b.getImage();
			sprite.draw(container.getWidth()-64-32*i,container.getHeight()-64-32*i);
			i++;
		}
	}
	public void startBattle(BattleObject[] party, BattleObject[] enemies, Map location){
		this.party=party;
		this.enemies=enemies;
		inBattle=true;
		initiativeOrder=new BattleObject[enemies.length+party.length-2];
		int i=0;
		for(BattleObject b:party){
			initiativeOrder[i]=b;
			i++;
		}
		for(BattleObject b:enemies){
			initiativeOrder[i]=b;
			i++;
		}
		Arrays.sort(initiativeOrder);
		currentIndex=-1;
	}
	public void endBattle(){
		int battleExp=0;
		for(BattleObject enemy: enemies){
			battleExp=battleExp+enemy.getExp();
		}
		battleExp=battleExp/party.length;
		for(BattleObject p:party){
			p.setExp(p.getExp()+battleExp);
			p.setExpToNextLevel(p.getExpToNextLevel()-battleExp);
		}
		inBattle=false;
	}
	private boolean isDefeated(BattleObject[] bo){
		for(BattleObject a:bo){
			boolean defeat=a.getdefeated();
			if (defeat=false) return false;
		}
		return true;
	}
	public void check(){
		if(isDefeated(enemies))endBattle();
		else{
			if(isDefeated(party)){
				//call game over
			}
			else nextAttacker();
		}
	}
	public void nextAttacker(){
		currentIndex++;
		current=initiativeOrder[currentIndex];
		round();
	}
	public void runAI(){
		AI enemy=new AI(current);
		Action attack=enemy.choose(current.getAttacks(), party);
		attack.execute();
		check();
		
	}
	public void runPlayer(){
		//UI
		attack.execute();
		check();
	}
	public void round(){
		for(BattleObject b:enemies){
			if(current==b){
				runAI();
				break;
			}
		}
		for(BattleObject b:party){
			if(current==b){
				runPlayer();
				break;
			}
		}
		nextAttacker();
	}
	public boolean inBattle(){ return inBattle; }

}
