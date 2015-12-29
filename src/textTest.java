import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class textTest{

	public static void main(String[] args) {
		JFrame window = new JFrame("War of Equestria");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = new Dimension(1,1);
		window.setPreferredSize(
				new Dimension(
						(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()*2/3,
						(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()*2/3
						)
				);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); //Full dimensions	
		//window.setUndecorated(true);
		window.setContentPane(new textPanel());
		
		//Bind Keys
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