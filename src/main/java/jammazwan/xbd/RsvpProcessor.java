package jammazwan.xbd;

import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Component;

@Component
public class RsvpProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String eipEndpoint = exchange.getFromEndpoint().getEndpointUri();
		List<String> activeMembers = (List<String>) exchange.getIn().getHeader("activeMembers");
		List<String> rsvpYes = (List<String>) exchange.getIn().getHeader("rsvpYes");
		List<String> rsvpNo = (List<String>) exchange.getIn().getHeader("rsvpNo");
		String recipient = exchange.getProperty("CamelToEndpoint").toString();
		recipient = recipient.substring(9);
		if (eipEndpoint.endsWith("recipientlist")) {
			exchange.getIn().setBody("\n" + recipient
					+ " was invited, but since recipientlist is a send only, we can't display any reply");
		} else {
			String rsvp = getRsvp(recipient, activeMembers);
			if (rsvp.equals("Yes")) {
				rsvpYes.add(recipient);
			} else {
				rsvpNo.add(recipient);
			}
			if (eipEndpoint.endsWith("routingslip")) {
				writeRoutingSlipHeaders(recipient, exchange, rsvp);
			}
			exchange.getIn().setBody("\n" + recipient + " RSVPd as " + rsvp);
		}
	}

	private void writeRoutingSlipHeaders(String recipient, Exchange exchange, String rsvp) {
		if (rsvp.equals("Declined")) {
			reassignFood(recipient, exchange);
		}
	}

	private void reassignFood(String recipient, Exchange exchange) {
		Map<String, String> foodAssignments = (Map<String, String>) exchange.getIn().getHeader("foodAssignments");
		String food = null;
		String reassignName = null;
		boolean keepGoing = true;
		String newFood = null;
		for (String name : foodAssignments.keySet()) {
			if (!keepGoing) {
				newFood = foodAssignments.get(name) + " " + food;
				reassignName = name;
				break;
			}
			if (keepGoing && name.equals(recipient)) {
				food = foodAssignments.get(name);
				keepGoing = false;
			}
		}
		foodAssignments.remove(recipient);
		foodAssignments.put(reassignName, newFood);
	}

	/*
	 * This method is designed to work with all four EIP patterns tested, but
	 * only DynamicRouter consumes the higher percentage of inactive members.
	 * 
	 * If recipient is contained in the activeMembers, 80% yes, else 50% yes
	 */
	private String getRsvp(String recipient, List<String> activeMembers) {
		String answer = "Yes";
		if (activeMembers.contains(recipient)) {
			answer = get4in5yes();
		} else {
			answer = get1in2yes();
		}
		return answer;
	}

	private String get1in2yes() {
		int i = RandomUtils.nextInt(2);
		if (i == 0) {
			return "Declined";
		} else {
			return "Yes";
		}
	}

	private String get4in5yes() {
		int i = RandomUtils.nextInt(5);
		if (i == 0) {
			return "Declined";
		} else {
			return "Yes";
		}
	}

}
