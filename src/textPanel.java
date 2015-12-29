import gameStateManager.GameStateManager;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import text_Engine.TextWindow;
import characters.PlayableCharacter;

@SuppressWarnings("serial")
public class textPanel extends JPanel implements KeyListener{
	PlayableCharacter mainC;
	TextWindow t;
	GameStateManager gsm;
	Thread main;
	
	private final int FPmS = 1000/60;
	
	
	public textPanel(){
		super();
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		requestFocusInWindow();

		gsm = new GameStateManager(this);
		
		//Create a thread that repaints every FPS
		main = new Thread(){
			private long startTime, stopTime, elapsed;
			public void run(){
				while(true){
					startTime = System.nanoTime();
					
					repaint();
					
					stopTime = System.nanoTime();
					elapsed = (stopTime - startTime)/1000000;
					elapsed = elapsed - FPmS;
					if(elapsed < 0 ) { elapsed = FPmS; }
					try{ sleep(elapsed); }
					catch(Exception e){ e.printStackTrace(); }
				}
			}
		};
		main.start();
		
		String[] s = {"Hello my name is Bob!", "What is your name?", "Bye :("};
		mainC = new PlayableCharacter(this, s);
		t = mainC.interact(); //Load up the interaction
		//Press space to call: t.displayNextLine();
	}
	
	@Override
	public void paintComponent(Graphics g){ //Called every time you repaint
		super.paintComponent(g);
		gsm.draw(g);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		gsm.keyDown(e.getKeyCode());
		
		/*
		 * if (e.getKeyCode() == KeyEvent.VK_SPACE){
			//t.displayNextLine();
		}
		 */
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	
}
