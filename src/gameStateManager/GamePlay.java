/*
 * This is the state manager for playing the game.
 */
package gameStateManager;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import textEngine.TextWindow;
import battleEngine.Battle;
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
	
	public GamePlay(GameStateManager g){
		
		super(g);
		
		//Make the variables
		world = new Map(this);
		player = new MainCharacter("/Characters/player.jpg", world);
		dialog = new TextWindow(this);
		menu = new Menu();
		npcs = new Characters(world);
		
		//Give the initialization variables to the linked classes
		g.setMainCharacter(player);
		world.setPlayer(player);
		world.setText(dialog);

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
			if(inConvo){ //Not sure how to deal with this
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

	
}
