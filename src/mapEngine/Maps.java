package mapEngine;

import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.*;

import character.Character;
import main.Camera;

public class Maps extends BasicGameState {
	TiledMap current;
	character.Character pc;
	Animation[] sprite;
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
		sprite=new Animation[]{new Animation(pc.getSprites(),0,0,2,0, true, 100, false),new Animation(pc.getSprites(),0,1,2,1, true, 100, false)};
		
	}
	@Override
	public void render(GameContainer app, StateBasedGame game, Graphics g) throws SlickException {
		camera.drawMap();
		g.translate(-camera.getCameraX(), -camera.getCameraY());
		pc.render();
	}
	@Override
	public void update(GameContainer app, StateBasedGame game, int delta) throws SlickException {
		Input input=app.getInput();
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			app.exit();
		}
		if(input.isKeyDown(Input.KEY_LEFT)){
			pc.setX((float)(pc.getXPos()-0.1*(delta)));
			sprite[0].update(delta);
			pc.setAvatar(sprite[0].getCurrentFrame());
			for(Rectangle collider:colliders){
				if(collider.intersects(pc.getCollider())){
					pc.setX((float)(pc.getXPos()+0.1*(delta)));
				}
			}
		}
		if(input.isKeyDown(Input.KEY_RIGHT)){
			pc.setX((float)(pc.getXPos()+0.1*(delta)));
			sprite[1].update(delta);
			pc.setAvatar(sprite[1].getCurrentFrame());
			for(Rectangle collider:colliders){
				if(collider.intersects(pc.getCollider())){
					pc.setX((float)(pc.getXPos()-0.1*(delta)));
				}
			}
		}
		if(input.isKeyDown(Input.KEY_DOWN)){
			pc.setY((float)(pc.getYPos()+0.1*(delta)));
			for(Rectangle collider:colliders){
				if(collider.intersects(pc.getCollider())){
					pc.setY((float)(pc.getYPos()-0.1*(delta)));
				}
			}
		}
		if(input.isKeyDown(Input.KEY_UP)){
			pc.setY((float)(pc.getYPos()-0.1*(delta)));
			for(Rectangle collider:colliders){
				if(collider.intersects(pc.getCollider())){
					pc.setY((float)(pc.getYPos()+0.1*(delta)));
				}
			}
		}
		onMap();
		camera.centerOn(pc.getCollider());
	}
	@Override
	public int getID() {
		return id;
	}

	public void onMap(){
		if(pc.getXPos()>camera.getMapWidth())pc.setX(camera.getMapWidth()-32f);
		if(pc.getXPos()<0)pc.setX(16f);
		if(pc.getYPos()>camera.getMapHeight())pc.setY(camera.getMapHeight()-32f);
		if(pc.getYPos()<0)pc.setY(16f);
	}

}
