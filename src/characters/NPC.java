package characters;

import java.util.Hashtable;

import javax.swing.JPanel;

import mapEngine.Map;

public class NPC extends WorldObject{
	Hashtable<Integer, String> lines = new Hashtable<Integer, String>();
	
	
	public NPC(String imgLocation, Map m) {
		super(imgLocation, m);
	}
	public NPC(int id, String imgLocation, Map m) {
		super(imgLocation, m);
	}
	
	
	public void addLines(int plot, String line) { lines.put(plot, line); }
	public String getLine(int plot){ return lines.get(plot); }
}
