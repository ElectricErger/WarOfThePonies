package characters;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import textEngine.TextWindow;
import main.Panel;
import mapEngine.Map;
	/**
	 * All types of objects that are visible in the world will use this class
	 * If you can see the thing in the world and interact with it, it's here.
	 * People, bosses, players, doors, signs, etc.
	 **/
public abstract class WorldObject {
	
	private Map area; 
	private int x;
	private int y;	
	private boolean isAnimated;
	private BufferedImage imgSheet; //Debating if it should be Image for scaling purposes
	private boolean isWalking;
	private Image currentImage;

	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	
	private static final int NUM_FRAMES = 4;
	private static final int FRAMES_PER_TILE_UP = NUM_FRAMES/Map.TILEHEIGHT;
	private static final int FRAMES_PER_TILE_RIGHT = NUM_FRAMES/Map.TILEWIDTH;
	private static final int FRAME_WIDTH = 32;
	private static final int FRAME_HEIGHT = 32;
	
	private int walkingFrame = 0;
	private int walkingDirection;
	
	public WorldObject(String imageLocation){
		try{
			imgSheet = ImageIO.read(getClass().getResourceAsStream(imageLocation));
			currentImage = imgSheet.getSubimage(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		}
		catch(Exception e){ e.printStackTrace(); }
	}
	
	//Update locations
	public void setX(int x){ this.x = x; }
	public void setY(int y){ this.y = y; }
		
	//Get location
	public int getX(){ return x; }
	public int getY(){ return y; }

	//Get image
	public Image getImage(){ return currentImage; }
	
	//Movement and animation
	public void move(int direction){
		if(!isWalking){
			isWalking = true;
			walkingDirection = direction;
			animate(direction);
			switch(direction){
			case UP:
				y--;
				break;
			case LEFT:
				x--;
				break;
			case DOWN:
				y++;
				break;
			case RIGHT:
				x++;
				break;
			}
		}
	}
	private void animate(int direction){
		Thread t = new Thread(){
			public void run(){
				for(walkingFrame = 0; walkingFrame<NUM_FRAMES; walkingFrame++){
					currentImage = imgSheet.getSubimage(
							walkingFrame*FRAME_WIDTH,
							direction*FRAME_HEIGHT,
							FRAME_WIDTH,
							FRAME_HEIGHT);
					try { Thread.sleep(Panel.FPmS*8); }
					catch (Exception e) { e.printStackTrace(); }
					//Update map find a way to update the map object for MainCharacter.
				}
				walkingFrame = 0;
				currentImage = imgSheet.getSubimage(
						0,
						direction*FRAME_HEIGHT,
						FRAME_WIDTH,
						FRAME_HEIGHT);
				isWalking = false;
			}
		};
		t.start();
	}

	public void draw(Graphics g){
		if(isWalking){//Since we already updated the variable, we need to offset?
			
			//Actually I'm not sure, let's build it and see what happens.
			
		}
		else{
			
		}
	}
}
