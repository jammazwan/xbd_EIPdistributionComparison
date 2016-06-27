package jammazwan.xbd.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodList {
	/*
	 * lazy man's test database
	 */

	private static Map<String, String> instance;

	public static Map<String, String> get() {
		if (instance == null) {
			instance = foodList();
		}
		return instance;
	}

	private static Map<String, String> foodList() {
		Map<String, String> map = new HashMap<>();
		List<String> food = Food.get();
		List<String> activeMembers = NamesActiveInactive.get().get("activeMembers");
		for (int i = 0; i < activeMembers.size(); i++) {
			map.put(activeMembers.get(i), food.get(i));
		}
		return map;
	}
}
