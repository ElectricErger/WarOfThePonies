/*
 * Still to do:
 * Write what happens upon "ENTER"
 * Fix font so it works with screen size
 * Move general stuff to the template GameState
*/

package gameStateManager;

import java.awt.*;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.*;
import org.newdawn.slick.*;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import main.WoE;

public class StartScreen extends BasicGameState {
	private static final int id=0;
	Image background;
	
	private BufferedImage bg;
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
	
	
	
	public StartScreen(){
		super();
		selected = optionsSelector = loadSelector = 0;
		optionsSelected = loadSelected = false;
	}
	
	private void displayMenu(){
		int i = 0;
		for(String s : SELECT){
			float posWidth=WoE.woE.getScreenWidth()/2;
			float posHeight=WoE.woE.getScreenHeight()/2;
			if(i == selected){
				TrueTypeFont font=new TrueTypeFont(new Font("Arial", Font.BOLD, 40),true);
				font.drawString(posWidth-(font.getWidth(s)/2), posHeight+i*font.getLineHeight(), s, Color.black);
				//Font should really scale better
			}
			else{
				TrueTypeFont font=new TrueTypeFont(new Font("Arial", Font.PLAIN,40),true);
				font.drawString(posWidth-(font.getWidth(s)/2), posHeight+i*font.getLineHeight(), s, Color.white);
			}
			i++;
		}
	}
	
	private void displayOptions(){
		Graphics draw=new Graphics();
		draw.setColor(Color.blue);
		draw.fillRect(WoE.woE.getScreenWidth()/4, WoE.woE.getScreenHeight()/4,WoE.woE.getScreenWidth()/2,WoE.woE.getScreenWidth()/2);
		
		draw.setColor(Color.white);
		draw.drawRect(WoE.woE.getScreenWidth()/4, WoE.woE.getScreenHeight()/4,WoE.woE.getScreenWidth()/2,WoE.woE.getScreenWidth()/2);
		
		
		String prompt = "OPTIONS";
		TrueTypeFont font=new TrueTypeFont(new Font("Arial", Font.PLAIN, 40),true);
		font.drawString(getCenteredX(prompt, font), WoE.woE.getHeight()/3, prompt,Color.black);
		
		int i = 0;
		for(String s : OPTIONS){
			if(i == optionsSelector){
				font.drawString(getCenteredX(s,font), WoE.woE.getHeight()/2+i*font.getHeight(),s,Color.black);
			}
			else{
				font=new TrueTypeFont(new Font("Arial", Font.PLAIN, 40),true);
				font.drawString(getCenteredX(s,font), WoE.woE.getHeight()/2+i*font.getHeight(),s,Color.white);
			}
			i++;
		}
	}
	
	private void displayLoads(){
		Graphics draw=new Graphics();
		draw.setColor(Color.blue);
		draw.fillRect(
				WoE.woE.getWidth()/4,
				WoE.woE.getHeight()/4,
				WoE.woE.getWidth()/2,
				WoE.woE.getHeight()/2);
		draw.setColor(Color.white);
		draw.drawRect(
				WoE.woE.getWidth()/4,
				WoE.woE.getHeight()/4,
				WoE.woE.getWidth()/2,
				WoE.woE.getHeight()/2);
		
		String loadPrompt = "Do you want to load?";
		TrueTypeFont font=new TrueTypeFont(new Font("Arial", Font.PLAIN, 40),true);
		font.drawString(getCenteredX(loadPrompt, font), WoE.woE.getHeight()/3,loadPrompt);
		
		int i = 0;
		for(String s : LOADS){
			if(i == loadSelector){
				TrueTypeFont bold=new TrueTypeFont(new Font("Arial", Font.BOLD,40),true);
				bold.drawString(getCenteredX(s,bold), WoE.woE.getHeight()/2+i*font.getHeight(s), s,Color.black);
			}
			else{
				font.drawString(getCenteredX(s,font), WoE.woE.getHeight()/2+i*font.getHeight(s), s,Color.white);
			}
			i++;
		}
	}
	
	private int getCenteredX(String s, TrueTypeFont f){
		return (WoE.woE.getWidth()-f.getWidth(s))/2;
	}

	public void keyUp(int key){}
	//Depending on your context A does the following

	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		background=new Image("/res/bg.jpg");
		
	}
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, org.newdawn.slick.Graphics arg2) throws SlickException {
		background.draw(0, 0, WoE.woE.getScreenWidth(), WoE.woE.getScreenHeight());
		if(!(optionsSelected||loadSelected))displayMenu();
		else if(optionsSelected) displayOptions();
		else if(loadSelected) displayLoads();
	}
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		Input input=WoE.woE.getInput();
		if(input.isKeyDown(Input.KEY_DOWN)){
			downResponse();
		}
		else if(input.isKeyDown(Input.KEY_UP)){
			upResponse();
		}
		else if(input.isKeyDown(Input.KEY_ENTER)){
			forwardResponse();
		}
		else if(input.isKeyDown(Input.KEY_BACK)){
			backwardResponse();
		}
		
	}
	@Override
	public int getID() {
		return id;
	}
	private void forwardResponse(){
		if(!(loadSelected || optionsSelected)){
			switch(selected){
			case 0:
				WoE.game.enterState(1, new FadeOutTransition(),new HorizontalSplitTransition());
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
	
	private void forwardOption(){
		
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

}
