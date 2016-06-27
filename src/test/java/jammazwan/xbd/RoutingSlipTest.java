package jammazwan.xbd;

import java.io.File;

import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RoutingSlipTest extends CamelSpringTestSupport {

	@Override
	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/camel-routingslip-context.xml");
	}

	@Test
	public void testXbd() throws Exception {
		String outputFileName = "output_routingslip.txt";
		File file = new File(outputFileName);
		if (file.exists()) {
			file.delete();
		}
		MockEndpoint mock = getMockEndpoint("mock:assert");
		// not a sufficient test, requires human visual check
		mock.expectedFileExists(outputFileName);
		// sendBody - as recipientList has no replies
		template.sendBodyAndHeader("direct:routingslip", "seed", "CamelFileName", outputFileName);
		mock.assertIsSatisfied();
	}

}
