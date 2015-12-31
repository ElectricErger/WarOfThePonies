package characters;

import java.awt.image.BufferedImage;
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
	private BufferedImage img;	
	
	//Update locations
	public void setX(int x){ this.x = x; }
	public void setY(int y){ this.y = y; }
	
	//Get location
	public int getX(){ return x; }
	public int getY(){ return y; }
}
