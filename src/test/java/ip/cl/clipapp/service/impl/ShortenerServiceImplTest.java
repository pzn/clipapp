package ip.cl.clipapp.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ip.cl.clipapp.service.LookupUrlService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShortenerServiceImplTest {

    private static final String HTTPS_URL = "https://www.google.com";
    private static final String HTTP_URL = "http://www.nooooooooooooooo.com";
    private static final String DRUNK_URL = "drunk://grroL..qqqq,fiifkw,xim";
    private static final String TINY_URL = "ijklmnop";

    @InjectMocks
    private ShortenerServiceImpl shortenerServiceImpl;
    @Mock
    private LookupUrlService mockedLookupUrlService;

    @Test
    public void can_shorten_https() throws Exception {

        // Given
        givenLookupUrlServiceReturns(HTTPS_URL, TINY_URL);

        // Execute
        String tinyUrl = shortenerServiceImpl.shorten(HTTPS_URL);

        // Verify
        assertThat(tinyUrl, equalTo(TINY_URL));
        verifyInteractions(HTTPS_URL, 1);
    }

    @Test
    public void can_shorten_http() throws Exception {

        // Given
        givenLookupUrlServiceReturns(HTTP_URL, TINY_URL);

        // Execute
        String tinyUrlForHttp = shortenerServiceImpl.shorten(HTTP_URL);

        // Verify
        assertThat(tinyUrlForHttp, equalTo(TINY_URL));
        verifyInteractions(HTTP_URL, 1);
    }

    @Test
    public void can_shorten_url_twice() throws Exception {

        // Given
        givenLookupUrlServiceReturns(HTTPS_URL, TINY_URL);

        // Execute
        String firstTime = shortenerServiceImpl.shorten(HTTPS_URL);
        String secondTime = shortenerServiceImpl.shorten(HTTPS_URL);

        // Verify
        assertThat(firstTime, equalTo(TINY_URL));
        assertThat(firstTime, equalTo(secondTime));
        verifyInteractions(HTTPS_URL, 2);
    }

    @Test
    public void when_long_url_is_null__should_throw_exception() throws Exception {

        // Execute
        try {
            shortenerServiceImpl.shorten(null);
        } catch (Exception e) {

            // Verify
            assertThat(e, is(instanceOf(IllegalArgumentException.class)));
            verifyZeroInteractions();
            return;
        }
        fail("exception expected!");
    }

    @Test
    public void when_long_url_is_empty__should_throw_exception() throws Exception {

        // Execute
        try {
            shortenerServiceImpl.shorten("");
        } catch (Exception e) {

            // Verify
            assertThat(e, is(instanceOf(IllegalArgumentException.class)));
            verifyZeroInteractions();
            return;
        }
        fail("exception expected!");
    }

    @Test
    public void when_long_url_uses_an_unrecognized_scheme__should_throw_exception() throws Exception {

        // Execute
        try {
            shortenerServiceImpl.shorten(DRUNK_URL);
        } catch (Exception e) {

            // Verify
            assertThat(e, is(instanceOf(IllegalArgumentException.class)));
            verifyZeroInteractions();
            return;
        }
        fail("exception expected!");
    }

    private void givenLookupUrlServiceReturns(String longUrl, String tinyUrl) {
        when(mockedLookupUrlService.getOrAddLongUrl(longUrl))
                .thenReturn(tinyUrl);
    }

    private void verifyZeroInteractions() {
        verifyInteractions(null, 0);
    }

    private void verifyInteractions(String longUrl, int times) {
        verify(mockedLookupUrlService, times(times)).getOrAddLongUrl(longUrl);
        verifyNoMoreInteractions(mockedLookupUrlService);
    }
}
