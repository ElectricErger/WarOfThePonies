package gameStateManager;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import textEngine.TextWindow;
import battleEngine.Battle;
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
	private Plot plot;
	
	public GamePlay(GameStateManager g){
		
		super(g);
		
		player = new MainCharacter("/CharacterPics/player.bmp");
		world = new Map(player);
		g.setMainCharacter(player);

		plot = new Plot();
		
		//Should I create a menu object here?
		dialog = new TextWindow();
		inMenu = inBattle = inConvo = false;
	}

	public void inBattle(){ inBattle = true; }
	public void inConvo(){ inConvo = true; }
	public void inMenu(){ inMenu = true; }
	
	public void endBattle(){ inBattle = false; }
	public void endConvo(){ inConvo = false; }
	public void leaveMenu(){ inMenu = false; }
	
	
	//Redirects to current state
	@Override 
	public void draw(Graphics g) {
		if(inBattle){
			battle.draw(g);
		}
		else{
			world.draw(g);
			if(inMenu){
				menu.draw(g);
			}
		}
		if(inConvo){
			dialog.draw(g);
		}
		
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

	//Don't need?
	private void upResponse(){}
	private void downResponse(){}
	private void leftResponse(){}
	private void rightResponse(){}
	private void forwardResponse(){}
	private void backwardResponse(){}
	
}
