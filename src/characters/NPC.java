package characters;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

import plotEngine.Plot;

public class NPC {
	String name;
	File dialogue;
	Image avatar;
	float xPosition;
	float yPosition;
	boolean inCollision=false;
	Rectangle collider;
	Scanner in;
	
	public NPC(String title, String address, String image, String x, String y) throws SlickException{
		name=title;
		dialogue=new File("address");
		avatar=new Image("image");
		xPosition=Float.parseFloat(x);
		yPosition=Float.parseFloat(y);
		collider=new Rectangle(avatar.getCenterOfRotationX()-avatar.getWidth()/2,
				avatar.getCenterOfRotationY()-avatar.getHeight()/2,
				avatar.getWidth(),
				avatar.getHeight());
		try {
			in=new Scanner(dialogue);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Rectangle getCollider(){return collider;}
	
	public void setInCollision(boolean a){inCollision=a;}
	public boolean getInCollision(){return inCollision;}
	
	public String relayDialogue(Plot current){
		String line=in.nextLine();
		return line;	
	}
	public void collision(Shape rectangle){
		if(collider.intersects(rectangle)){
			inCollision=true;
		}
	}

}
