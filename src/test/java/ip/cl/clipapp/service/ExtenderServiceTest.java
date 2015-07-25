package ip.cl.clipapp.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import ip.cl.clipapp.Application;
import ip.cl.clipapp.ClipAppException;

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
public class ExtenderServiceTest {

    @Autowired
    private ShortenerService    shortenerService;
    @Autowired
    private ExtenderService     extenderService;

    private static final String GOOGLE_COM       = "http://www.google.com";
    private static final String GOOGLE_COM_SHORT = "b";
    private static final String GOOGLE_CA        = "http://www.google.ca";
    private static final String GOOGLE_CA_SHORT  = "c";

    @Test
    public void extend() throws Exception {
        shortenerService.shorten(GOOGLE_COM);
        assertThat(extenderService.extend(GOOGLE_COM_SHORT), equalTo(GOOGLE_COM));

        shortenerService.shorten(GOOGLE_CA);
        assertThat(extenderService.extend(GOOGLE_CA_SHORT), equalTo(GOOGLE_CA));
    }

    @Test
    public void extendTwice() throws Exception {
        shortenerService.shorten(GOOGLE_COM);
        assertThat(extenderService.extend(GOOGLE_COM_SHORT), equalTo(GOOGLE_COM));
        // Must return the same value
        assertThat(extenderService.extend(GOOGLE_COM_SHORT), equalTo(GOOGLE_COM));
    }

    @Test(expected = ClipAppException.class)
    public void extendKO() throws Exception {
        extenderService.extend(GOOGLE_COM_SHORT);
    }

}
