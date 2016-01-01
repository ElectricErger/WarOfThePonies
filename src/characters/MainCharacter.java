package characters;

import java.awt.Graphics;


public class MainCharacter extends BattleObject{
	
	public MainCharacter(String img) {
		super(img, new BattleClass());
	}

	
	public void draw(Graphics g){
		//X is local
		//Y is local
		//ABSTRACT CLASS
		g.drawImage(super.getImage(), super.getX(), super.getY(), null);
	}
}
