package plotEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Quest {
	protected int id;
	File requirements;
	boolean active;
	boolean complete;
	Scanner reader;
	
	
	public Quest(File reqs) throws FileNotFoundException{
		active=false;
		complete=false;
		requirements=reqs;
		reader=new Scanner(requirements);
		id=reader.nextInt();
	}
	
	public boolean getActive() {return active;}
	public boolean getComplete() {return complete;}
	
	public void setActive(boolean x){active=x;}
	public void setComplete(boolean x){complete=x;}																					
	
	

}
