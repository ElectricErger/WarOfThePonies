package textEngine;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import characters.WorldObject;

/**
 * When an text window is made, it's made for a specific person
 * If we have a conversation, we make a different text window and they trigger events
 * 
 *
 */
public class TextWindow {
	
	WorldObject object;
	JPanel context;
	TextBuffer buffer;
	boolean writtingInProgress;
	String currentString;
	boolean killThread;
	
	private final long TEXTSPEED = 1000/10; //in 1000ms we should have X text speed 
	
	public TextWindow(WorldObject thing, JPanel panel){
		object = thing;
		context = panel;
		buffer = new TextBuffer();
		writtingInProgress = false;
		killThread = false;
	}
	
	/**
	 * Receive a string to put in a buffer.
	 * @param String s - The string to write
	 */
	public void write(String s) {
		buffer.add(s);
	}
	
	/**
	 * The controller calls this when the user presses A
	 * and the world object is talking
	 * The 
	 */
	public void displayNextLine(){
		if(writtingInProgress) //Okay Mr. Speedy. Just write it out already
		{ 
			killThread = true;
			printText(currentString);
			writtingInProgress = false;
		}
		else
		{	
			writtingInProgress = true;
			currentString = buffer.getNext();
			
			//Writer loop
			
			////EXAMPLE USES BUFFERED IMAGE AS A TEMP
			Thread t = new Thread(){
				@Override
				public void run(){
					String writtenOut = "";
					long start, stop, elapsed;
					for(int i = 0; i<currentString.length();i++){	
						start = System.nanoTime();

						if(killThread){
							killThread = false;
							break;
						}
						
						//Which words to write
						char c = currentString.charAt(i);
						writtenOut += c;
						
						//Draw the box and words
						printText(writtenOut);
						
						//Play sound
						
						//Wait for delay
						stop = System.nanoTime();
						elapsed = (stop - start)/1000000; //In ms
						if(elapsed > TEXTSPEED){ elapsed = TEXTSPEED; }
						try { Thread.sleep(TEXTSPEED - elapsed); }
						catch (Exception e) { e.printStackTrace(); }
					}
					writtingInProgress = false;
				}
			};
			t.start();
		}
	}
	
	private void printText(String s){
		Graphics g = context.getGraphics();
		//g.fillRect(0,context.HEIGHT*2/3,context.WIDTH,context.HEIGHT/3);
		//g.drawString(s, 1, context.HEIGHT/3+1);
		
		g.setColor(Color.BLUE);
		g.fillRect(100,100,500,100);
		g.setColor(Color.WHITE);
		g.drawString(s, 100, 110);
	}
}

