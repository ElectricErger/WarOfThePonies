//If you change the protocol, you will rewrite this file with final variables for protocol numbers
package mapEngine;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.RandomAccessFile;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

public class OverworldParser {

	public static final int NORTH = 1;
	public static final int EAST = 2;
	public static final int SOUTH = 3;
	public static final int WEST = 4;
	
	private RandomAccessFile overworld;
	//private BufferedReader overworld;
	private int currentMap;
	private Tile[][] tiles;
	private Image[] tileArray;
	private BufferedImage tileFile;
	private String mapName;
	private int width, height;

	public static final int TILE_PIXELS_X = 36;
	public static final int TILE_PIXELS_Y = 36;
	//Surrounding maps and conditions to change .... maybe in Map?
	
	/*
	 * World map formatting:
	 * <LOCATION NUMBER>,<LOCATION NAME>,<WIDTH>,<HEIGHT>
	 * <TILE1>,<TILE2>,<TILE3>,<TILE4>....
	 * <1,1> ............ <WIDTH,1>
	 * <1,2>
	 * .
	 * .
	 * .
	 * <1,HEIGHT>........ <WIDTH,HEIGHT>
	 * ----Other parameters for things like connecting maps
	 *  ------------Extra line---------------
	 * 
	 * E.G.
	 * 1,DogVille,3,3
	 * road.bmp,grass.bmp
	 * 2,1,2
	 * 1,1,1
	 * 2,1,2
	 * CONNECTING MAPS
	 * 
	 * <EOF>
	 * 
	 * This is for now. This should be updated to be primarily connections
	 */
	
	public OverworldParser(){
		try {
			overworld = new RandomAccessFile("res/maps/worldMap.map", "r");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("We are sorry, but we could not find vital files. Please reinstall the game.\n"
					+ "If you still experience difficulties please go to the GitHub Wiki.");
		}
	}
	
	//I guess the towns will be numbered
	public int changeLocation(int location, int dir){ //Perhaps giving the XY coordinates and direction and we can do a line crossing thing
		
		//Idea of line crossing: If on A, you go down past line XY, you switched maps.
		int newLoc = 0;
		//Look through the map file for location
		//Look up/down/right/left of that and get our answer
		return newLoc;
	}
	
	//These 3 are still being workshopped a bit
	public String getName(int location){
		if(location != currentMap){ parse(location); }
		return mapName;
	}
	public Tile[][] getTiles(int location){
		return tiles;
	}
	public BufferedImage getTileImages(int location){
		if(location != currentMap){ parse(location); }
		return tileFile;
	}

	
	public void parse(int location){
		String[] mapData = findMapByIndex(location);

		if(mapData == null){
			System.out.println("An error has occured reading the map. Please contact the developers and reinstall the game.");
			System.exit(0);
		}
		
		String[] header = mapData[0].split(",");
		
		//Final data needed from header
		mapName = header[1];
		width = Integer.parseInt(header[2]);
		height = Integer.parseInt(header[3]);
		
		//Parse Images
		//String[] tilePics = mapData[1].split(",");
		String tilePics = mapData[1];
		parseTileImages(tilePics);

		//Parse Tiles
		tiles = new Tile[height][width];
		parseMap(mapData);
	}
	

	private void parseTileImages(String location){
		try{
			tileFile = scale(ImageIO.read(getClass().getResourceAsStream("/tiles/"+location)));
		}
		catch(Exception e){}
	}
	private BufferedImage scale(Image img){
		int currentX = img.getWidth(null);
		BufferedImage imgSheet;
		img = img.getScaledInstance(
				currentX/TILE_PIXELS_X*Map.TILEWIDTH,
				Map.TILEHEIGHT,
				0);
		imgSheet = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		imgSheet.createGraphics().drawImage(img, 0, 0, null);
		imgSheet.createGraphics().dispose();
		return imgSheet;
	}
	
	private void parseMap(String[] mapData){
		for(int row = 2; row<height; row++){
			String[] mapRow = mapData[row].split(",");
			for(int col = 0; col<width; col++){		
				tiles[row][col] = new Tile(Integer.parseInt(mapRow[col]));
			}
		}
	}
	
	private String[] findMapByIndex(int location){
		try { overworld.seek(0); } catch (Exception e1) { e1.printStackTrace(); }
		
		String[] mapData = {};
		String streamData;
		try {
			while((streamData = overworld.readLine()) != null){
				
				String[] s = streamData.split(",");
				boolean rightMap = (location == Integer.parseInt(s[0]));
				
				int numLinesToParse = Integer.parseInt(s[3])+4; //Map + Tiles,Options,NewLine
				mapData = new String[numLinesToParse];
				mapData[0] = streamData;
				for(int i = 1; i<numLinesToParse; i++){ mapData[i] = overworld.readLine(); } //0 is taken by header information
				
				if(rightMap){
					overworld.seek(0); 
					return mapData;
				}
			}
		} catch (Exception e) {e.printStackTrace();}
		
		return null;
	}
}
