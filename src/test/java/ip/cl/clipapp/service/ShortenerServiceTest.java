package ip.cl.clipapp.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import ip.cl.clipapp.Application;
import ip.cl.clipapp.ClipAppRuntimeException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ShortenerServiceTest {

    private static final String GOOGLE_COM       = "http://www.google.com";
    private static final String GOOGLE_COM_SHORT = "b";
    private static final String GOOGLE_CA        = "http://www.google.ca";
    private static final String GOOGLE_CA_SHORT  = "c";
    private static final String DRUNK_URL        = "grroL..qqqq,fiifkw,xim";

    @Autowired
    private ShortenerService    shortenerService;

    @Test
    public void shorten() {
        assertThat(shortenerService.shorten(GOOGLE_COM), equalTo(GOOGLE_COM_SHORT));
        assertThat(shortenerService.shorten(GOOGLE_CA), equalTo(GOOGLE_CA_SHORT));
    }

    @Test
    public void shortenTwice() {
        assertThat(shortenerService.shorten(GOOGLE_COM), equalTo(GOOGLE_COM_SHORT));
        // Must return the same value
        assertThat(shortenerService.shorten(GOOGLE_COM), equalTo(GOOGLE_COM_SHORT));
    }

    @Test(expected = ClipAppRuntimeException.class)
    public void shortenKO() {
        shortenerService.shorten(DRUNK_URL);
    }

}
