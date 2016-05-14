package fr.lirmm.advanse.chv.acquisition;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Test {
     public static void main(String[] args) {

	JSONObject obj = new JSONObject();
	obj.put("na\"me", "mkyong.com");
	obj.put("a'ge", new Integer(100));

	JSONArray list = new JSONArray();
	list.add("msg\" 1");
	list.add("msg' 2");
	list.add("msg 3");

	obj.put("messages", list);
//
//	try {
//
//		FileWriter file = new FileWriter("c:\\test.json");
//		file.write(obj.toJSONString());
//		file.flush();
//		file.close();
//
//	} catch (IOException e) {
//		e.printStackTrace();
//	}

	System.out.println(obj.toJSONString());
	System.out.println(obj.toString());

     }

}
