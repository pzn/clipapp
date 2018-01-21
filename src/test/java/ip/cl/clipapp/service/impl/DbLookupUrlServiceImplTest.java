package ip.cl.clipapp.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ip.cl.clipapp.model.entity.ClipUrl;
import ip.cl.clipapp.repository.ClipUrlRepository;
import ip.cl.clipapp.service.ClipEncoderService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbLookupUrlServiceImplTest {

    private static final int CLIP_URL_ID = 1;
    private static final String GOOGLE_COM = "http://www.google.com";
    private static final String ENCODED_GOOGLE_COM = "b";

    @InjectMocks
    private DbLookupUrlServiceImpl dbLookupUrlServiceImpl;
    @Mock
    private ClipEncoderService mockedClipEncoderService;
    @Mock
    private ClipUrlRepository mockedClipUrlRepository;

    @Test
    public void can_add_long_url() {

        // Given
        givenClipUrlRepositoryRecord(CLIP_URL_ID, GOOGLE_COM);
        givenEncodedValueReturnsTinyUrl(CLIP_URL_ID, ENCODED_GOOGLE_COM);

        // Execute
        String tinyUrl = dbLookupUrlServiceImpl.getOrAddLongUrl(GOOGLE_COM);

        // Verify
        assertThat(tinyUrl, equalTo(ENCODED_GOOGLE_COM));
        verifyAddLongUrlInteractions(CLIP_URL_ID, GOOGLE_COM, 1);
    }

    @Test
    public void can_add_long_url_twice_and_expect_same_short_url_version() {

        // Given
        givenClipUrlRepositoryRecord(CLIP_URL_ID, GOOGLE_COM);
        givenEncodedValueReturnsTinyUrl(CLIP_URL_ID, ENCODED_GOOGLE_COM);

        // Execute
        String firstTime = dbLookupUrlServiceImpl.getOrAddLongUrl(GOOGLE_COM);
        String secondTime = dbLookupUrlServiceImpl.getOrAddLongUrl(GOOGLE_COM);

        // Verify
        assertThat(firstTime, equalTo(ENCODED_GOOGLE_COM));
        assertThat(firstTime, equalTo(secondTime));
        verifyAddLongUrlInteractions(CLIP_URL_ID, GOOGLE_COM, 2);
    }

    @Test
    public void can_get_long_url() {

        // Given
        givenDecodedTinyUrlReturnsValue(ENCODED_GOOGLE_COM, CLIP_URL_ID);
        givenClipUrlRepositoryRecord(CLIP_URL_ID, GOOGLE_COM);

        // Execute
        String longUrl = dbLookupUrlServiceImpl.getLongUrl(ENCODED_GOOGLE_COM);

        // Verify
        assertThat(longUrl, equalTo(GOOGLE_COM));
        verifyGetTinyUrlInteractions(CLIP_URL_ID, ENCODED_GOOGLE_COM);
    }

    @Test
    public void when_short_url_does_not_exist__should_return_null() {

        // Given
        givenDecodedTinyUrlReturnsValue(ENCODED_GOOGLE_COM, 2);
        givenClipUrlRepositoryRecord(2, null);

        // Execute
        String urlNotFound = dbLookupUrlServiceImpl.getLongUrl(ENCODED_GOOGLE_COM);

        // Verify
        assertThat(urlNotFound, is(nullValue()));
        verifyGetTinyUrlInteractions(2, ENCODED_GOOGLE_COM);
    }

    private void givenClipUrlRepositoryRecord(int id, String longUrl) {
        when(mockedClipUrlRepository.findByLongUrl(longUrl))
                .thenReturn(new ClipUrl(id, longUrl));
        when(mockedClipUrlRepository.findOne(id))
                .thenReturn(new ClipUrl(id, longUrl));
    }

    private void givenEncodedValueReturnsTinyUrl(int value, String expectedTinyUrl) {
        when(mockedClipEncoderService.encode(value))
                .thenReturn(expectedTinyUrl);
    }

    private void givenDecodedTinyUrlReturnsValue(String tinyUrl, int expectedValue) {
        when(mockedClipEncoderService.decode(tinyUrl))
                .thenReturn(expectedValue);
    }

    private void verifyAddLongUrlInteractions(int id, String longUrl, int times) {
        verify(mockedClipUrlRepository, times(times)).findByLongUrl(longUrl);
        verifyNoMoreInteractions(mockedClipUrlRepository);
        verify(mockedClipEncoderService, times(times)).encode(id);
        verifyNoMoreInteractions(mockedClipEncoderService);
    }

    private void verifyGetTinyUrlInteractions(int id, String tinyUrl) {
        verify(mockedClipEncoderService).decode(tinyUrl);
        verifyNoMoreInteractions(mockedClipEncoderService);
        verify(mockedClipUrlRepository).findOne(id);
        verifyNoMoreInteractions(mockedClipUrlRepository);
    }
}
