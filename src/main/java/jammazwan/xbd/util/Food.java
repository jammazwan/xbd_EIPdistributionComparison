package jammazwan.xbd.util;

import java.util.ArrayList;
import java.util.List;

public class Food {
	/*
	 * lazy man's test database
	 */
	
	private static List<String> instance;
	
	public static List<String> get() {
		if(instance==null){
			instance = food();
		}
		return instance;
	}

	private static List<String> food() {
		List<String> food = new ArrayList<>();
		food.add("Pickles");
		food.add("Chips");
		food.add("Lettuce");
		food.add("Baked Beans");
		food.add("Potato Salad");
		food.add("Hot Dog Buns");
		food.add("Hot Dogs");
		food.add("Tomatoes");
		food.add("Onions");
		food.add("Salad Dressing");
		food.add("Salt Pepper");
		food.add("Dip");
		food.add("Ketchup");
		food.add("Brownies");
		food.add("Mustard");
		return food;
	}
}
