package readinglist;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// @see http://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4
public class ServerWebTest {

	@Value("${local.server.port}")
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testSomething() throws Exception {

		RestTemplate test = new RestTemplate();
		String s = test.getForObject("http://localhost:{port}", String.class, port);
		System.out.println("-----[testSomething] http://localhost:" + port + "\n" + s);
	}

	@Test
	public void testSomething2() throws Exception {
		String s = restTemplate.getForObject("/", String.class);
		System.out.println("-----[testSomething2] /\n" + s);
	}

}
