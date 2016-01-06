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
	private int img; ///The image associated with the tile
	
	public Tile(int tileImage){
		//inUseBy = object; //We will implement this later when the files is finished
		img = tileImage;
	}
	
	public WorldObject getObject(){ return inUseBy; }
	public int getTileIndex(){ return img; }
	
	public void setObject(WorldObject occupiedBy){ inUseBy = occupiedBy; }
	public WorldObject occupiedBy(){ return inUseBy; }
	public boolean occupied(){
		if(inUseBy == null ) return false;
		else return true;
	}
	public void unsetObject(){ inUseBy = null; }
}
