package mapEngine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
//SCALE WHEN YOU INITIATE
public class Tile {
	private boolean solid;
	private BufferedImage img;
	
	
	public Tile(boolean solid, BufferedImage i){
		
		this.solid = solid;
		img = i;
		scale();
	}
	
	public boolean isSolid(){ return solid; }
	public BufferedImage tileImage(){ return img; }
	private void scale(){
		
		int scaleHeight = Map.TILEHEIGHT/img.getHeight();
		int scaleWidth = Map.TILEWIDTH/img.getWidth();
		
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.scale(scaleHeight, scaleWidth);
		g.drawImage(img, 0, 0, null); //Not sure if I need this
	}
}
