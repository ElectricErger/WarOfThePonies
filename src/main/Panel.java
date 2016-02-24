package main;


import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.*;

import gameStateManager.GamePlay;
import gameStateManager.StartScreen;


public class Panel extends StateBasedGame {
	
	public Panel(){
		super("WoE");
	}	


	@Override
	public void initStatesList(GameContainer WoE) throws SlickException {
		//addState(new Menu()); - to add states to your possible states for initialization
		/*Each state extends BasicGameState and has to override the methods init, update, and render 
		 * These contain the loading of static resources, the logic updates, and the command to render at set time interval
		 * Eclipse will write these for you if you ask it to :P
		 * Note that each state has a built in event listener for mouse/key/gamecontroller so you don't need to write/add one
		 */
		addState(new StartScreen());
		//addState(new GamePlay());
	}

		
}
