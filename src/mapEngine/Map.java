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
	private int xOffset = 0;
	private int yOffset = 0;
	
	private static final int TILESACROSS = 64;
	private static final int TILESDOWN = 32;
	
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
	public void setAbsoluteLocation(int x, int y){
		top = y/TILEHEIGHT-TILESDOWN/2;
		bottom = y/TILEHEIGHT+TILESDOWN/2;
		left = x/TILEWIDTH-TILESACROSS/2;
		right = x/TILEWIDTH+TILESACROSS/2;
		
		xOffset = x%TILEWIDTH;
		yOffset = y%TILEHEIGHT;
	}
	
	
	public void draw(Graphics g){
		setAbsoluteLocation(player.getX(), player.getY()); //Eventually: pixel by pixel
		
		drawField(g); //Bottom layer, walking plain
		drawAssets(g); //Buildings, signs, things that don't move
		drawCharacters(g); //Player an any other top layer people		
	}
	
	//Draws tiles
	public void drawField(Graphics g){
		
		
		int row = 0;
		for( int i = top-1; i < bottom+1; i++){
			int col = 0;
			for( int j = left-1; j < right+1; j++ ){
				g.drawImage(
						tileImages[field[i][j].getTileIndex()],
						col*TILEWIDTH + xOffset,
						row*TILEHEIGHT + yOffset,
						null);
				col++;
			}
			row++;
		}
	}
	
	public void drawAssets(Graphics g){
		
	}
	
	public void drawCharacters(Graphics g){
		g.drawImage(player.getImage(),
				convertAbsoluteToRelativeX(player.getXInTiles()),
				convertAbsoluteToRelativeY(player.getYInTiles()),
				null);
	}
	
	public int convertAbsoluteToRelativeX(int x){
		return (x-left-1)*TILEWIDTH;
	}
	public int convertAbsoluteToRelativeY(int y){
		return(y-top+1)*TILEHEIGHT;
	}
	
	
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

	private void upResponse(){ player.move(player.UP); }
	private void downResponse(){ player.move(player.DOWN); }
	private void leftResponse(){ player.move(player.LEFT); }
	private void rightResponse(){ player.move(player.RIGHT); }
	private void forwardResponse(){}
	private void backwardResponse(){}
}
