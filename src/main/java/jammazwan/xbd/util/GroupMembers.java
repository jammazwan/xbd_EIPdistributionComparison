package jammazwan.xbd.util;

import java.util.ArrayList;
import java.util.List;

public class GroupMembers {
	/*
	 * lazy man's test database
	 */
	
	private static List<String> instance;
	
	public static List<String> get() {
		if(instance==null){
			instance = members();
		}
		return instance;
	}

	private static List<String> members() {
		List<String> members = new ArrayList<>();
		members.add("IslaOliver");
		members.add("ArabellaSilas");
		members.add("AuroraLevi");
		members.add("AdelineMilo");
		members.add("EleanorJack");
		members.add("PenelopeJasper");
		members.add("IsabellaElijah");
		members.add("AstridLeo");
		members.add("MiaHenry");
		members.add("VioletWyatt");
		members.add("AriaEthan");
		members.add("RoseLiam");
		members.add("TheaCaleb");
		members.add("CoraEli");
		members.add("AliceSebastian");
		members.add("ClaireTheodore");
		members.add("EmmaBenjamin");
		members.add("HazelOscar");
		members.add("NoraAustin");
		members.add("ImogenFelix");
		members.add("ElizabethWilliam");
		members.add("LucyLuke");
		members.add("EsmeMiles");
		members.add("ScarlettAxel");
		members.add("GraceThomas");
		members.add("EllaAndrew");
		members.add("MilaJames");
		return members;
	}
}
