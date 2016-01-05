/*
 * Plot is an object that maintains where we are in the story.
 * It tells us:
 * 	What triggers the next plot point
 * 		 EX. scripted fights, scripted text, talking triggers, location triggers
 * This may fit into GameStateManager?
 */

package gameStateManager;

public class Plot {

	private int currentPlot;
	
	public Plot(){
		currentPlot = 0;
	}
	
	public void nextTrigger(){
		switch (currentPlot){
		case 0:
			//If map position = xy on map z
			break;
		case 1:
			//If talking to XYZ
			break;
		case 2:
			//After a battle with XYZ
			break;
			//...
		}
	}
	
	
	public void loadPlot(int i){
		currentPlot = i;
	}
	
}
