package battleEngine;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class BattleScreen extends BasicGameState {
	Image background;
	characters.Character[] party;
	characters.Character[] opponents;
	private static final int id=2;
	characters.Character current;
	
	public BattleScreen(Image a){
		background=a;
	}
	
	@Override
	public void init(GameContainer app, StateBasedGame game) throws SlickException {
		float x=50;
		float y=50;
		int index=0;
		for(characters.Character c:party){
			c.setX(x+10*index);
			c.setY(y+64*index);
		}
		index=0;
		x=app.getWidth();
		y=app.getHeight();
		for(characters.Character c:opponents){
			c.setX(x-10*index);
			c.setY(y-10*index);
		}
	}
	@Override
	public void render(GameContainer app, StateBasedGame game, Graphics g) throws SlickException {
		background.draw();
		for(characters.Character c:party){
			c.getAvatar().draw(c.getXPos(), c.getYPos());
		}
		for(characters.Character c:opponents){
			c.getAvatar().draw(c.getXPos(),c.getYPos());
		}
		
	}
	@Override
	public void update(GameContainer app, StateBasedGame game, int g) throws SlickException {
		if(current.getController()==1){
			
		}
		else if(current.getController()==0){
			
		}
	}
	@Override
	public int getID() {
		return id;
	}
	

}
