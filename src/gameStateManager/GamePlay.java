/*
 * This is the state manager for playing the game.
 */
package gameStateManager;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import textEngine.TextWindow;
import battleEngine.Battle;
import characters.BattleObject;
import characters.Characters;
import characters.MainCharacter;
import mapEngine.Map;
import menuEngine.Menu;

public class GamePlay extends GameState{
	
	private MainCharacter player;
	private boolean inMenu, inBattle, inConvo;
	private Menu menu;
	private Battle battle;
	private TextWindow dialog;
	private Map world;
	private Characters npcs;
	private int plot;
	private BattleObject[] party = new BattleObject[6];
	
	public GamePlay(GameStateManager g){
		
		super(g);
		
		//Make the variables
		world = new Map(this);
		dialog = new TextWindow(this);
		menu = new Menu(this);
		//Pieces to go on the board
		player = new MainCharacter("/characterData/player.png", world);
		npcs = new Characters(world);
		
		//Give the initialization variables to the linked classes
		world.setPlayer(player);
		world.setNPCs(npcs);
		world.setText(dialog);
		party[0] = player;

		plot = 0;
		inMenu = inBattle = inConvo = false;
	}
	
	public void advancePlot(){
		plot++;
		//Do things
	}
	public int plot(){ return plot; }

	public void inBattle(boolean b){ inBattle = b; }
	public void inConvo(boolean b){ inConvo = b; }
	public void inMenu(boolean b){ inMenu = b; }
	
	//Redirects to current state
	@Override 
	public void draw(Graphics g) {
		if(inBattle){ battle.draw(g); }
		else{
			world.draw(g);
			if(inMenu){ menu.draw(g); }
		}
		if(inConvo){ dialog.draw(g); }
	}
	public void keyDown(int key) {
		if(inBattle){
			battle.keyDown(key);
			inBattle = battle.inBattle();
		}
		else{
			if(inConvo){ //Not sure how to deal with this - May display, but wont advance
				dialog.keyDown(key);
				inConvo = dialog.hasContent();
			}
			else{
				if(inMenu){
					menu.keyDown(key);
				}
				else{
					world.keyDown(key);
				}
			}
		}
	}
	public void keyUp(int key) {
		if(inBattle){
			battle.keyUp(key);
			inBattle = battle.inBattle();
		}
		else{
			if(inConvo){ //Not sure how to deal with this
				dialog.keyUp(key);
				inConvo = dialog.hasContent();
			}
			else{
				if(inMenu){
					menu.keyUp(key);
				}
				else{
					world.keyUp(key);
				}
			}
		}
	}

	public void startBattle(BattleObject[] enemies){
		battle.startBattle(party, enemies, world);
	}
}
