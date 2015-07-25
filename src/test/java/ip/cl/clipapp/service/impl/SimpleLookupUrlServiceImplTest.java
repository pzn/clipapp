package ip.cl.clipapp.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import ip.cl.clipapp.Application;
import ip.cl.clipapp.service.LookupUrlService;

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
public class SimpleLookupUrlServiceImplTest {

    private static final String GOOGLE_COM       = "http://www.google.com";
    private static final String GOOGLE_COM_SHORT = "b";
    private static final String GOOGLE_CA        = "http://www.google.ca";
    private static final String GOOGLE_CA_SHORT  = "c";

    @Autowired
    private LookupUrlService    lookupUrlService;

    @Test
    public void getOrAddLongUrl() {
        assertThat(lookupUrlService.getOrAddLongUrl(GOOGLE_COM), equalTo(GOOGLE_COM_SHORT));
        assertThat(lookupUrlService.getOrAddLongUrl(GOOGLE_CA), equalTo(GOOGLE_CA_SHORT));
    }

    @Test
    public void getOrAddLongUrlTwice() {
        assertThat(lookupUrlService.getOrAddLongUrl(GOOGLE_COM), equalTo(GOOGLE_COM_SHORT));
        // Must return the same value
        assertThat(lookupUrlService.getOrAddLongUrl(GOOGLE_COM), equalTo(GOOGLE_COM_SHORT));
    }

    @Test
    public void getLongUrl() {
        assertThat(lookupUrlService.getOrAddLongUrl(GOOGLE_COM), equalTo(GOOGLE_COM_SHORT));
        assertThat(lookupUrlService.getLongUrl(GOOGLE_COM_SHORT), equalTo(GOOGLE_COM));
    }

    @Test
    public void getLongUrlKO() {
        assertThat(lookupUrlService.getLongUrl(GOOGLE_COM_SHORT), is(nullValue()));
    }

}
