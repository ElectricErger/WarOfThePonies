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
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import main.WoE;
import characters.*;

public class Map {
	
	private MainCharacter player;
	private OverworldParser world;
	private int[][] field;
	private Tile[] tiles;
	private int mapLocation;
	private String locationName;
	private int top, bottom, right, left; // Window bounds in terms of tiles
	
	private static final int TILESACROSS = 16;
	private static final int TILESDOWN = 8;
	
	public static final int TILEWIDTH = WoE.WIDTH/TILESACROSS;
	public static final int TILEHEIGHT = WoE.HEIGHT/TILESDOWN;
	
	public Map(MainCharacter c){
		player = c;
		mapLocation = 0; //FOR NOW, THE REAL THING WILL BE 0
		
		world = new OverworldParser();
		world.parse(mapLocation);
		locationName = world.getName(mapLocation);
		field = world.getTileMap(mapLocation);
		tiles = world.getTileSet(mapLocation);
		setAbsoluteLocation(field[0].length/2, field.length/2);
	}

	public void setAbsoluteLocation(int x, int y){ //This may cause an off by 1 error
		top = y-TILESDOWN/2;
		bottom = y+TILESDOWN/2;
		left = x-TILESACROSS/2;
		right = x+TILESACROSS/2;
	}
	
	
	public void draw(Graphics g){
		//setAbsoluteLocation(player.getX(), player.getY()); //update map relative to players position
		long start = System.nanoTime();
		
		drawField(g); //Bottom layer, walking plain
		drawAssets(g); //Buildings, signs, things that don't move
		drawCharacters(g); //Player an any other top layer people
		
		System.out.println((System.nanoTime() - start)/1000000 + "ms per frame");
	}
	
	
	public void drawField(Graphics g){
		
		int row = 0;
		for( int i = top; i < bottom; i++){
			int col = 0;
			for( int j = left; j < right; j++ ){
				g.drawImage(tiles[field[i][j]].tileImage(), col*TILEWIDTH, row*TILEHEIGHT, null);
				col++;
			}
			row++;
		}
	}
	
	public void drawAssets(Graphics g){
		
	}
	
	public void drawCharacters(Graphics g){
		
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

	private void upResponse(){}
	private void downResponse(){}
	private void leftResponse(){}
	private void rightResponse(){}
	private void forwardResponse(){}
	private void backwardResponse(){}
}
