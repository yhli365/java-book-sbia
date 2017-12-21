package readinglist;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
// @WebAppConfiguration
public class MockMvcWebTests {

	@Autowired
	WebApplicationContext webContext;
	private MockMvc mockMvc;

	@Before
	public void setupMockMvc() {
		mockMvc = MockMvcBuilders //
				.webAppContextSetup(webContext) //
				.apply(springSecurity()) //
				.build(); //
	}

	@Test
	public void homePage_unauthenticatedUser() throws Exception {
		mockMvc.perform(get("/")) //
				.andExpect(status().is3xxRedirection()) //
				.andExpect(header().string("Location", "http://localhost/login"));
	}

	@Test
	@WithUserDetails("craig")
	@WithMockUser(username = "craig", password = "password", roles = "READER")
	public void homePage_authenticatedUser() throws Exception {
		Reader expectedReader = new Reader();
		expectedReader.setUsername("craig");
		expectedReader.setPassword("password");
		expectedReader.setFullname("Craig Walls");

		mockMvc.perform(get("/")) //
				.andExpect(status().isOk()) //
				.andExpect(view().name("readingList")) //
				.andExpect(model().attribute("reader", //
						samePropertyValuesAs(expectedReader))) //
				.andExpect(model().attribute("books", hasSize(0))) //
				.andExpect(model().attribute("amazonID", "habuma-20"));
	}

}
