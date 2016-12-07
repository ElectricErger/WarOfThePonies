package states;

import java.awt.Font;

import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.*;
import org.newdawn.slick.*;



public class StartScreen extends BasicGameState {
	private static final int id=0;
	Image background;
	GameContainer WoE;
	StateBasedGame game;
	
	private final String[] SELECT = {
			"NEW GAME",
			"LOAD GAME",
			"OPTIONS",
			"EXIT GAME"
	};
	private final String[] OPTIONS = {
			"RESOLUTION",
			"KEY BINDINGS",
			"BACK"
	};
	private final String[] LOADS = { //Maybe we'll load some statistics like game time and map location
			"YES",
			"NO"
	};
	private int selected, optionsSelector, loadSelector;
	private boolean optionsSelected, loadSelected;
	private Font menuFont=new Font("Arial", Font.PLAIN,40);
	private Font selectFont=new Font("Arial",Font.BOLD,40);
	private TrueTypeFont bold;
	private TrueTypeFont plain;
	
	
	public StartScreen(){
		super();
		selected = optionsSelector = loadSelector = 0;
		optionsSelected = loadSelected = false;
		bold=new TrueTypeFont(selectFont,false);
		plain=new TrueTypeFont(menuFont,false);
	}
	
	private void displayMenu(){
		int i = 0;
		for(String s : SELECT){
			float posWidth=WoE.getWidth()/2;
			float posHeight=WoE.getHeight()/2;
			if(i == selected){
				bold.drawString(posWidth-(bold.getWidth(s)/2), posHeight+i*bold.getLineHeight(), s, Color.black);
				//Font should really scale better
			}
			else{
				plain.drawString(posWidth-(plain.getWidth(s)/2), posHeight+i*plain.getLineHeight(), s, Color.white);
			}
			i++;
		}
	}
	
	private void displayOptions(){
		Graphics draw=new Graphics();
		draw.setColor(Color.blue);
		draw.fillRect(WoE.getScreenWidth()/4, WoE.getHeight()/4,WoE.getScreenWidth()/2,WoE.getScreenWidth()/2);
		
		draw.setColor(Color.white);
		draw.drawRect(WoE.getScreenWidth()/4, WoE.getHeight()/4,WoE.getScreenWidth()/2,WoE.getScreenWidth()/2);
		
		
		String prompt = "OPTIONS";
		bold.drawString(getCenteredX(prompt, bold), WoE.getHeight()/3, prompt,Color.black);
		
		int i = 0;
		for(String s : OPTIONS){
			if(i == optionsSelector){
				bold.drawString(getCenteredX(s,bold), WoE.getHeight()/2+i*bold.getHeight(),s,Color.black);
			}
			else{
				plain.drawString(getCenteredX(s,plain), WoE.getHeight()/2+i*plain.getHeight(),s,Color.white);
			}
			i++;
		}
	}
	
	private void displayLoads(){
		Graphics draw=new Graphics();
		draw.setColor(Color.blue);
		int width=WoE.getWidth();
		int height=WoE.getHeight();
		draw.fillRect(
				width/4,
				height/4,
				width/2,
				height/2);
		draw.setColor(Color.white);
		draw.drawRect(
				width/4,
				height/4,
				width/2,
				height/2);
		
		String loadPrompt = "Do you want to load?";
		bold.drawString(getCenteredX(loadPrompt, bold), WoE.getHeight()/3,loadPrompt);
		
		int i = 0;
		for(String s : LOADS){
			if(i == loadSelector){
				bold.drawString(getCenteredX(s,bold), WoE.getHeight()/2+i*bold.getHeight(s), s,Color.black);
			}
			else{
				plain.drawString(getCenteredX(s,plain), WoE.getHeight()/2+i*plain.getHeight(s), s,Color.white);
			}
			i++;
		}
	}
	
	private int getCenteredX(String s, TrueTypeFont f){
		return (WoE.getWidth()-f.getWidth(s))/2;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		background=new Image("/res/bg.jpg");
		WoE=container;
		this.game=game;
		
	}
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, org.newdawn.slick.Graphics arg2) throws SlickException {
		background.draw(0, 0, WoE.getWidth(), WoE.getHeight());
		if(!(optionsSelected||loadSelected))displayMenu();
		else if(optionsSelected) displayOptions();
		else if(loadSelected) displayLoads();
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input=WoE.getInput();
		if(input.isKeyPressed(Input.KEY_DOWN)){
			downResponse();
		}
		else if(input.isKeyPressed(Input.KEY_UP)){
			upResponse();
		}
		else if(input.isKeyPressed(Input.KEY_ENTER)){
			forwardResponse();
		}

		else if(input.isKeyPressed(Input.KEY_BACK)){
			backwardResponse();
			System.exit(0);
		}
		else if (input.isKeyPressed(Input.KEY_ESCAPE)){
			System.exit(0);
		}

	}
	@Override
	public int getID() {
		return id;
	}
	private void forwardResponse(){
		if(!(loadSelected || optionsSelected)){
			switch(selected){
			case 0:
				game.enterState(1, new FadeOutTransition(),new HorizontalSplitTransition());
				break;
			case 1:
				loadSelected = true;
				break;
			case 2:
				optionsSelected = true;
				break;
			case 3:
				System.exit(0);
				break;
			}
		}
		else{
			if(optionsSelected){
				forwardOption();
			}
			else{
				forwardLoad();
			}
		}
	}
	
	private void forwardLoad(){
		switch(loadSelector){
		case 0:
			//BEGIN LOAD
			break;
		case 1:
			loadSelected = false;
			break;
		}
	}
	
	private void forwardOption(){
		
	}
	
	private void backwardResponse(){
		if(optionsSelected){
			optionsSelected = false;
		}
		if(loadSelected){
			loadSelected = false;
		}
	}
	
	private void upResponse(){
		if(!(optionsSelected || loadSelected)){
			selected--;
			if(selected < 0){ selected = SELECT.length-1; }
		}
		else{
			if(optionsSelected){
				optionsSelector--;
				if(optionsSelector < 0){ optionsSelector = OPTIONS.length-1; }
			}
			else{
				loadSelector--;
				if(loadSelector < 0){ loadSelector = LOADS.length-1; }
			}
		}
	}
	
	private void downResponse(){
		if(!(optionsSelected || loadSelected)){
			selected++;
			if(selected >= SELECT.length){ selected = 0; }
		}
		else{
			if(optionsSelected){
				optionsSelector++;
				if(optionsSelector >= OPTIONS.length){ optionsSelector = 0; }
			}
			else{
				loadSelector++;
				if(loadSelector >= LOADS.length){ loadSelector = 0; }
			}
		}
	}

}

