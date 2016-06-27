package jammazwan.xbd.util;

import java.util.Map;

public class Invited {

	public static String summary(Map<String, String> foodList) {
		StringBuffer sb = new StringBuffer();
		sb.append("Here is what the initial campout list looks like:\n");
		for (String name : foodList.keySet()) {
			sb.append("\t" + name + " is assigned to bring " + foodList.get(name) + "\n");
		}
		return sb.toString();
	}

}
