package plotEngine;

import java.util.ArrayList;

import battleEngine.Enemy;

public class DefeatObjective extends Objective {
	ArrayList<Enemy> enemies;
	
	
	public DefeatObjective(int x, String type, String desc) {
		super();
		enemies=new ArrayList<Enemy>();
		int y;
		for(y=1; y<=x; y++){
			Enemy toKill=new Enemy(type);
			enemies.add(toKill);
		}
		description=desc;
	}
	public void isComplete(){
		boolean killed=true;
		for(Enemy x:enemies){
			if(x.getDefeated()==true){
				killed=true;
			}
			else if(x.getDefeated()==false){
				killed=false;
			}
			if(killed==false)break;
		}
		if(killed==true)setComplete(true);
		else setComplete(false);
	}

}
