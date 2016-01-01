package characters;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import textEngine.TextWindow;
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
	private Image img;	
	//Animation
	
	public WorldObject(String imageLocation){
		try{ img = ImageIO.read(getClass().getResourceAsStream(imageLocation)); }
		catch(Exception e){ e.printStackTrace(); }
	}
	
	//Update locations
	public void setX(int x){ this.x = x; }
	public void setY(int y){ this.y = y; }
		
	//Get location
	public int getX(){ return x; }
	public int getY(){ return y; }

	//Get image
	public Image getImage(){ return img; }
}
