package mapEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import battleEngine.Enemy;
import character.NPC;
import inventory.Item;

public class Map {
	NPC[] npcs;
	Enemy[] enemies;
	Item [] drops;
	int worldID;
	TiledMap overworld;
	File data;
	ArrayList<Rectangle> blocked=new ArrayList<Rectangle>();
	ArrayList<Rectangle> leave=new ArrayList<Rectangle>();
	
	public Map(File mapfile, File data) throws SlickException{
		this.data=data;
		overworld=new TiledMap(mapfile.getPath());
		blocked();
		nextMap();
		try {
			parseData();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public ArrayList<Rectangle> getBlocked(){return blocked;}
	
	//parse .txt to generate relevant NPCs, monsters, and items
	private void parseData() throws FileNotFoundException{
		Scanner reader=new Scanner(data);
		worldID=reader.nextInt();
		reader.nextLine();
		String x=reader.nextLine();
		x.trim();
		String[] npc=x.split(",");
		npcs=new NPC[npc.length];
		int index=0;
		for(String s:npc){
			String n="res/characterData/";
			s=n.concat(s);
			npcs[index]=new NPC(s);
			index++;
		}
		if(reader.hasNextLine()){
			x=reader.nextLine();
			String[] monsters=x.split(",");
			enemies=new Enemy[monsters.length];
			index=0;
			for(String s: monsters){
				enemies[index]=new Enemy(s);
				index++;
			}
		}
		if(reader.hasNextLine()==true){
			index=0;
			x=reader.nextLine();
			String[] items=x.split(",");
			drops=new Item[items.length];
			for(String s: items){
				drops[index]=new Item(s);
				index++;
			}
		}
		reader.close();
	}
	
	//generates colliders for blocked tiles
	private void blocked(){
		int layer=1;
		for(int i=0; i<(overworld.getWidth());i++ ){
			for(int j=0;j<(overworld.getHeight());j++){
				int id=overworld.getTileId(i, j, layer);
				if(overworld.getTileProperty(id,"Blocked", "false")!=null){
					String result=overworld.getTileProperty(id, "Blocked", "false");
					if(result.contains("true")){
						Rectangle x=new Rectangle((float)i*overworld.getTileWidth(),
								(float)j*overworld.getTileHeight(),
								overworld.getTileWidth(),
								overworld.getTileHeight());
						blocked.add(x);
					}
				}
			}
		}
	}
	
	private void nextMap(){
		Rectangle left=new Rectangle(0f,0f,2f,1280f);
		Rectangle right=new Rectangle(1278f,0f,2f,1280f);
		Rectangle top=new Rectangle(0f,0f,1280f,2f);
		Rectangle bottom=new Rectangle(0f,1278f,1280f,2f);
		leave.add(left);
		leave.add(right);
		leave.add(top);
		leave.add(bottom);
	}
}
