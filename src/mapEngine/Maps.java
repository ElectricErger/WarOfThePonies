package mapEngine;

import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.*;

import character.Character;
import main.CameraController;

public class Maps extends BasicGameState {
	TiledMap current;
	character.Character pc;
	Animation spikeLeft;
	Animation spikeRight;
	character.Character[] npcs;
	public static final int id=1;
	CameraController camera;
	private boolean[][] blocked;
	private ArrayList<Rectangle> colliders=new ArrayList<Rectangle>();
	
	public Maps(String screen) throws SlickException {
		current=new TiledMap(screen);
		blocked=new boolean[current.getWidth()][current.getHeight()];
		int layer=0;
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
		camera=new CameraController(current,app);
		Image spike=new Image("/res/Spikesheet.png");
		pc=new Character("Spike", 20, 20, spike, 0);
		spikeLeft=new Animation(pc.getSprites(),0,0,2,0, true, 100, false);
		spikeRight=new Animation(pc.getSprites(),0,1,2,1, true, 100, false);
		
	}
	@Override
	public void render(GameContainer app, StateBasedGame game, Graphics g) throws SlickException {
		camera.drawMap();
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
		isColliding(app);
		camera.centerOn(pc.getCollider());

	}
	@Override
	public int getID() {
		return id;
	}
	void isOnScreen(GameContainer app){
		if(pc.getCollider().getCenterX()<=16)pc.setX(16f);
		if(pc.getCollider().getCenterX()>=current.getWidth()*32-16f)pc.setX(current.getWidth()*32-16f);
		if(pc.getCollider().getCenterY()<=16)pc.setY(16f);
		if(pc.getCollider().getCenterY()>=camera.getMapHeight()-16f)pc.setY(camera.getMapHeight()-16f);
	}
	void isColliding(GameContainer app){
		Rectangle test=pc.getCollider();
		for(Rectangle r:colliders){
			
			if(overlapRight(r,test))pc.setX(r.getCenterX()-32);
			
			else if(overlapLeft(r,test))pc.setX(r.getCenterX()+32);

			else if (overlapBottom(r,test))pc.setY(r.getCenterY()-32);

			else if(overlapTop(r,test))pc.setY(r.getCenterY()+32);
				
		}
		isOnScreen(app);
	}
	boolean overlapTop(Rectangle fixed, Rectangle moving){
		if(moving.intersects(fixed)&&fixed.getCenterY()-16<=moving.getCenterY()+16) return true;
		//if(moving.getY()+16>=fixed.getY()-16&&fixed.getY()-16>=moving.getY()-16) return true;
		else return false;
	}
	boolean overlapBottom(Rectangle fixed, Rectangle moving){
		if(moving.intersects(fixed)&&fixed.getCenterY()+16<=moving.getCenterY()-16)return true;
		//if(moving.getY()+16>=fixed.getY()+16&&fixed.getY()-16<=moving.getY()-16) return true;
		else return false;
	}
	boolean overlapLeft(Rectangle fixed, Rectangle moving){
		if(moving.intersects(fixed)&&fixed.getCenterX()+16<=moving.getCenterX()-16)return true;
		//if(moving.getX()+16>=fixed.getX()-16&&fixed.getX()-16>=moving.getX()-16) return true;
		else return false;
	}
	boolean overlapRight(Rectangle fixed, Rectangle moving){
		if(moving.intersects(fixed)&&fixed.getCenterX()-16<=moving.getCenterX()+16)return true;
		//if(moving.getX()+16>=fixed.getX()+16&&fixed.getX()+16>=moving.getX()-16) return true;
		else return false;
	}

	

}
