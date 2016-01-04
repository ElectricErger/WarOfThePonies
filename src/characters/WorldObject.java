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
	private int x; //Absolute...not tiles
	private int y;	
	private boolean isAnimated;
	private BufferedImage imgSheet; //Debating if it should be Image for scaling purposes
	private boolean isWalking;
	private Image currentImage;

	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	
	private static final int FRAME_WIDTH = 32;
	private static final int FRAME_HEIGHT = 32;
	
	private static final int NUM_FRAMES = 4; //Number of sprite frames in a cycle
	private static final int FRAME_SPEED = 8; //The number of game frames per sprite frame
	private static final int FRAMES_PER_CYCLE = NUM_FRAMES * FRAME_SPEED;
	private static final int PIXELS_PER_FRAME_Y= FRAMES_PER_CYCLE/Map.TILEHEIGHT;
	private static final int PIXELS_PER_FRAME_X = FRAMES_PER_CYCLE/Map.TILEWIDTH;
	
	private int walkingDirection;
	private int framesWalked = 0;
	
	public WorldObject(String imageLocation){
		try{
			imgSheet = ImageIO.read(getClass().getResourceAsStream(imageLocation));
			currentImage = imgSheet.getSubimage(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		}
		catch(Exception e){ e.printStackTrace(); }
	}
	
	//Update locations - In tiles
	public void setX(int x){ this.x = x*Map.TILEWIDTH; }
	public void setY(int y){ this.y = y*Map.TILEHEIGHT; }
		
	//Get location - in tiles
	public int getX(){ return x/Map.TILEWIDTH; }
	public int getY(){ return y/Map.TILEHEIGHT; }

	//Get image
	public Image getImage(){ return currentImage; }
	
	//Movement and animation
	public void move(int direction){
		if(!isWalking){
			isWalking = true;
			walkingDirection = direction;
			animate(direction);
		}
	}
	private void animate(int direction){
		Thread t = new Thread(){
			public void run(){
				for( int i = 0; i<FRAMES_PER_CYCLE; i++ ){
					if(framesWalked % FRAME_SPEED == 0){
						for(int walkingFrame = 0; walkingFrame<NUM_FRAMES; walkingFrame++){
						currentImage = imgSheet.getSubimage(
							walkingFrame*FRAME_WIDTH,
							direction*FRAME_HEIGHT,
							FRAME_WIDTH,
							FRAME_HEIGHT);
					}
					
					switch(direction){
					case UP:
						y -= PIXELS_PER_FRAME_Y;
						break;
					case RIGHT:
						x += PIXELS_PER_FRAME_X;
						break;
					case DOWN:
						y += PIXELS_PER_FRAME_Y;
						break;
					case LEFT:
						x -= PIXELS_PER_FRAME_X;
						break;
					}
					
					try { Thread.sleep(Panel.FPmS); }
					catch (Exception e) { e.printStackTrace(); }
				}
					//Update map find a way to update the map object for MainCharacter.
				}
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
			
		}
		else{
			
		}
	}
}
