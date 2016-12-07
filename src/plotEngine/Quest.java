package plotEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Quest {
	protected int id;
	File requirements;
	boolean active;
	boolean complete;
	Scanner reader;
	ArrayList<Objective> objectives;
	
	
	public Quest(File reqs) throws FileNotFoundException{
		active=false;
		complete=false;
		requirements=reqs;
		reader=new Scanner(requirements);
		id=reader.nextInt();
		objectives=new ArrayList<Objective>();
		while(reader.hasNextLine()){
			String x=reader.nextLine();
			String [] y=x.split(";");
			if(y[0].contains("Enemyobj")){
				String [] z=y[1].split(" ");
				int number=Integer.parseInt(z[0]);
				String type=z[1];
				DefeatObjective obj=new DefeatObjective(number, type, y[2]);
				objectives.add(obj);
			}
			else if (y[0].contains("NPCobj")){
				
			}
			else if (y[0].contains("Invobj")){
				
			}
		}
		
	}
	
	public boolean getActive() {return active;}
	public boolean getComplete() {return complete;}
	
	public void setActive(boolean x){active=x;}
	public void setComplete(boolean x){complete=x;}	
	
	public void checkComplete(){
		boolean finished=true;
		for(Objective x: objectives){
			if(x.getComplete()==true){
				finished=true;
			}
			else {
				finished=false;
				break;
			}

		}
		if(finished=false) setComplete(false);
		else if(finished=true) setComplete(true);
	}

}
