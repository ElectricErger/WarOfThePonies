/*
 * Rebuilding Tile to be the bulk of the map
 * It needs to reference the worldObject in it (blocking it and allowing for dialog)
 * The image will be an array outside of this.
 * 
 * BRANCH THIS SHIT
 */

package mapEngine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
//SCALE WHEN YOU INITIATE
public class Tile {
	private boolean solid;
	private Image img;
	
	
	public Tile(boolean solid, BufferedImage i){
		
		this.solid = solid;
		img = i;
		scale();
	}
	
	public boolean isSolid(){ return solid; }
	public Image tileImage(){ return img; }
	private void scale(){
		img = img.getScaledInstance(Map.TILEWIDTH, Map.TILEHEIGHT, Image.SCALE_DEFAULT);
	}
}
