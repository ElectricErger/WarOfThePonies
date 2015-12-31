//If you change the protocol, you will rewrite this file with final variables for protocol numbers
package mapEngine;

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
			overworld = new RandomAccessFile("res/Maps/worldMap.map", "r");
			//overworld = new BufferedReader(new FileReader("res/Maps/worldMap.map"));
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
	
	public String getName(int location){
		//Looks up where we are and gets the name
		return "";
	}

	//MAYBE I SHOULD NAME THIS PARSE DATA
	public Tile[][] getTiles(int location){
		String[] mapData = findMapByIndex(location);

		if(mapData == null){
			System.out.println("An error has occured reading the map. Please contact the developers and reinstall the game.");
		}
		
		String[] header = mapData[0].split(",");
		String[] tilePics = mapData[1].split(",");		
		
		//Final data needed from header
		String name = header[1];
		int width = Integer.parseInt(header[2]);
		int height = Integer.parseInt(header[3]);
		
		
		//Process map to tiles
		tiles = new Tile[width][height];

		for(int row = 2; row<height; row++){
			String[] mapParse = mapData[row].split(",");
			for(int col = 0; col<width; col++){				
				try {
					tiles[row][col] = new Tile(
						false,
						ImageIO.read(getClass().getResourceAsStream(
							"/Floor/"+tilePics[Integer.parseInt(mapParse[col])-1])
						)
					);
				} catch (Exception e) { e.printStackTrace(); System.out.println(tilePics[Integer.parseInt(mapParse[col])-1]); }
				
			}
		}
		
		return tiles;
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
