package characters;

import java.awt.Graphics;

import main.Panel;
import mapEngine.Map;


public class MainCharacter extends BattleObject{
	
	public MainCharacter(String img, Map m) {
		super(img, new BattleClass(), m); //Hrm...maybe?
	}
	
	public void animate(int direction){
		walkingDirection = direction;
		Thread t = new Thread(){
			public void run(){
				for( int i = 0; i<FRAMES_PER_CYCLE; i++ ){
					if(i % FRAME_SPEED == 0){
						for(int walkingFrame = 0; walkingFrame<NUM_FRAMES; walkingFrame++){
							currentImage = imgSheet.getSubimage(
									walkingFrame*Map.TILEWIDTH,
									direction*Map.TILEHEIGHT,
									Map.TILEWIDTH,
									Map.TILEHEIGHT);
						}
						try { Thread.sleep(Panel.FPmS); }
						catch (Exception e) { e.printStackTrace(); }
					}
				}
				animationCleanup(direction);
			}
		};
		t.start();
	}
}
