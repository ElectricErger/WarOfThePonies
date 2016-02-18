package Parser;

import java.util.*;

import org.dom4j.*;

import characters.MainCharacter;
import characters.NPC;

public class DialogueParser {
	public void parseDialogue(Document document, NPC character, MainCharacter twilight){
		Element type=document.getRootElement();
		Element a;
		for(Iterator i=type.elementIterator("character");i.hasNext();){
			a=(Element)i.next();
			if(a.attributeValue("name").trim()==character.getName()){
				//kind of assuming all NPCs will have a name, if this assumption is faulty, will rethink xml doc format
				break;
			}
		}
		Iterator conditions;
			for(conditions=a.elementIterator("condition");conditions.hasNext();){
				Element b=(Element)conditions.next();
				String text=b.getText();
				String[] condition=text.split("\"");
				List<String> ifs=Arrays.asList(condition);
				ArrayList<Boolean> conditionValues=new ArrayList<Boolean>();
				while(ifs.contains("has")){
					conditionValues.add(has(twilight, ifs));
				}
				while(ifs.contains("quest")){
					conditionValues.add(quest(ifs));		
				}
			}
	}
	private boolean has(MainCharacter twilight, List<String> ifs){
		int x=ifs.indexOf("has");
		String item=ifs.get(x+1);
		if(twilight.getInventory().contains(item)){
			ifs.remove("has");
			ifs.remove(x+1);
			return true;
		}
		else{
			ifs.remove("has");
			ifs.remove(x+1);
			return false;
		}	
	}
	private boolean quest(List<String> ifs){
		int y=ifs.indexOf("quest");
		if(ifs.get(y+2)=="complete"){
			y=y+1;
			String reference=ifs.get(y);
			y=Integer.parseInt(reference);
			//method to retrieve quest with id y
			if(quest.complete=true){
				ifs.remove("quest");
				ifs.remove(y);
				ifs.remove(y+1);
				return true;
			}
			else {
				ifs.remove("quest");
				ifs.remove(y);
				ifs.remove(y+1);
				return false;
			}
		}
		else if(ifs.get(y+2)=="active"){
			y=y+1;
			String reference=ifs.get(y);
			y=Integer.parseInt(reference);
			//retrieve quest with id y
			if(quest.active==true){
				ifs.remove("quest");
				ifs.remove(y);
				ifs.remove(y+1);
				return true;
			}
			else{
				ifs.remove("quest");
				ifs.remove(y);
				ifs.remove(y+1);
				return false;
			}
		}
	}

}
