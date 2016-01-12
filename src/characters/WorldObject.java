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
	protected BufferedImage imgSheet; //Debating if it should be Image for scaling purposes
	protected Image currentImage;
	private boolean isWalking;

	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int STOP = 4;
	
	private static final int FRAME_WIDTH = 32;
	private static final int FRAME_HEIGHT = 32;
	
	protected static final int NUM_CYCLES = 4; //The number of rows of images
	protected static final int NUM_FRAMES = 4; //Number of sprite frames in a cycle
	protected static final int FRAME_SPEED = 4; //The number of game frames per sprite frame
	protected static final int FRAMES_PER_CYCLE = NUM_FRAMES * FRAME_SPEED; //Number of game frames in a cycle
	protected static final double PIXELS_PER_FRAME_Y= (double)Map.TILEHEIGHT/FRAMES_PER_CYCLE;  //Pixels moved up/down in a cycle
	protected static final double PIXELS_PER_FRAME_X = (double)Map.TILEWIDTH/FRAMES_PER_CYCLE; //Pixels moved left/right in a cycle
	
	protected int walkingDirection;
	private double offsetX;
	private double offsetY;
	
	public WorldObject(String imageLocation, Map m){
		try{
			Image img = ImageIO.read(getClass().getResourceAsStream(imageLocation));		
			scale(img);
			currentImage = imgSheet.getSubimage(0, 0, Map.TILEWIDTH, Map.TILEHEIGHT);
		}
		catch(Exception e){ e.printStackTrace(); }
		isWalking = false;
		area = m;
	}
	
	//Update locations - In tiles
	public void setX(int x){ this.x = x; }
	public void setY(int y){ this.y = y; }

	//Get location - In tiles
	public int getX(){ return x;}
	public int getY(){ return y;}
	
	//Get the fraction of a tile moved
	public double getOffsetX(){	return offsetX; }
	public double getOffsetY(){ return offsetY; }
	
	//Get image
	public Image getImage(){ return currentImage; }
	
	public int getDirection(){ return walkingDirection; }
	
	//Movement and animation
	public void move(int direction){
		if(!isWalking){
			isWalking = true;
			walkingDirection = direction;
			area.unoccupy(this);
			//Position is updated, so offset is negative
			switch(direction){
			case UP:
				y--;
				offsetY = -Map.TILEHEIGHT;
				break;
			case RIGHT:
				x++;
				offsetX = Map.TILEWIDTH;
				break;
			case DOWN:
				y++;
				offsetY = Map.TILEHEIGHT;
				break;
			case LEFT:
				x--;
				offsetX = -Map.TILEWIDTH;
				break;
			}

			animate(direction);
		}
	}
	private void animate(int direction){
		Thread t = new Thread(){
			public void run(){
				int walkingFrame = 0;
				for( int i = 0; i<FRAMES_PER_CYCLE; i++ ){ //16 frames in a whole cycle
					updateOffset(direction); //If I turn this off I can make it public for another method
					if(i % FRAME_SPEED == 0){
						currentImage = imgSheet.getSubimage(
								walkingFrame*Map.TILEWIDTH,
								direction*Map.TILEHEIGHT,
								Map.TILEWIDTH,
								Map.TILEHEIGHT);
						walkingFrame++;
					}
					try { Thread.sleep(Panel.FPmS); }
					catch (Exception e) { e.printStackTrace(); }
				}
				animationCleanup(direction);
			}
		};
		t.start();
	}
	private void updateOffset(int direction){
		switch(direction){
		case UP:
			offsetY += PIXELS_PER_FRAME_Y;
			break;
		case RIGHT:
			offsetX -= PIXELS_PER_FRAME_X;
			break;
		case DOWN:
			offsetY -= PIXELS_PER_FRAME_Y;
			break;
		case LEFT:
			offsetX += PIXELS_PER_FRAME_X;
			break;
		}
	}
	protected void animationCleanup(int direction){
		currentImage = imgSheet.getSubimage(
				0,
				direction*Map.TILEHEIGHT,
				Map.TILEWIDTH,
				Map.TILEHEIGHT);
		isWalking = false;
		offsetX = offsetY = 0;
		area.occupy(this);
	}
	
	public void draw(Graphics g){}
	
	private void scale(Image img){
		img = img.getScaledInstance(Map.TILEWIDTH*NUM_FRAMES, Map.TILEHEIGHT*NUM_CYCLES, 0);
		imgSheet = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		imgSheet.createGraphics().drawImage(img, 0, 0, null);
		imgSheet.createGraphics().dispose();
	}
}
