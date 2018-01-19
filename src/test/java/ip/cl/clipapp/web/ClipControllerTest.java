package ip.cl.clipapp.web;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import ip.cl.clipapp.Application;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@WebIntegrationTest("server.port:0")
public class ClipControllerTest {

    private static final String GOOGLE_COM = "http://www.google.com";
    private static final String GOOGLE_COM_SHORT = "b";

    @Value("${local.server.port}")
    private int port;

    private static final MultiValueMap<String, Object> PARAM_MAP = new LinkedMultiValueMap<>(1);
    private final RestTemplate REST_TEMPLATE = new RestTemplate();

    @BeforeClass
    public static void beforeClass() {
        PARAM_MAP.add("u", GOOGLE_COM);
    }

    @Test
    public void clipUrl() {
        ResponseEntity<String> response = REST_TEMPLATE.postForEntity("http://localhost:" + port, PARAM_MAP, String.class);
        assertThat(response.getBody(), equalTo(GOOGLE_COM_SHORT));
    }

    @Test
    public void unclipUrl() {
        // Add URL
        REST_TEMPLATE.postForEntity("http://localhost:" + port, PARAM_MAP, String.class);
        // Now the URL exists
        // We can retrieve it
        ResponseEntity<String> response = REST_TEMPLATE.postForEntity("http://localhost:" + port + "/" + GOOGLE_COM_SHORT, null, String.class);
        assertThat(response.getBody(), equalTo(GOOGLE_COM));
    }

    @Test(expected = HttpServerErrorException.class)
    public void unclipUrlNotFound() {
        // This time, we won't add the URL
        ResponseEntity<String> response = REST_TEMPLATE.postForEntity("http://localhost:" + port + "/" + GOOGLE_COM_SHORT, null, String.class);
        // Error excepted
        response.getBody();
    }

    @Test
    public void index() {
        ResponseEntity<String> response = REST_TEMPLATE.getForEntity("http://localhost:" + port, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

}
