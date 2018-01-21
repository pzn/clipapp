package ip.cl.clipapp.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import ip.cl.clipapp.TinyUrlNotFoundException;
import ip.cl.clipapp.service.ExtenderService;
import ip.cl.clipapp.service.ShortenerService;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class ClipControllerTest {

    private static final String GOOGLE_COM = "http://www.google.com";
    private static final String ENCODED_GOOGLE_COM = "polopopo";

    private MockMvc mockMvc;

    @InjectMocks
    private ClipController clipController;
    @Mock
    private ShortenerService mockedShortenerService;
    @Mock
    private ExtenderService mockedExtenderService;

    @Before
    public void before() {
        mockMvc = standaloneSetup(clipController).build();
    }

    @Test
    public void can_clip_long_url() throws Exception {

        // Given
        given(mockedShortenerService.shorten(GOOGLE_COM))
                .willReturn(ENCODED_GOOGLE_COM);

        // Execute
        mockMvc.perform(post("/").param("u", GOOGLE_COM))
                // Verify
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.value", is(ENCODED_GOOGLE_COM)));
    }

    @Test
    public void can_unclip_tiny_url() throws Exception {

        // Given
        given(mockedExtenderService.extend(ENCODED_GOOGLE_COM))
                .willReturn(GOOGLE_COM);

        // Execute
        mockMvc.perform(post("/" + ENCODED_GOOGLE_COM))
                // Verify
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.url", is(GOOGLE_COM)));
    }

    @Test
    public void when_cannot_unclip_tiny_url__should_return_404() throws Exception {

        // Given
        doThrow(TinyUrlNotFoundException.class)
                .when(mockedExtenderService).extend(any());

        // Execute
        mockMvc.perform(post("/any-tiny-url"))
                // Verify
                .andExpect(status().isNotFound());
    }

    @Test
    public void can_redirect() throws Exception {

        // Given
        when(mockedExtenderService.extend("hlowurl"))
                .thenReturn("https://hello.world");

        // Execute
        mockMvc.perform(get("/hlowurl"))
                // Verify
                .andExpect(status().isFound())
                .andExpect(header().string("location", is("https://hello.world")));
    }

    @Test
    public void when_cannot_redirect_tiny_url__should_redirect_to_root() throws Exception {

        // Given
        doThrow(TinyUrlNotFoundException.class)
                .when(mockedExtenderService).extend("tiny-url-not-found");

        // Execute
        mockMvc.perform(get("/tiny-url-not-found"))
                // Verify
                .andExpect(status().isFound())
                .andExpect(header().string("location", is("/")));
    }
}
