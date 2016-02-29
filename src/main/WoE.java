package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import state.StartScreen;

public class WoE extends StateBasedGame{
	private static StateBasedGame game;
	private static AppGameContainer screen;
	private static int width = 800, height = 600;
	private static boolean fullScreen = false, vSynch = false;
	private static final String gameName = "War for Equestria";
	public static final int FPS = 60;
	
//Main method
	public static void main(String[] args){
		try{
			game=new WoE(); //The game
			screen=new AppGameContainer(game); //The window
			screen.setTitle(gameName);
			screen.setDisplayMode(width, height, fullScreen); //Default
			screen.setTargetFrameRate(FPS);
			screen.start(); //starts game
			//Init, render, update, render, update, etc
		}
		catch(SlickException e){ e.printStackTrace(); }
	}


//Container object for game
	private WoE(){
		super (gameName);
	}
	public static AppGameContainer getScreen(){ return screen; }
//Start thread
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartScreen(this));
		//MAIN GAME
	}
	
	//Get and set screen aspects
	public static int getWidth(){return width;}
	public static int getHeight(){return height;}
	public static boolean getFullScreen(){return fullScreen;}
	public static boolean getVSync(){return vSynch;}
	public static void setDimensions(int width, int height) throws SlickException{
		WoE.width = width; WoE.height = height;
		screen.setDisplayMode(width, height, fullScreen);
	}
	public static void setFullScreen(boolean fS) throws SlickException{
		WoE.fullScreen = fS;
		screen.setDisplayMode(width, height, fS);
	}
	public static void setVSync(boolean vS){
		WoE.vSynch = vS;
		screen.setVSync(vS);
	}

}