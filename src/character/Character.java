package character;

import java.awt.Font;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

public class Character {
	String name;
	int currentHP;
	int currentMP;
	int maxHP;
	int maxMP;
	int[] attacks;
	int damageDie;
	int speed=5;
	int defence=10;
	SpriteSheet avatar;
	Image currentAvatar;
	float positionX=320f;
	float positionY=240f;
	Random r=new Random();
	int controller;
	Rectangle collider;
	
	public Character(String name,int hp, int mp, Image face,int controller){
		this.name=name;
		maxHP=hp;
		currentHP=hp;
		maxMP=mp;
		currentMP=mp;
		avatar=new SpriteSheet(face, 32, 32);
		currentAvatar=avatar.getSprite(0, 0);
		damageDie=r.nextInt(3)+3;
		attacks=new int[] {0,1,2};
		this.controller=controller;
		collider=new Rectangle(positionX,positionY,32,32);
	}
	public int getHP(){return currentHP;}
	public int getMP(){return currentMP;}
	public int getMaxHP(){return maxHP;}
	public int getMaxMP(){return maxMP;}
	public int[] getAttacks(){return attacks;}
	public int getDamageDie(){return damageDie;}
	public int getSpeed(){return speed;}
	public Image getAvatar(){return currentAvatar;}
	public SpriteSheet getSprites(){return avatar;}
	public float getXPos(){return positionX;}
	public float getYPos(){return positionY;}
	public int getDefence(){return defence;}
	public int getController(){return controller;}//if 0, user controlled, if 1, ai controlled
	public Rectangle getCollider(){return collider;}
	
	public void setHP(int HP){currentHP=HP;}
	public void setMP(int MP){currentMP=MP;}
	public void setMaxHP(int max){maxHP=max;}
	public void setMaxMP(int max){maxMP=max;}
	public void setAvatar (Image a){currentAvatar=a;}
	public void setSpeed (int speed){this.speed=speed;}
	public void setX(float d){
		positionX=d;
		collider.setX(d);
		}
	public void setY(float move){
		positionY=move;
		collider.setY(move);
		}
	public void render(){
		currentAvatar.draw(collider.getX(), collider.getY());
	}

}
