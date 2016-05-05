package mapEngine;

import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.*;

import character.*;
import character.Character;
import main.Camera;

public class Maps extends BasicGameState {
	TiledMap current;
	character.Character pc;
	Animation spikeLeft;
	Animation spikeRight;
	character.NPC[] npcs;
	public static final int id=1;
	Camera camera;
	private boolean[][] blocked;
	private ArrayList<Rectangle> colliders=new ArrayList<Rectangle>();
	
	public Maps(String screen) throws SlickException {
		current=new TiledMap(screen);
		blocked=new boolean[current.getWidth()][current.getHeight()];
		int layer=1;
		for(int i=0; i<(current.getWidth());i++ ){
			for(int j=0;j<(current.getHeight());j++){
				int id=current.getTileId(i, j, layer);
				String result=current.getTileProperty(id, "Blocked", "false");
				boolean block;
				if(result.contains("true")){
					block=true;
					Rectangle x=new Rectangle((float)i*current.getTileWidth(),(float)j*current.getTileHeight(),
							current.getTileWidth(),current.getTileHeight());
					colliders.add(x);
				}
				else block=false;
				blocked[i][j]=block;
			}
		}
		
	}
	@Override
	public void init(GameContainer app, StateBasedGame game) throws SlickException {
		camera=new Camera(current,app);
		Image spike=new Image("/res/Spikesheet.png");
		pc=new Character("Spike", 20, 20, spike, 0);
		spikeLeft=new Animation(pc.getSprites(),0,0,2,0, true, 100, false);
		spikeRight=new Animation(pc.getSprites(),0,1,2,1, true, 100, false);
		
	}
	@Override
	public void render(GameContainer app, StateBasedGame game, Graphics g) throws SlickException {
		camera.draw(0, 0);
		Image avatar=pc.getAvatar();
		avatar.draw(pc.getXPos(), pc.getYPos());
	}
	@Override
	public void update(GameContainer app, StateBasedGame game, int delta) throws SlickException {
		Input input=app.getInput();
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			app.exit();
		}
		if(input.isKeyDown(Input.KEY_LEFT)){
			float x=pc.getXPos()-0.1f*delta;
			pc.setX(x);
			spikeLeft.update(delta);
			pc.setAvatar(spikeLeft.getCurrentFrame());
		}
		if(input.isKeyDown(Input.KEY_RIGHT)){
			float x=pc.getXPos()+(0.1f*delta);
			pc.setX(x);
			spikeRight.update(delta);
			pc.setAvatar(spikeRight.getCurrentFrame());
		}
		if(input.isKeyDown(Input.KEY_DOWN)){
			float y=pc.getYPos()+(0.1f*delta);
			pc.setY(y);
			spikeRight.update(delta);
			pc.setAvatar(spikeRight.getCurrentFrame());
		}
		if(input.isKeyDown(Input.KEY_UP)){
			float y=pc.getYPos()-(0.1f*delta);
			pc.setY(y);
			spikeLeft.update(delta);
			pc.setAvatar(spikeLeft.getCurrentFrame());
		}
		camera.centerOn(pc.getCollider());
		for(NPC x:npcs){
			if(npcCollision(x)){
				//trigger NPC action
			}
		}

	}
	@Override
	public int getID() {
		return id;
	}
	void isOnScreen(GameContainer app){
		if(pc.getCollider().getCenterX()<=16)pc.setX(16f);
		if(pc.getCollider().getCenterX()>=camera.getMapWidth()-16f)pc.setX(camera.getMapWidth()-16f);
		if(pc.getCollider().getCenterY()<=16)pc.setY(16f);
		if(pc.getCollider().getCenterY()>=camera.getMapHeight()-16f)pc.setY(camera.getMapHeight()-16f);
	}
	
	public boolean npcCollision(NPC x){
		if(pc.getCollider().intersects(x.getCollider())){ 
			x.setInCollision(true);
			return true;
		}
		else return false;
	}

	

}
