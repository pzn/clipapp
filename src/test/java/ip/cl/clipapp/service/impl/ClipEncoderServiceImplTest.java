package ip.cl.clipapp.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import ip.cl.clipapp.Application;
import ip.cl.clipapp.service.impl.ClipEncoderServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ClipEncoderServiceImplTest {

    @Autowired
    private ClipEncoderServiceImpl clipEncoderService;

    @Test
    public void encode() {
        assertThat(clipEncoderService.decode("Q"), equalTo(42));
        assertThat(clipEncoderService.decode("vJ"), equalTo(1337));
    }

    @Test
    public void decode() {
        assertThat(clipEncoderService.encode(42), equalTo("Q"));
        assertThat(clipEncoderService.encode(1337), equalTo("vJ"));
    }

}
