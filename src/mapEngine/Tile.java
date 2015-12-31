package mapEngine;

import java.awt.image.BufferedImage;

public class Tile {
	private boolean solid;
	private BufferedImage img;
	
	public Tile(boolean solid, BufferedImage i){
		this.solid = solid;
		img = i;
	}
	
	public boolean isSolid(){ return solid; }
	public BufferedImage tileImage(){ return img; }
}
