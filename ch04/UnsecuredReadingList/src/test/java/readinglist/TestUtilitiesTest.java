package readinglist;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUtilitiesTest {

  @Autowired
  Environment env;
  
  @Test
  public void loadProperties() {
    
    assertEquals("bar", env.getProperty("foo"));
    assertEquals("123", env.getProperty("bar"));
    
  }
  
  
  @Configuration
  public static class TestConfig {
  }
}
