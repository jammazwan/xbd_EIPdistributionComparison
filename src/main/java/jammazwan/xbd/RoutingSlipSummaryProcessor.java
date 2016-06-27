package jammazwan.xbd;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class RoutingSlipSummaryProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String newBody = makeSummary(exchange);
		exchange.getIn().setBody(newBody);
	}

	private String makeSummary(Exchange exchange) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n\nAfter the declines and food reassignments, this is what it looks like:");
		Map<String, String> foodAssignments = (Map<String, String>) exchange.getIn().getHeader("foodAssignments");
		for (String name : foodAssignments.keySet()) {
			sb.append("\n\t" + name + " brings " + foodAssignments.get(name));
		}
		return sb.toString();
	}

}
