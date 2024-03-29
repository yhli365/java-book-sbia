package readinglist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SimpleWebTest {

	@Value("${local.server.port}")
	private int port;

	@Test(expected = HttpClientErrorException.class)
	public void pageNotFound() {
		try {
			RestTemplate rest = new RestTemplate();
			rest.getForObject("http://localhost:{port}/bogusPage", String.class, port);
			fail("Should result in HTTP 404");
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
			throw e;
		}
	}

	@Test
	public void testRestTemplate() throws Exception {

		TestRestTemplate rest = new TestRestTemplate();
		String s = rest.getForObject("http://localhost:{port}/bogusPage", String.class, port);
		System.out.println("-----[testRestTemplate] \n" + s);

	}

}
