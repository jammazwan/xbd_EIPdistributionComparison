package jammazwan.xbd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Headers;
import org.apache.camel.RecipientList;
import org.apache.camel.RoutingSlip;
import org.springframework.stereotype.Component;

@Component
public class RoutingSlipBean {
	@RoutingSlip
	public String[] route(@Headers Map<String, Object> headers) {
		List<String> routingSlipURIs = new ArrayList<String>();
		for (String name : (List<String>) headers.get("activeMembers")) {
			routingSlipURIs.add("direct:" + name);
		}
		String[] routingslip = new String[routingSlipURIs.size()];
		routingslip = routingSlipURIs.toArray(routingslip);
		return routingslip;
	}
}
