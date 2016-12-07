package plotEngine;

import java.io.File;

public class Objective {
	boolean complete;
	String description;
	public Objective(){
		complete=false;

	}
	public boolean getComplete(){return complete;}
	
	public void setComplete(boolean t){complete=t;}
	
	public String displayObjective(){
		return description;
	}
}