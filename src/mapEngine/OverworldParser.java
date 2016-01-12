//If you change the protocol, you will rewrite this file with final variables for protocol numbers
package mapEngine;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class OverworldParser {

	public static final int NORTH = 1;
	public static final int EAST = 2;
	public static final int SOUTH = 3;
	public static final int WEST = 4;
	
	public static final int TILE_PIXELS_X = 36;
	public static final int TILE_PIXELS_Y = 36;
	
	private static final int LINES_PER_WORLDMAP = 3;
	private static final int MAP_START_LINE = 1;
	
	private RandomAccessFile overworld;
	private int currentMap;
	private String mapName;

	private BufferedImage tileFile;
	private Tile[][] tiles;
	
	public OverworldParser(){
		try {
			overworld = new RandomAccessFile("res/maps/worldMap.map", "r");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("We are sorry, but we could not find vital files. Please reinstall the game.\n"
					+ "If you still experience difficulties please go to the GitHub Wiki.");
		}
	}
	
	//Get data
	public String getName(int location){
		if(location != currentMap){ parse(location); }
		return mapName;
	}
	public Tile[][] getTiles(int location){
		if(location != currentMap){ parse(location); }
		return tiles;
	}
	public BufferedImage getTileImages(int location){
		if(location != currentMap){ parse(location); }
		return tileFile;
	}

	//WIP - Called when you cross a boundary - From current map, along 
	public int changeLocation(int location, int dir){ 
		//Idea of line crossing: If on A, you go down past line XY, you switched maps.
		currentMap = 0;
		parse(currentMap);
		return currentMap;
	}
		
	public void parse(int location){
		String[] mapData = findMapByIndex(location);
		RandomAccessFile rawMap = null;
		try {
			parseHeader(mapData[0]);
			
			//Get map data
			rawMap = new RandomAccessFile("res/maps/"+mapData[1], "r");
			parseTileSheet(rawMap.readLine());
			ArrayList<String> map = isolateMap(rawMap);
			parseMap(map);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private String[] findMapByIndex(int location){
		try { overworld.seek(location*LINES_PER_WORLDMAP); } catch (Exception e1) { e1.printStackTrace(); }
		String[] mapData = new String[LINES_PER_WORLDMAP];
		try {
			for(int i = 0; i<LINES_PER_WORLDMAP; i++){
				
				mapData[i] = overworld.readLine();
			
			}
			return mapData;
		} catch (Exception e) {e.printStackTrace();}
		
		return null;
	}
	private void parseHeader(String headerData){
		String[] header = headerData.split(",");
		currentMap = Integer.parseInt(header[0]);
		mapName = header[1];
	}
	private void parseTileSheet(String location){
		try{
			tileFile = scale(ImageIO.read(getClass().getResourceAsStream("/tiles/"+location)));
		}
		catch(Exception e){}
	}
	private ArrayList<String> isolateMap(RandomAccessFile f) throws Exception{
		String s;
		ArrayList<String> map = new ArrayList<String>();
		while( !(s = f.readLine()).equals("") && s != null){
			map.add(s);
		}
		return map;
	}
 	private void parseMap(ArrayList<String> mapData){
 		int height = mapData.size();
 		int width = mapData.get(0).split(",").length;
 		tiles = new Tile[height][width];
 		
		for(int row = 0; row<height; row++){
			String[] mapRow = mapData.get(row).split(",");
			for(int col = 0; col<width; col++){
				tiles[row][col] = new Tile(Integer.parseInt(mapRow[col]));
			}
		}
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
}






