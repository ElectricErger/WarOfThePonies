package DialogEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.ElementIterator;

import character.NPC;
import plotEngine.Quest;

public class Display {
	NPC speaker;
	File dialog;
	String output;
	String[] responses;
	Document doc;
	
	public Display(NPC speak, int id){
		speaker=speak;
		dialog=new File("res/characterData/"+speaker.getName()+"dialog"+id+".xml");
		try {
			parse();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public String getOutput(){return output;}
	
	public void parse() throws DocumentException{
		SAXReader reader=new SAXReader();
		Document document=reader.read(dialog);
		doc=document;
	}
	public void getFirstLine(){
		Element initial=doc.getRootElement();
		output=initial.getText();
	}	

}
