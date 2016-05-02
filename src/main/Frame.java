package main;

import java.io.File;
import java.io.FileNotFoundException;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import mapEngine.Maps;
import battleEngine.BattleScreen;

public class Frame extends StateBasedGame {
	

	public Frame(String title) {
		super(title);
	}
	public static void main(String[] args) {
		try {
			AppGameContainer game=new AppGameContainer(new Frame("War for Equestria"));
			game.setFullscreen(true);
			game.start();
		}
		catch(SlickException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initStatesList(GameContainer game) throws SlickException {
		addState(new StartScreen());
		addState(new Maps("/res/map.tmx"));
		//addState(new BattleScreen(new Image("/res/SkyBG.png")));
	}

}
