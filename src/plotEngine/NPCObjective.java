package plotEngine;

import characters.NPC;

public class NPCObjective extends Objective {
	NPC talker;
	
	
	public NPCObjective(String name, String desc) {
		super();
		//get NPC with name matching name from NPC ArrayList associated with current map
		description=desc;
	}
	
	protected void update(){
		
	}

}
