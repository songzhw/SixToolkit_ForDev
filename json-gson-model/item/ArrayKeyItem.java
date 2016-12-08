package your.company.model;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArrayKeyItem {
	public String key1;
	public int key2;
	public boolean key3;

	public ArrayKeyItem (JSONObject json){
		if(json != null){
			key1 = json.optString("key1");
			key2 = json.optInt("key2");
			key3 = json.optBoolean("key3");
		}
	}

	public static ArrayList<ArrayKeyItem> createWithJsonArray(JSONArray array) {
		if(array != null){
			int len = array.length();
			ArrayList<ArrayKeyItem> list = new ArrayList<ArrayKeyItem>();
			for(int i = 0 ; i < len ; i++){
				JSONObject obj = array.optJSONObject(i);
				ArrayKeyItem oneItem = new ArrayKeyItem(obj);
				list.add(oneItem);
			}
			return list;
		}
		return null;
	}

}