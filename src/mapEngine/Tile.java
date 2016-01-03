/*
 * Rebuilding Tile to be the bulk of the map
 * It needs to reference the worldObject in it (blocking it and allowing for dialog)
 * The image will be an array outside of this.
 * 
 * BRANCH THIS SHIT
 */

package mapEngine;

import characters.WorldObject;
//SCALE WHEN YOU INITIATE
public class Tile {
	private WorldObject inUseBy;
	private int img;
	
	public Tile(WorldObject object, int tileImage){
		inUseBy = object;
		img = tileImage;
	}
	
	public WorldObject getObject(){ return inUseBy; }
	public int tileImage(){ return img; }
}
