/*
 * How map works
 * You can travel anywhere within the overworld (an object that tells us what location is connected to what
 * You start out in a town, and if you leave the boarders of that town you go to the next area.
 * When you switch areas you update you and all other instances of Map to tell all of the people you changed. Also XY has to be updated
 * 	This is to avoid writing out the whole map and assets even though we aren't using it.
 * XY refers to tiles. The screen at any point in time will have 16 tiles across and 8 down
 * 	As the graphics engine gets better and we add in animations/smooth transitions we'll probably have to add room on that buffer
 * 
 * 
 * All WorldObjects are absolute to the XY coordinates of the map
 */


package mapEngine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import main.WoE;
import characters.*;

public class Map {
	
	private MainCharacter player;
	private OverworldParser world;
	private Tile[][] field;
	private Image[] tileImages;
	private int mapLocation;
	private String locationName;
	private int top, bottom, right, left; // Window bounds in terms of tiles
	private double xOffset = 0;
	private double yOffset = 0;
	
	private static final int TILESACROSS = 32;
	private static final int TILESDOWN = 16;
	
	public static final int TILEWIDTH = WoE.WIDTH/TILESACROSS+1;
	public static final int TILEHEIGHT = WoE.HEIGHT/TILESDOWN+1;
	
	public Map(MainCharacter c){
		player = c;
		mapLocation = 0; //FOR NOW, THE REAL THING WILL BE 0
		
		world = new OverworldParser();
		world.parse(mapLocation);
		locationName = world.getName(mapLocation);
		field = world.getTiles(mapLocation);
		tileImages = world.getTileImages(mapLocation);

		c.setX(field[0].length/2);
		c.setY(field.length/2);
	}

	//I Broke something
	public void centerAround(int x, int y){
		top = y-TILESDOWN/2;
		bottom = y+TILESDOWN/2;
		left = x-TILESACROSS/2;
		right = x+TILESACROSS/2;
	}
	public void centerAround(int x, int y, double offsetX, double offsetY){
		centerAround(x,y);
		xOffset = offsetX;
		yOffset = offsetY;
	}
	
	
	public void draw(Graphics g){
		centerAround(player.getX(), player.getY(), player.getOffsetX(), player.getOffsetY());
		
		drawField(g); //Bottom layer, walking plain
		drawAssets(g); //Buildings, signs, things that don't move
		drawCharacters(g); //Player an any other top layer people		
	}
	
	//I have 1 bad frame and the tiles above are missing
	public void drawField(Graphics g){
		int row = -1;
		for( int i = top-1; i < bottom+1; i++){
			int col = -1;
			for( int j = left-1; j < right+1; j++ ){
				g.drawImage(
						tileImages[field[i][j].getTileIndex()],
						(int)(col*TILEWIDTH+xOffset), //Left and right not working
						(int)(row*TILEHEIGHT+yOffset),
						null);
				col++;
			}
			row++;
		}	
	}
	public void drawAssets(Graphics g){}
	public void drawCharacters(Graphics g){ //Rebuild this
		g.drawImage(player.getImage(),
				convertAbsoluteToRelativeX(player.getX()),
				convertAbsoluteToRelativeY(player.getY()),
				null);
	}
	
	public int convertAbsoluteToRelativeX(int x){
		return (x-left-1)*TILEWIDTH;
	}
	public int convertAbsoluteToRelativeY(int y){
		return(y-top+1)*TILEHEIGHT;
	}
	

	//Responsiveness is slow. Consider making it a vector.
	public void keyDown(int key){
		switch(key){
		case KeyEvent.VK_UP:
			upResponse();
			break;
		case KeyEvent.VK_DOWN:
			downResponse();
			break;
		case KeyEvent.VK_LEFT:
			leftResponse();
			break;
		case KeyEvent.VK_RIGHT:
			rightResponse();
			break;
		case KeyEvent.VK_SPACE:
			forwardResponse();
			break;
		case KeyEvent.VK_BACK_SPACE:
			backwardResponse();
			break;
		}
	}

	private void upResponse(){ player.move(WorldObject.UP); }
	private void downResponse(){ player.move(WorldObject.DOWN); }
	private void leftResponse(){ player.move(WorldObject.LEFT); }
	private void rightResponse(){ player.move(WorldObject.RIGHT); }
	private void forwardResponse(){
		Tile nextTile = getAdjacentTile(player.getDirection());
		WorldObject person = nextTile.getObject();
		if(person != null){
			//Start up text or purchase
		}
		else{
			//there is nothing there
		}
	}
	private void backwardResponse(){}
	
	private Tile getAdjacentTile(int direction){
		int x = player.getX();
		int y = player.getY();
		Tile t = null;
		switch (player.getDirection()){
		case WorldObject.UP:
			t = field[x][y-1];
			break;
		case WorldObject.RIGHT:
			t = field[x+1][y];
			break;
		case WorldObject.DOWN:
			t = field[x][y+1];
			break;
		case WorldObject.LEFT:
			t = field[x-1][y];
			break;
		}
		return t;
	}
}
