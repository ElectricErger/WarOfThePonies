package main;

import java.io.File;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import mapEngine.Map;
import mapEngine.Overworld;
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
		addState(new Overworld(new Map(new File("/res/map.tmx"), new File("res/maps/world1.txt")), game));
		//addState(new BattleScreen(new Image("/res/SkyBG.png")));
	}

}
