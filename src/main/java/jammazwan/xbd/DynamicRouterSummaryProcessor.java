package jammazwan.xbd;

import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class DynamicRouterSummaryProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String newBody = makeSummary(exchange);
		exchange.getIn().setBody(newBody);
	}

	private String makeSummary(Exchange exchange) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n\nAs it was run from the DynamicRouter, below is how it assembled:");
		Map<String, String> foodAssignments = (Map<String, String>) exchange.getIn().getHeader("foodAssignments");
		List<String> rsvpYes = (List<String>) exchange.getIn().getHeader("rsvpYes");
		List<String> rsvpNo = (List<String>) exchange.getIn().getHeader("rsvpNo");
		sb.append("\n\nThis was the original list, look below for modifications");
		for (String name : foodAssignments.keySet()) {
			sb.append("\n\t" + name + " brings " + foodAssignments.get(name));
		}
		sb.append("\n\nThis is a list of the RSVPS that said yes. \n"
				+ "Where the food assignment is null, more code needs to be written to fetch the food list from the declines.");
		for (String name : rsvpYes) {
			sb.append("\n\t" + name + " says YES " + foodAssignments.get(name));
		}
		sb.append("\n\nThis is a list of the RSVPS that said no. \n"
				+ "It can be mined with more code to provide the replacements above with food to bring.");
		for (String name : rsvpNo) {
			sb.append("\n\t" + name + " Declines " + foodAssignments.get(name));
		}
		return sb.toString();
	}

}
