package characters;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

import plotEngine.Plot;

public class NPC {
	String name;
	Image avatar;
	float xPosition;
	float yPosition;
	boolean inCollision=false;
	Rectangle collider;
	File data;
	
	public NPC (String file){
		data=new File(file);
		try {
			parseData();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public NPC(String title, String image, String x, String y) throws SlickException{
		name=title;
		avatar=new Image("image");
		xPosition=Float.parseFloat(x);
		yPosition=Float.parseFloat(y);
		collider=new Rectangle(avatar.getCenterOfRotationX()-avatar.getWidth()/2,
				avatar.getCenterOfRotationY()-avatar.getHeight()/2,
				avatar.getWidth(),
				avatar.getHeight());
	}
	
	public Rectangle getCollider(){return collider;}
	public String getName(){return name;}
	public float getX(){return xPosition;}
	public float getY(){return yPosition;}
	public Image getAvatar(){return avatar;}
	
	public void setInCollision(boolean a){inCollision=a;}
	public boolean getInCollision(){return inCollision;}
	
	public void collision(Shape rectangle){
		if(collider.intersects(rectangle)){
			inCollision=true;
		}
	}

		
	private void parseData() throws FileNotFoundException{
		Scanner reader=new Scanner(data);
		name=reader.nextLine();
		try {
			avatar=new Image("res/characterData/"+reader.nextLine());
		} catch (SlickException e) {
			e.printStackTrace();
		}
		xPosition=(float)Integer.parseInt(reader.nextLine());
		yPosition=(float)Integer.parseInt(reader.nextLine());
		collider=new Rectangle(xPosition, yPosition, 32, 32);
		reader.close();
		
	}

}
