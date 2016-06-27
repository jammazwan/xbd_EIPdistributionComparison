package jammazwan.xbd;

import java.util.List;
import java.util.Map;

import org.apache.camel.DynamicRouter;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.springframework.stereotype.Component;

@Component
public class DynamicRouterBean {
	@DynamicRouter
	public String route(String body, @Header(Exchange.SLIP_ENDPOINT) String previous,
			@Headers Map<String, Object> headers) {
		if (null != previous && previous.length() > 10) {
			previous = previous.substring(9);
		}
		List<String> activeMembers = (List<String>) headers.get("activeMembers");
		List<String> inactiveMembers = (List<String>) headers.get("inactiveMembers");
		List<String> rsvpYes = (List<String>) headers.get("rsvpYes");
		if (rsvpYes.size() == 15) {
			// abort! Quota full
			return null;
		}
		if (previous == null) {
			// 1st time
			return "direct:" + activeMembers.get(0);
		} else {
			String next = getNext(previous, activeMembers, inactiveMembers);
			if (next != null) {
				return next;
			} else {
				return null;
			}
		}
	}

	private String getNext(String previous, List<String> activeMembers, List<String> inactiveMembers) {
		String next = null;
		if (activeMembers.contains(previous)) {
			int i = getNextIndex(previous, activeMembers);
			if (i != activeMembers.size()) {
				next = "direct:" + activeMembers.get(i);
			} else {
				next = "direct:" + inactiveMembers.get(0);
			}
		} else if (inactiveMembers.contains(previous)) {
			int i = getNextIndex(previous, inactiveMembers);
			next = "direct:" + inactiveMembers.get(i);
		}
		return next;
	}

	private int getNextIndex(String previous, List<String> members) {
		int i = 0;
		for (i = 0; i < members.size(); i++) {
			if (members.get(i).equals(previous)) {
				break;
			}
		}
		return i + 1;
	}
}
