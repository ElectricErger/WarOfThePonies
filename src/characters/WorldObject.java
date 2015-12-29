package characters;

import javax.swing.JPanel;

import textEngine.TextWindow;
import mapEngine.Location;
	/**
	 * All types of objects that are visible in the world will use this class
	 * If you can see the thing in the world and interact with it, it's here.
	 * People, bosses, players, doors, signs, etc.
	 **/
public abstract class WorldObject {
	
	private JPanel context; //IS THIS THE BEST IDEA? MAYBE SOMEWHERE ELSE
	
	
	//Geographical data
	private Location area; //Perhaps you can be in many locations
	//On the map you are here
	private double x;
	private double y;

	//Update locations
	public void setX(double x){ this.x = x; }
	public void setY(double y){ this.y = y; }
	public void Location(Location loc){ area = loc; }
	
	//Get location
	public double getX(){ return x; }
	public double getY(){ return y; }
	public boolean amIHere( Location loc ){
		return area == loc;
	}
	
	
	
	
	//Interaction
	private boolean isTrigger; //Triggers an event like a battle
	private String[] dialog; //Perhaps this should be a hash table and allow for people to have different dialog depending on where in the plot you are
	private boolean inConvo;
	
	//What happens when you press A
	public TextWindow interact(){
		TextWindow t = new TextWindow(this, context);
		for(String s : dialog){
			if(s != null){
				inConvo = true;
				t.write(s); //We'll write our dialog to a buffer for it to use as it pleases
			}
		}
		if (isTrigger){
			//Trigger the event
		}
		return t;
	}
	public void setConvo(boolean b){ inConvo = b; }
	public boolean getConvo(){ return inConvo; }

	public WorldObject(JPanel panel, String[] s){
		context = panel;
		dialog = s.clone();
	}
}
