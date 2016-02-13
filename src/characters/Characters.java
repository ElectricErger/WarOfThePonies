package characters;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import mapEngine.Map;

public class Characters {
	//Class variables
	private ArrayList<NPC> npcs = new ArrayList<NPC>();
	private Map mapObject;
	
	//Singleton
	public Characters(Map m){
		mapObject = m;
		try {
			FileReader fr = new FileReader("res/characterData/npcs.txt");
			BufferedReader br = new BufferedReader(fr);
			
			parseFile(br);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	private void parseFile(BufferedReader file){
		ArrayList<String> npcData = new ArrayList<String>();
		String nextLine;
		try {
			while((nextLine = file.readLine()) != null){
				if(nextLine.equals("")){ makeNewNPC(npcData); }
				else{ npcData.add(nextLine); }
			}
		} catch (Exception e) { e.printStackTrace(); }
	}
	private void makeNewNPC(ArrayList<String> npcData){
		String[] data = npcData.get(0).split(",");
		int id = Integer.parseInt(data[0]);
		String name = data[1];
		npcData.remove(0);
		
		String img = npcData.get(0);
		npcData.remove(0);
		
		NPC npc = new NPC(id, img, mapObject);
		npcs.add(npc);
		
		//The rest is plot data
		for(String line : npcData){
			String[] s = line.split("\\v");
			
			npc.addLines(Integer.parseInt(s[0]), s[1]);
		}
		
		npcData = new ArrayList<String>();
	}

	public NPC[] getNPCS(int[] npcIds){
		NPC[] n = new NPC[npcIds.length];
		for(int i = 0; i<n.length; i++){
			n[i] = npcs.get(npcIds[i]);
		}
		return n;
	}
}