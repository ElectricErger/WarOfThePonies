package main;

import gameStateManager.GameStateManager;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panel extends JPanel implements KeyListener{
	GameStateManager gsm;
	Thread main;
	
	public static final int FPmS = 1000/60;
	
	
	public Panel(){
		super();
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		requestFocusInWindow();

		gsm = new GameStateManager();
		
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
	}
	
	@Override
	public void paintComponent(Graphics g){ //Called every time you repaint
		super.paintComponent(g);
		gsm.draw(g);
	}

	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) { gsm.keyDown(e.getKeyCode()); }
	public void keyReleased(KeyEvent e) { gsm.keyUp(e.getKeyCode()); }
	
	
}
