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

import gameStateManager.GamePlay;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import textEngine.TextWindow;
import main.WoE;
import characters.*;

public class Map {
	
	private MainCharacter player;
	private OverworldParser world;
	private GamePlay game;
	private TextWindow textbox;
	
	//Tile information
	private Tile[][] field;
	private Image[] tileImages;
	private int mapLocation;
	private String locationName;
	
	//Window information
	private int top, bottom, right, left;
	private double xOffset = 0; //percentage x
	private double yOffset = 0;
	
	private static final int TILESACROSS = 32;
	private static final int TILESDOWN = 16;
	
	public static final int TILEWIDTH = WoE.WIDTH/TILESACROSS+1;
	public static final int TILEHEIGHT = WoE.HEIGHT/TILESDOWN+1;
	
	//TESTING
	private NPC thing;
	private ArrayList<NPC> charactersOnScreen;
	
	
	
	
	public Map(GamePlay g){
		mapLocation = 0; 
		game = g;
		
		world = new OverworldParser();
		world.parse(mapLocation);
		locationName = world.getName(mapLocation);
		field = world.getTiles(mapLocation);
		tileImages = world.getTileImages(mapLocation);

		//For testing only
		thing = new NPC("/CharacterPics/player.bmp", this);
		thing.setX(field[0].length/2);
		thing.setY(field.length/2-5);
		thing.move(WorldObject.UP);
		//Occupy by is not set
		charactersOnScreen = new ArrayList<NPC>();
		charactersOnScreen.add(thing);
	}
	public void setPlayer(MainCharacter c){
		player = c;

		c.setX(field[0].length/2);
		c.setY(field.length/2);
	}
	
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
				g.drawString(j+","+i, col*TILEWIDTH, row*TILEHEIGHT);
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
		//For testing only
		for(NPC c : charactersOnScreen){		
			//Needs to be offset too...
			g.drawImage(thing.getImage(),
					convertAbsoluteToRelativeX(c.getX())+(int)(xOffset),
					convertAbsoluteToRelativeY(c.getY())+(int)(yOffset),
					null);
		}
	}
	
	//Convert tiles to pixels
	//Off by 1?
	public int convertAbsoluteToRelativeX(int x){
		return (x-left)*TILEWIDTH;
	}
	public int convertAbsoluteToRelativeY(int y){
		return(y-top)*TILEHEIGHT;
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

	//Can I optimize this?
	private void upResponse(){
		if(getAdjacentTile(WorldObject.UP).occupied()){
			player.animate(WorldObject.UP);
		}
		else{ player.move(WorldObject.UP);}
	}
	private void downResponse(){
		if(getAdjacentTile(WorldObject.DOWN).occupied()){
			player.animate(WorldObject.DOWN);
		}
		else{ player.move(WorldObject.DOWN); }
	}
	private void leftResponse(){
		if(getAdjacentTile(WorldObject.LEFT).occupied()){
			player.animate(WorldObject.LEFT);
		}
		else{ player.move(WorldObject.LEFT); }
	}
	private void rightResponse(){
		if(getAdjacentTile(WorldObject.RIGHT).occupied()){
			player.animate(WorldObject.RIGHT);
		}
	else{ player.move(WorldObject.RIGHT); }
	}
	private void forwardResponse(){
		WorldObject person = getAdjacentTile(player.getDirection()).getObject();
		if(person != null){
			game.inConvo(true);
			//Start up text or purchase
			textbox.loadWithCharacter(person);
			System.out.println("Opening text box");
		}
		else{
			//there is nothing there
		}
	}
	private void backwardResponse(){}
	
	//Up block works, but not any others
	//Currently WRT player only, if we want to make this soft, we'll replace that
	private Tile getAdjacentTile(int direction){
		int x = player.getX();
		int y = player.getY();
		Tile t = null;
		switch (direction){
		case WorldObject.UP:
			t = field[y-1][x];
			break;
		case WorldObject.RIGHT:
			t = field[y][x+1];
			break;
		case WorldObject.DOWN:
			t = field[y+1][x];
			break;
		case WorldObject.LEFT:
			t = field[y][x-1];
			break;
		}
		return t;
	}
	public void occupy(WorldObject o){ field[o.getY()][o.getX()].setObject(o); }
	public void unoccupy(WorldObject o){ field[o.getY()][o.getX()].unsetObject(); }
	public void unoccupy(int x, int y){ field[y][x].unsetObject(); }
	
	public void setText(TextWindow dialog) { textbox = dialog; }
}

