package jammazwan.xbd.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

public class NamesActiveInactive {
	/*
	 * lazy man's database
	 */

	private static Map<String, List<String>> instance;

	public static Map<String, List<String>> get() {
		if (instance == null) {
			instance = create();
		}
		return instance;
	}

	/*
	 * Takes a list of names and returns 15 random activeMembers, rest inactive
	 */
	private static Map<String, List<String>> create() {
		List<String> inlist = GroupMembers.get();
		ArrayList<String> outlist = new ArrayList<>();
		int activeLimit = 15;
		while (inlist.size() > activeLimit) {
			int i = (int) (Math.random() * (activeLimit - 1));
			try {
				outlist.add(inlist.get(i));
				inlist.remove(i);
			} catch (Exception e) {
				// do nothing
			}
		}
		HashMap<String, List<String>> value = new HashMap<>();
		value.put("activeMembers", inlist);
		value.put("inactiveMembers", outlist);
		return value;
	}

	public static String[] multicastTargets() {
		List<String> multicastUris = new ArrayList<String>();
		for (String name : (List<String>) get().get("activeMembers")) {
			multicastUris.add("direct:" + name);
		}
		String[] multicastList = new String[multicastUris.size()];
		multicastList = multicastUris.toArray(multicastList);
		return multicastList;
	}

}
// List<String>