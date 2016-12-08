package mapEngine;

import java.util.ArrayList;
import java.awt.font.*;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.*;

import DialogEngine.Display;
import character.Character;
import character.NPC;
import main.Camera;
//TODO: fix coordinates to be bottom of App screen etc
public class Overworld extends BasicGameState {
	Map current;
	character.Character pc;
	Animation[] sprite;
	public static final int id=1;
	Camera camera;
	boolean inDialog=false;
	NPC speaker=null;
	boolean toNext=false;
	GameContainer app;
	java.awt.Font a=new java.awt.Font("Arial", java.awt.Font.PLAIN, 10);
	
	public Overworld(Map m, GameContainer app) throws SlickException {
		current=m;
		this.app=app;
		
	}
	@Override
	public void init(GameContainer app, StateBasedGame game) throws SlickException {
		camera=new Camera(current.overworld,app);
		Image player=new Image("res/characterData/player.png");
		pc=new Character("player", 20, 20, player, 0);
		sprite=new Animation[]{
				new Animation(pc.getSprites(),0,0,4,0, true, 100, false),
				new Animation(pc.getSprites(),0,1,4,1, true, 100, false), 
				new Animation(pc.getSprites(),0,2,4,2,true,100,false), 
				new Animation(pc.getSprites(),0,3,4,3,true,100,false)};
		
	}
	@Override
	public void render(GameContainer app, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.black);
		camera.drawMap();
		g.translate(-camera.getCameraX(), -camera.getCameraY());
		pc.render();
		for(NPC n:current.npcs){
			n.getAvatar().draw(n.getX(), n.getY());
		}
		if(inDialog){
			g.translate(camera.getCameraX(), camera.getCameraY());
			g.fill(new Rectangle(0, app.getHeight()-128, 640, 128));
			TrueTypeFont write=new TrueTypeFont(a, false);
			Display speech=new Display(speaker,1);
			speech.getFirstLine();
			write.drawString(5, app.getHeight()-120, speech.getOutput());
			
		}
		if(toNext){
			g.translate(camera.getCameraX(), camera.getCameraY());
			g.fill(new Rectangle(app.getWidth()/2-100,app.getHeight()/2-100,200,200));
			TrueTypeFont write=new TrueTypeFont(a,false);
			write.drawString(app.getWidth()/2-write.getWidth("Press X to leave this area.")/2, app.getHeight()/2, "Press X to leave this area", Color.white);
		}
		
	}
	@Override
	public void update(GameContainer app, StateBasedGame game, int delta) throws SlickException {
		Input input=app.getInput();
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			app.exit();
		}
		if(inDialog){
			if(input.isKeyPressed(Input.KEY_UP)){
				inDialog=false;
			}
			if(input.isKeyPressed(Input.KEY_DOWN)){
				inDialog=false;
			}
			if(input.isKeyPressed(Input.KEY_LEFT)){
				inDialog=false;
			}
			if(input.isKeyPressed(Input.KEY_RIGHT)){
				inDialog=false;
			}
		}
		if(toNext){
			if(input.isKeyPressed(Input.KEY_UP)){
				toNext=false;
			}
			if(input.isKeyPressed(Input.KEY_DOWN)){
				toNext=false;
			}
			if(input.isKeyPressed(Input.KEY_LEFT)){
				toNext=false;
			}
			if(input.isKeyPressed(Input.KEY_RIGHT)){
				toNext=false;
			}
		}
		else{
			if(input.isKeyDown(Input.KEY_LEFT)){
				pc.setX((float)(pc.getXPos()-0.1*(delta)));
				sprite[3].update(delta);
				pc.setAvatar(sprite[3].getCurrentFrame());
				for(Rectangle collider:current.getBlocked()){
					if(collider.intersects(pc.getCollider())){
						pc.setX((float)(pc.getXPos()+0.1*(delta)));
					}
				}
				conversationCheckX(0.1f*delta);
				leave();
			}
			if(input.isKeyDown(Input.KEY_RIGHT)){
				pc.setX((float)(pc.getXPos()+0.1*(delta)));
				sprite[1].update(delta);
				pc.setAvatar(sprite[1].getCurrentFrame());
				for(Rectangle collider:current.getBlocked()){
					if(collider.intersects(pc.getCollider())){
						pc.setX((float)(pc.getXPos()-0.1*(delta)));
					}
				}
				conversationCheckX(-0.1f*delta);
				leave();
			}
			if(input.isKeyDown(Input.KEY_DOWN)){
				pc.setY((float)(pc.getYPos()+0.1*(delta)));
				sprite[2].update(delta);
				pc.setAvatar(sprite[2].getCurrentFrame());
				for(Rectangle collider:current.getBlocked()){
					if(collider.intersects(pc.getCollider())){
						pc.setY((float)(pc.getYPos()-0.1*(delta)));
					}
				}
				conversationCheckY(-0.1f*delta);
				leave();
			}
			if(input.isKeyDown(Input.KEY_UP)){
				pc.setY((float)(pc.getYPos()-0.1*(delta)));
				sprite[0].update(delta);
				pc.setAvatar(sprite[0].getCurrentFrame());
				for(Rectangle collider:current.getBlocked()){
					if(collider.intersects(pc.getCollider())){
						pc.setY((float)(pc.getYPos()+0.1*(delta)));
					}
				}
				conversationCheckY(0.1f*delta);
				leave();
			}
			onMap();
			camera.centerOn(pc.getCollider());
		}
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
	
	private void conversationCheckX(float n){
		for(NPC npc:current.npcs){
			if(npc.getCollider().intersects(pc.getCollider())){
				inDialog=true;
				speaker=npc;
				pc.setX(pc.getXPos()+n);
			}
		}
	}
	private void conversationCheckY(float n){
		for(NPC npc:current.npcs){
			if(npc.getCollider().intersects(pc.getCollider())){
				inDialog=true;
				speaker=npc;
				pc.setY(pc.getYPos()+n);
			}
		}
	}
	private void leave(){
		for(Rectangle r:current.leave){
			if(r.intersects(pc.getCollider()))toNext=true;
		}
	}
}
