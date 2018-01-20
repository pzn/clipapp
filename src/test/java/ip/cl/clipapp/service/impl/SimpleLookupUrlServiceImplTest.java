package ip.cl.clipapp.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ip.cl.clipapp.service.ClipEncoderService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleLookupUrlServiceImplTest {

    private static final int ID = 1;
    private static final String GOOGLE_COM = "https://www.google.com";
    private static final String ENCODED_GOOGLE_COM = "meow";

    @InjectMocks
    private SimpleLookupUrlServiceImpl lookupUrlService;
    @Mock
    private ClipEncoderService mockedClipEncoderService;

    @Test
    public void can_add_long_url() {

        // Given
        when(mockedClipEncoderService.encode(ID))
                .thenReturn(ENCODED_GOOGLE_COM);

        // Execute
        String tinyUrl = lookupUrlService.getOrAddLongUrl(GOOGLE_COM);

        // Verify
        assertThat(tinyUrl, equalTo(ENCODED_GOOGLE_COM));
    }

    @Test
    public void can_add_url_twice_and_expect_same_short_url_version() {

        // Given
        when(mockedClipEncoderService.encode(ID))
                .thenReturn(ENCODED_GOOGLE_COM);

        // Execute
        String firstTime = lookupUrlService.getOrAddLongUrl(GOOGLE_COM);
        String secondTime = lookupUrlService.getOrAddLongUrl(GOOGLE_COM);

        // Verify
        assertThat(firstTime, equalTo(ENCODED_GOOGLE_COM));
        assertThat(firstTime, equalTo(secondTime));
    }

    @Test
    public void can_get_long_url() {

        // Given
        when(mockedClipEncoderService.encode(ID))
                .thenReturn(ENCODED_GOOGLE_COM);
        when(mockedClipEncoderService.decode(ENCODED_GOOGLE_COM))
                .thenReturn(ID);
        String tinyUrl = lookupUrlService.getOrAddLongUrl(GOOGLE_COM);

        // Execute
        String longUrl = lookupUrlService.getLongUrl(tinyUrl);

        // Verify
        assertThat(longUrl, equalTo(GOOGLE_COM));
    }

    @Test
    public void when_short_url_does_not_exist__should_return_null() {

        // Given
        when(mockedClipEncoderService.encode(ID))
                .thenReturn(null);

        // Execute
        String tinyUrlNotFound = lookupUrlService.getLongUrl(ENCODED_GOOGLE_COM);

        // Verify
        assertThat(tinyUrlNotFound, is(nullValue()));
    }
}
