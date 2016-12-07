package main;

import java.io.File;
import java.io.FileNotFoundException;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import battleEngine.BattleScreen;
import graphics.mapEngine.Maps;
import states.StartScreen;

public class WarOfThePonies extends StateBasedGame {
	//Singleton
	static private WarOfThePonies _instance;
	public static WarOfThePonies getInstance(){
		if(_instance == null)
			_instance = new WarOfThePonies("War for Equestria");
		return _instance;
	}
	private WarOfThePonies(String title) { super(title); }
	
	private static AppGameContainer game;
	
	public static void main(String[] args) {
		try {
			game = new AppGameContainer(new WarOfThePonies("War for Equestria"));
			game.setFullscreen(true);
			game.start();
		}
		catch(SlickException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initStatesList(GameContainer game) throws SlickException {
		addState(new states.StartScreen());
		addState(new Maps("/res/map.tmx"));
		//addState(new BattleScreen(new Image("/res/SkyBG.png")));
	}

}
