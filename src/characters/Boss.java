package characters;

import javax.swing.JPanel;

import mapEngine.Map;

public class Boss extends BattleObject{
	//enemies really need to extend battleobject, otherwise I can't use them and throw them in lists with other battle objects

	public Boss(String img, Map m){
		super(img, new BattleClass(), m);
		// I don't know exactly what this was doing, so I'm not sure this was the appropriate fix
	}

}
