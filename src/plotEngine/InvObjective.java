package plotEngine;

public class InvObjective extends Objective {
	
	int quantity;
	String type;
	boolean[] inInv;
	
	public InvObjective(int quantity, String type, String desc) {
		super();
		this.quantity=quantity;
		description=desc;
		this.type=type;
		inInv=new boolean[quantity];
		for(boolean b:inInv)b=false;
	}
	
	@Override
	protected void update(){
		//int index=0; for each item in inventory, if type==type {inInv[index]=true; index++}
		//for each boolean in inInv, if boolean==false {setComplete(false);}
	}

}
