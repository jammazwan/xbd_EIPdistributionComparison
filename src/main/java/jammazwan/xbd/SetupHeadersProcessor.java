package jammazwan.xbd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import jammazwan.xbd.util.Food;
import jammazwan.xbd.util.FoodList;
import jammazwan.xbd.util.Invited;
import jammazwan.xbd.util.NamesActiveInactive;

@Component
public class SetupHeadersProcessor implements Processor {
	/*
	 * Setup is the same for all 4 EIPs, even though some may not utilize all of
	 * what is setup
	 */

	@Override
	public void process(Exchange exchange) throws Exception {
		Map<String, List<String>> members = NamesActiveInactive.get();
		exchange.getIn().setHeader("inactiveMembers", members.get("inactiveMembers"));
		exchange.getIn().setHeader("activeMembers", members.get("activeMembers"));
		exchange.getIn().setHeader("foodAssignments", FoodList.get());
		exchange.getIn().setHeader("rsvpYes", new ArrayList<String>());
		exchange.getIn().setHeader("rsvpNo", new ArrayList<String>());
		exchange.getIn().setHeader("textSummary", Invited.summary(FoodList.get()));

		exchange.getIn().setBody(
				Invited.summary(FoodList.get()) + "\ninactiveMembersCount=" + members.get("inactiveMembers").size() + "\n\n");
	}

}
