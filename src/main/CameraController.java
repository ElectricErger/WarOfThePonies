package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class CameraController {
	protected TiledMap map;
	protected int mapTileWidth=40;
	protected int mapTileHeight=40;
	protected int mapWidth;
	protected int mapHeight;
	protected int tileWidth=32;
	protected int tileHeight=32;
	GameContainer app;
	protected float cameraX;
	protected float cameraY;
	
	public CameraController(TiledMap map,GameContainer app){
		this.map=map;
		this.app=app;
		mapWidth=mapTileWidth*tileWidth;
		mapHeight=mapTileHeight*tileHeight;
	}
	
	public float getMapHeight(){return mapHeight;}
	public float getMapWidth(){return mapWidth;}
	public float getX(){return cameraX;}
	public float getY(){return cameraY;}
	public TiledMap getMap(){return map;}
	
	public void centerOn(float x,float y){
		cameraX=x-app.getWidth()/2;
		cameraY=y-app.getHeight()/2;
		if(cameraX < 0) cameraX = 0;
		else if(cameraX + app.getWidth()/2 > mapWidth) cameraX = mapWidth - app.getWidth();
		
	    //if the camera is at the top or bottom edge lock it to prevent a black bar
	    if(cameraY < 0) cameraY = 0;
	    else if(cameraY + app.getHeight()/2 > mapHeight) cameraY = mapHeight - app.getHeight();
	}
	public void centerOn(float x, float y, float height, float width) {
		this.centerOn(x + width / 2, y + height / 2);
	   }
		   /**
		    * "locks the camera on the center of the given Shape. The camera tries to keep the location in it's center.
		    * @param shape the Shape which should be centered on the screen
		    */
	public void centerOn(Shape shape) {
		this.centerOn(shape.getX(), shape.getY(), shape.getHeight(), shape.getWidth());
		}
		   
		   /**
		    * draws the part of the map which is currently focused by the camera on the screen
		    */
	
}
