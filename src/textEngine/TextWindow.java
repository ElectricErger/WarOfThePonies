/*
 * REMEMBER: THIS IS JUST AN OBJECT
 * It has a thread to develop the creep, nothing more
 * When someone wants to know what to display we give them the buffer
 * When someone wants to advance the text we hold that state.
 * 
 * 
 * This is a constant class
 * We display the character and the words
 * When it's done we can change the character
 */

package textEngine;

import gameStateManager.GamePlay;
import gameStateManager.GameState;
import gameStateManager.GameStateManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.WoE;
import characters.WorldObject;

public class TextWindow {
	
	private GamePlay game;	
	
	//What data we are working with
	private WorldObject character;
	private ArrayList<String> buffer;

	//Current state
	private String currentString, stringBuffer; // The string buffer is for creeping and what should be displayed
	private boolean hasContent, writing;
	
	private static final long TEXTSPEED = 1000/25; //in 1000ms we should have X text speed 
	
	
	
	
	public TextWindow(GamePlay g){
		buffer = new ArrayList<String>();
		hasContent = false;
		game = g;
		stringBuffer = "";
	}
	
	// Views
	public void draw(Graphics g){
		drawBox(g);
		drawName(g);
		writeText(g);
		playSound();
	}
	
	private void drawBox(Graphics g){
		g.setColor(Color.BLUE);
		g.fillRect(0, WoE.HEIGHT*3/4, WoE.WIDTH, WoE.HEIGHT/4);
		g.setColor(Color.WHITE);
		g.drawRect(0, WoE.HEIGHT*3/4, WoE.WIDTH, WoE.HEIGHT/4);
	}
	private void drawName(Graphics g){
		//g.drawString(character.getName());
	}
	private void writeText(Graphics g){
		g.drawString(stringBuffer, 20, WoE.HEIGHT*3/4+40);
	}
	private void playSound(){
		if(writing){
			//Play sound
			//This was put here because it should play as the words advance with the frames, not the buffer
		}
	}
	
	
	// Starts the convo
	public void loadWithCharacter(WorldObject character){
		//Look up the character in the translator
		buffer.add("HELLO");
		buffer.add("THIS IS A LOREM IPSUM TO TEST HOW TEXT WILL APPEAR. PLEASE DISREGARD ANY AND ALL DATA");
		buffer.add("I AM ACTUALLY YOUR BIRTH MOTHER. PLEASE LET ME OUT!");
		buffer.add("Remember, all screems for mercy are actually simulations, and should not be taken seriously.");
		hasContent = true;
		
		currentString = buffer.get(0);
	}

	//Direct control
	public void keyDown(int key){
		switch(key){
		case KeyEvent.VK_UP:
			upResponse();
			break;
		case KeyEvent.VK_DOWN:
			downResponse();
			break;
		case KeyEvent.VK_LEFT:
			leftResponse();
			break;
		case KeyEvent.VK_RIGHT:
			rightResponse();
			break;
		case KeyEvent.VK_SPACE:
			forwardResponse();
			break;
		case KeyEvent.VK_BACK_SPACE:
			backwardResponse();
			break;
		}
	}

	private void upResponse(){}
	private void downResponse(){}
	private void leftResponse(){}
	private void rightResponse(){}
	private void forwardResponse(){ advanceText(); }
	private void backwardResponse(){}


	//What happens when we press buttons
	synchronized private void advanceText(){
		if(writing){
			stringBuffer = currentString;
			writing = false;
			//Stops the creeping
		}
		else{
			if(buffer.size() == 0){
				hasContent = false; //Do I even need this now?
				cleanup();
				game.inConvo( false );
			}
			else{
				nextLine();
			}
		}
	}
	synchronized private void nextLine(){
		writing = true;
		currentString = buffer.get(0);
		buffer.remove(0); //This is kind of like a stack
		
		//Writer loop
		Thread t = new Thread(){
			@Override
			synchronized public void run(){
				stringBuffer = "";
				long start, stop, elapsed;
				for(int i = 0; i<currentString.length();i++){	
					
					start = System.nanoTime();

					if(!writing){ break; }
					
					//Update buffer
					char c = currentString.charAt(i);
					stringBuffer += c;
					
					//Wait for delay
					stop = System.nanoTime();
					elapsed = (stop - start)/1000000; //In ms
					if(elapsed > TEXTSPEED){ elapsed = TEXTSPEED; }
					try { Thread.sleep(TEXTSPEED - elapsed); }
					catch (Exception e) { e.printStackTrace(); }
				}
				writing = false;
			}
		};
		t.start();
	}
	private void cleanup(){
		currentString = stringBuffer = "";
		writing = hasContent = false;
		
	}
	
	//Getters and setters
	public boolean hasContent(){ return hasContent; } //Do I need this?

	public void keyUp(int key) {
		// TODO Auto-generated method stub
		
	}
}

