package main;


import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.*;

import gameStateManager.GameStateManager;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panel extends StateBasedGame {
	
	public static final int FPmS = 1000/60;
	
	public Panel(){
		super("WoE");
	}	
		
	public void gameDisplay() throws SlickException{
		AppGameContainer WoE=new AppGameContainer(new Panel());
		WoE.setTitle("War for Equestria");
		WoE.setDisplayMode(int width, int height, true);
		//creates window and sets to fullscreen - put in the non-fullscreen basic dimensions you want
		//to tell it how big the picture is supposed to be(e.g. how many tiles should fit)
		WoE.start();
		//starts game, calls init, render, then update, render, update, render etc
	}
	
	@Override
	public void initStatesList(GameContainer WoE) throws SlickException {
		//addState(new Menu()); - to add states to your possible states for initialization
		/*Each state extends BasicGameState and has to override the methods init, update, and render 
		 * These contain the loading of static resources, the logic updates, and the command to render at set time interval
		 * Eclipse will write these for you if you ask it to :P
		 * Note that each state has a built in event listener for mouse/key/gamecontroller so you don't need to write/add one
		 */
	}

		
}
