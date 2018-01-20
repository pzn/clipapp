package ip.cl.clipapp.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ip.cl.clipapp.TinyUrlNotFoundException;
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
public class ExtenderServiceImplTest {

    private static final String GOOGLE_COM = "http://www.google.com";
    private static final String ENCODED_GOOGLE_COM = "abcdefgh";

    @InjectMocks
    private ExtenderServiceImpl extenderServiceImpl;
    @Mock
    private LookupUrlService mockedLookupUrlService;

    @Test
    public void can_extend() throws Exception {

        // Given
        givenLookupUrlServiceReturns(GOOGLE_COM, ENCODED_GOOGLE_COM);

        // Execute
        String longUrl = extenderServiceImpl.extend(ENCODED_GOOGLE_COM);

        // Verify
        assertThat(longUrl, equalTo(GOOGLE_COM));
        verifyInteractions(1);
    }

    @Test
    public void can_extend_twice() throws Exception {

        // Given
        givenLookupUrlServiceReturns(GOOGLE_COM, ENCODED_GOOGLE_COM);

        // Execute
        String firstTime = extenderServiceImpl.extend(ENCODED_GOOGLE_COM);
        String secondTime = extenderServiceImpl.extend(ENCODED_GOOGLE_COM);

        // Verify
        assertThat(firstTime, equalTo(GOOGLE_COM));
        assertThat(firstTime, equalTo(secondTime));
        verifyInteractions(2);
    }

    @Test
    public void when_tiny_url_is_null__should_throw_exception() throws Exception {

        // Execute
        try {
            extenderServiceImpl.extend(null);
        } catch (Exception e) {

            // Verify
            assertThat(e, is(instanceOf(IllegalArgumentException.class)));
            verifyZeroInteractions();
            return;
        }
        fail("exception expected!");
    }

    @Test
    public void when_tiny_url_is_empty__should_throw_exception() throws Exception {

        // Execute
        try {
            extenderServiceImpl.extend("");
        } catch (Exception e) {

            // Verify
            assertThat(e, is(instanceOf(IllegalArgumentException.class)));
            verifyZeroInteractions();
            return;
        }
        fail("exception expected!");
    }

    @Test
    public void when_tiny_url_not_found__should_throw_exception() throws Exception {

        // Given
        givenLookupUrlServiceReturns(null, ENCODED_GOOGLE_COM);

        // Execute
        try {
            extenderServiceImpl.extend(ENCODED_GOOGLE_COM);
        } catch (Exception e) {

            // Verify
            assertThat(e, is(instanceOf(TinyUrlNotFoundException.class)));
            verifyInteractions(1);
            return;
        }
        fail("exception expected!");
    }

    private void givenLookupUrlServiceReturns(String longUrl, String tinyUrl) {
        when(mockedLookupUrlService.getLongUrl(tinyUrl))
                .thenReturn(longUrl);
    }

    private void verifyZeroInteractions() {
        verifyInteractions(0);
    }

    private void verifyInteractions(int times) {
        verify(mockedLookupUrlService, times(times)).getLongUrl(ENCODED_GOOGLE_COM);
        verifyNoMoreInteractions(mockedLookupUrlService);
    }
}
