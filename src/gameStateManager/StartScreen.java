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
import javax.swing.JPanel;

import main.WoE;

public class StartScreen extends GameState {
	
	private BufferedImage bg;
	private GameStateManager manager;
	private JPanel panel;
	private final String[] SELECT = {
			"NEW GAME",
			"LOAD GAME",
			"OPTIONS",
			"EXIT GAME"
	};
	private final String[] OPTIONS = {
			"RESOLUTION",
			"KEY BINDINGS",
			"BACK"
	};
	private final String[] LOADS = { //Maybe we'll load some statistics like game time and map location
			"YES",
			"NO"
	};
	private int selected, optionsSelector, loadSelector;
	private boolean optionsSelected, loadSelected;
	
	
	
	public StartScreen(GameStateManager gsm, JPanel p){
		panel = p;
		manager = gsm;
		try {
			bg = ImageIO.read(getClass().getResourceAsStream("/bg.jpg")); 
		}
		catch (Exception e) { e.printStackTrace(); }
		selected = optionsSelector = loadSelector = 0;
		optionsSelected = loadSelected = false;
	}
	
	/**
	 * Draw assets to screen 
	 */
	@Override
	public void draw(Graphics g){
		g.drawImage(bg, 0, 0, WoE.WIDTH, WoE.HEIGHT, null);
		displayMenu(g);
		if(optionsSelected){
			displayOptions(g);
		}
		else{
			if(loadSelected){
				displayLoads(g);
			}
		}
		
	}
	
	//View
	
	private void displayMenu(Graphics g){
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
			g.drawString(s, getCenteredX(s, g), WoE.HEIGHT/2+i*g.getFontMetrics().getHeight());//Height is an issue with screen size
			i++;
		}
	}
	
	private void displayOptions(Graphics g){
		g.setColor(Color.BLUE);
		g.fillRect(
				WoE.WIDTH/4,
				WoE.HEIGHT/4,
				WoE.WIDTH/2,
				WoE.HEIGHT/2);
		g.setColor(Color.WHITE);
		g.drawRect(
				WoE.WIDTH/4,
				WoE.HEIGHT/4,
				WoE.WIDTH/2,
				WoE.HEIGHT/2);
		
		String prompt = "OPTIONS";
		g.drawString(prompt, getCenteredX(prompt, g), WoE.HEIGHT/3);
		
		int i = 0;
		for(String s : OPTIONS){
			if(i == optionsSelector){
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.BOLD, 40)); //Font should really scale better
			}
			else{
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 40));
			}
			g.drawString(s, getCenteredX(s, g), WoE.HEIGHT/2+i*g.getFontMetrics().getHeight());
			i++;
		}
	}
	
	private void displayLoads(Graphics g){
		g.setColor(Color.BLUE);
		g.fillRect(
				WoE.WIDTH/4,
				WoE.HEIGHT/4,
				WoE.WIDTH/2,
				WoE.HEIGHT/2);
		g.setColor(Color.WHITE);
		g.drawRect(
				WoE.WIDTH/4,
				WoE.HEIGHT/4,
				WoE.WIDTH/2,
				WoE.HEIGHT/2);
		
		String loadPrompt = "Do you want to load?";
		g.drawString(loadPrompt, getCenteredX(loadPrompt, g), WoE.HEIGHT/3);
		
		int i = 0;
		for(String s : LOADS){
			if(i == loadSelector){
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.BOLD, 40)); //Font should really scale better
			}
			else{
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 40));
			}
			g.drawString(s, getCenteredX(s, g), WoE.HEIGHT/2+i*g.getFontMetrics().getHeight());//Height is an issue with screen size
			i++;
		}
	}
	
	private int getCenteredX(String s, Graphics g){
		return (WoE.WIDTH-g.getFontMetrics().stringWidth(s))/2;
	}
	
	//Controller
	
	public void keyDown(int key){
		switch(key){
		case KeyEvent.VK_ENTER:
			forwardResponse();
			break;
		case KeyEvent.VK_UP:
			upResponse();
			break;
		case KeyEvent.VK_DOWN:
			downResponse();
			break;
		case KeyEvent.VK_BACK_SPACE:
			backwardResponse();
			break;
		}
	}

	private void forwardResponse(){
		if(!(loadSelected || optionsSelected)){
			switch(selected){
			case 0:
				manager.nextState(GameStateManager.CHAPTER1, new Chapter1());
				break;
			case 1:
				loadSelected = true;
				break;
			case 2:
				optionsSelected = true;
				break;
			case 3:
				System.exit(0);
				break;
			}
		}
		else{
			if(optionsSelected){
				forwardOption();
			}
			else{
				forwardLoad();
			}
		}
	}
	
	private void backwardResponse(){
		if(optionsSelected){
			optionsSelected = false;
		}
		if(loadSelected){
			loadSelected = false;
		}
	}
	
	private void upResponse(){
		if(!(optionsSelected || loadSelected)){
			selected--;
			if(selected < 0){ selected = SELECT.length-1; }
		}
		else{
			if(optionsSelected){
				optionsSelector--;
				if(optionsSelector < 0){ optionsSelector = OPTIONS.length-1; }
			}
			else{
				loadSelector--;
				if(loadSelector < 0){ loadSelector = LOADS.length-1; }
			}
		}
	}
	
	private void downResponse(){
		if(!(optionsSelected || loadSelected)){
			selected++;
			if(selected >= SELECT.length){ selected = 0; }
		}
		else{
			if(optionsSelected){
				optionsSelector++;
				if(optionsSelector >= OPTIONS.length){ optionsSelector = 0; }
			}
			else{
				loadSelector++;
				if(loadSelector >= LOADS.length){ loadSelector = 0; }
			}
		}
	}

	//Depending on your context A does the following
	private void forwardOption(){
		switch(optionsSelector){
		case 0:
			break;
		case 1:
			
			break;
		case 2:
			optionsSelected = false;
			break;
		}
	}
	
	private void forwardLoad(){
		switch(loadSelector){
		case 0:
			//BEGIN LOAD
			break;
		case 1:
			loadSelected = false;
			break;
		}
	}
}
