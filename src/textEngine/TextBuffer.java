package textEngine;

import java.util.ArrayList;

/**
 * A queue for text to be written
 */
public class TextBuffer {
	ArrayList<String> s;
	
	TextBuffer(){
		s = new ArrayList<String>();
	}
	
	public void add(String str){
		s.add(str);
	}
	
	public String getNext(){
		String text = s.get(0);
		s.remove(0);
		return text;
	}
}
