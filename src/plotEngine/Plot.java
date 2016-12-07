package plotEngine;

import java.util.ArrayList;

public class Plot {
	Integer currentWorld;
	ArrayList<Quest> activeQuests;
	ArrayList<Quest> completedQuests;

	public Plot(int n){
		currentWorld=n;
		activeQuests=new ArrayList<Quest>();
		completedQuests=new ArrayList<Quest>();
	}
	
	public void updatePlot(){
		for(Quest q:activeQuests){
			if(q.getComplete()==true){
				completedQuests.add(q);
				activeQuests.remove(q);
			}
		}
	}
}

