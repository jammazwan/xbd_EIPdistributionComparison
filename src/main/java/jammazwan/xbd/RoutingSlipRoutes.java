package jammazwan.xbd;

import java.util.List;
import java.util.Map;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.springframework.beans.factory.annotation.Autowired;

import jammazwan.xbd.util.NamesActiveInactive;

public class RoutingSlipRoutes extends RouteBuilder {

	@Autowired
	private RsvpProcessor rsvpProcessor;
	@Autowired
	private SetupHeadersProcessor setupHeadersProcessor;
	@Autowired
	private RoutingSlipBean routingSlipBean;
	@Autowired
	private RoutingSlipSummaryProcessor routingSlipSummaryProcessor;

	@Override
	public void configure() throws Exception {
		// addMemberRoutes() includes RSVP functionality for each invited member
		addMemberRoutes(getContext());
		from("direct:routingslip").process(setupHeadersProcessor).to("file://./").bean(routingSlipBean)
				.process(routingSlipSummaryProcessor).to("file://./?fileExist=Append");
	}

	private void addMemberRoutes(ModelCamelContext context) throws Exception {
		Map<String, List<String>> members = NamesActiveInactive.get();
		for (String name : members.get("inactiveMembers")) {
			RouteDefinition routeDefinition = new RouteDefinition("direct:" + name);
			routeDefinition.process(rsvpProcessor).to("file://./?allowNullBody=true&fileExist=Append");
			context.addRouteDefinition(routeDefinition);
		}
		for (String name : members.get("activeMembers")) {
			RouteDefinition routeDefinition = new RouteDefinition("direct:" + name);
			routeDefinition.process(rsvpProcessor).to("file://./?allowNullBody=true&fileExist=Append");
			context.addRouteDefinition(routeDefinition);
		}
	}

}
