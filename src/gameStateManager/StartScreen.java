/*
 * Still to do:
 * Write what happens upon "ENTER"
 * Fix font so it works with screen size
 * Move general stuff to the template GameState
*/

package gameStateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class StartScreen extends GameState {
	
	private BufferedImage bg;
	private GameStateManager manager;
	private final String[] SELECT = {
			"NEW GAME",
			"LOAD GAME",
			"OPTIONS"
	};
	private int selected;
	protected int screenWidth, screenHeight;
	
	
	
	
	
	public StartScreen(GameStateManager gsm){
		manager = gsm;
		try {
			bg = ImageIO.read(getClass().getResourceAsStream("/bg.jpg")); 
		}
		catch (Exception e) { e.printStackTrace(); }
		selected = 0;
	}
	
	/**
	 * Draw assets to screen 
	 */
	@Override
	public void draw(Graphics g){
		screenWidth = manager.getPanel().getWidth();
		screenHeight = manager.getPanel().getHeight();
		g.drawImage(bg, 0, 0, screenWidth, screenHeight, null);
		
		displayOptions(g);
		
		
		//In general this is where we draw all the things
		//If battle = true
			//
		//else
			//Get data from map object
		//If talk = true
			//
	}
	
	private void displayOptions(Graphics g){
		int i = 0;
		for(String s : SELECT){
			if(i == selected){
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.BOLD, 40)); //Font should really scale better
			}
			else{
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 40));
			}
			g.drawString(s, getCenteredX(s, g), screenHeight/2+i*g.getFontMetrics().getHeight());//Height is an issue with screen size
			i++;
		}
	}
	
	private int getCenteredX(String s, Graphics g){
		return (screenWidth-g.getFontMetrics().stringWidth(s))/2;
	}
	
	public void keyDown(int key){
		switch(key){
		case KeyEvent.VK_SPACE:
			
			break;
		case KeyEvent.VK_ENTER:
			if(selected == 0){ //We should transfer this to a method and keep it all in one place
				manager.nextState(manager.CHAPTER1, new Chapter1());
			}
			break;
		case KeyEvent.VK_UP:
			selected--;
			if(selected < 0){ selected = SELECT.length-1; }
			break;
		case KeyEvent.VK_DOWN:
			selected++;
			if(selected >= SELECT.length){ selected = 0; }
			break;
		}
	}
	
}
