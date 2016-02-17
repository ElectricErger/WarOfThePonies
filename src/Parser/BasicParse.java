package Parser;

import java.io.*;
import java.util.*;
import org.dom4j.*;
import org.dom4j.io.*;

public class BasicParse {
	public Document readFile(File file) throws DocumentException{
		SAXReader reader=new SAXReader();
		Document document=reader.read(file);
		return document;
	}
	public void parse(Document document){
		String type=document.getRootElement().getName().trim();
		if(type=="Dialogue"){
			//use dialog parser
		}
		else if(type=="Character"){
			//call character creating parser
		}
	}

}
