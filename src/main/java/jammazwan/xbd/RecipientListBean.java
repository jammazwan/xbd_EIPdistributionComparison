package jammazwan.xbd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Headers;
import org.apache.camel.RecipientList;
import org.springframework.stereotype.Component;

@Component
public class RecipientListBean {
	@RecipientList
	public String[] route(@Headers Map<String, Object> headers) {
		List<String> recipientUris = new ArrayList<String>();
		for (String name : (List<String>) headers.get("activeMembers")) {
			recipientUris.add("direct:" + name);
		}
		String[] recipientList = new String[recipientUris.size()];
		recipientList = recipientUris.toArray(recipientList);
		return recipientList;
	}
}
