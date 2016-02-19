package main;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class WoE{
	public static StateBasedGame game;
	public static AppGameContainer woE;

	public static void main(String[] args){
		try{
			game=new Panel();
			woE=new AppGameContainer(game);
			woE.setTitle("War for Equestria");
			woE.setDisplayMode(800, 600, true);
		//creates window and sets to fullscreen - put in the non-fullscreen basic dimensions you want
		//to tell it how big the picture is supposed to be(e.g. how many tiles should fit)
			woE.start();
		//starts game, calls init, render, then update, render, update, render etc
		}
		catch(SlickException e){
			e.printStackTrace();
		}
	}

}