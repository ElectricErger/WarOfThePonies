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

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.WoE;
import characters.WorldObject;

/**
 * When an text window is made, it's made for a specific person
 * If we have a conversation, we make a different text window and they trigger events
 * 
 *
 */
public class TextWindow {
	
	private boolean hasContent; //When there is content we can 
	
	private boolean writing;
	ArrayList<String> buffer;
	String currentString, stringBuffer; // The string buffer is for creeping and what should be displayed
	WorldObject character, nextCharacter;
	
	
	private final long TEXTSPEED = 1000/10; //in 1000ms we should have X text speed 
	
	public TextWindow(){
		buffer = new ArrayList<String>();
		hasContent = false;
	}
	
	// Views
	public void draw(Graphics g){
		g.setColor(Color.BLUE);
		g.fillRect(0, WoE.HEIGHT*3/4, WoE.WIDTH, WoE.HEIGHT/4);
		g.setColor(Color.WHITE);
		g.drawRect(0, WoE.HEIGHT*3/4, WoE.WIDTH, WoE.HEIGHT/4);
		
		//DRAW NAME
		
		g.drawString(currentString, 0, WoE.HEIGHT*3/4+40);
		if(writing){
			//Play sound
			//This was put here because it should play as the words advance with the frames, not the buffer
		}
	}
	
	
	// Object controllers
	/**
	 * Content the character has to say and who should talk next at the end
	 * @param String s - The string to write
	 */
	public void setContent(String[] dialog, WorldObject next) {
		nextCharacter = next;
		for(String s : dialog){
			buffer.add(s);
		}
		hasContent = true;
	}

	/**
	 * Tells you if someone is talking
	 * @return
	 */
	public boolean hasContent(){
		return hasContent;
	}

	/**
	 * Call this method to advance the text
	 */
	synchronized public void advanceText(){
		if(writing){
			stringBuffer = currentString;
			writing = false; //Should writing be used for the loop?
			//Design nextLine to break if !writing
		}
		else{
			if(buffer.size() == 0){
				if(nextCharacter != null){					
					nextCharacter.advanceDialog(this); //This tells the character to give the buffer an update
					nextLine(); //Now we have more dialog
				}
				else{
					hasContent = false;					
				}
			}
			else{
				nextLine();
			}
			nextLine();
		}
	}
	
	/**
	 * The controller calls this when the user presses A
	 * and the world object is talking
	 * The 
	 */
	synchronized public void nextLine(){
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
}

