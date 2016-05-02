package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.*;

public class Camera {
	TiledMap map;
	float mapTileWidth;
	float mapTileHeight;
	float mapWidth;
	float mapHeight;
	float tileWidth;
	float tileHeight;
	float cameraX;
	float cameraY;
	GameContainer app;
	
	public Camera(TiledMap map, GameContainer app){
		this.map=map;
		mapTileHeight=map.getHeight();
		mapTileWidth=map.getWidth();
		tileHeight=map.getTileHeight();
		tileWidth=map.getTileWidth();
		mapHeight=tileHeight*mapTileHeight;
		mapWidth=tileWidth*mapTileWidth;
		cameraX=0;
		cameraY=0;
		this.app=app;
	}
	
	public float getMapWidth(){return mapWidth;}
	public float getMapHeight(){return mapHeight;}
	
	
	public void centerOn(Shape collider){
		cameraX=collider.getCenterX()-app.getWidth()/2;
		cameraY=collider.getCenterY()-app.getHeight()/2;
		if (cameraX<0) cameraX=0;
		else if(cameraX+app.getWidth()/2>mapWidth)cameraX=mapWidth-app.getWidth()/2;
		if (cameraY<0) cameraY=0;
		else if(cameraY+app.getHeight()/2>mapHeight)cameraY=mapHeight-app.getHeight()/2;
	}
	public void draw(float offsetX,float offsetY){
		int tileOffsetX = (int) - (cameraX % tileWidth);
		int tileOffsetY = (int) - (cameraY% tileHeight);
		
		int tileIndexX=(int) (cameraX / tileWidth);
		int tileIndexY=(int) (cameraY / tileHeight);
		
		map.render((int)(offsetX+tileOffsetX), (int)(offsetY+tileOffsetY),
				tileIndexX,tileIndexY,
				(app.getWidth()-tileOffsetX)/32+1,
				(app.getHeight()-tileOffsetY)/32+1);
	}

}
