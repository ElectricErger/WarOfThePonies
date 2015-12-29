package main;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class WoE{

	public final static int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public final static  int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	public static void main(String[] args) {
		JFrame window = new JFrame("War of Equestria");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window.setPreferredSize(new Dimension(WIDTH*2/3, HEIGHT*2/3));
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); //Full dimensions	
		window.setUndecorated(true);
		window.setContentPane(new Panel());
		
		//Bind Keys - Temporary
		window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "EXIT");
		window.getRootPane().getActionMap().put("EXIT", new AbstractAction(){ 
			        public void actionPerformed(ActionEvent e){
			            window.dispose();
			            System.exit(0);
			        }
			    }
		);
		
		window.pack();
		window.setVisible(true);
	}

}