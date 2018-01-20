package ip.cl.clipapp.service.impl;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ClipEncoderServiceImplTest {

    private ClipEncoderServiceImpl clipEncoderServiceImpl = new ClipEncoderServiceImpl();

    @Test
    public void can_decode() {
        assertThat(clipEncoderServiceImpl.decode("Q"), equalTo(42));
        assertThat(clipEncoderServiceImpl.decode("vJ"), equalTo(1337));
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_decode_null_string__should_throw_iae() {
        clipEncoderServiceImpl.decode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_decode_empty_string__should_throw_iae() {
        clipEncoderServiceImpl.decode("");
    }

    @Test
    public void can_encode() {
        assertThat(clipEncoderServiceImpl.encode(42), equalTo("Q"));
        assertThat(clipEncoderServiceImpl.encode(1337), equalTo("vJ"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_encode_zero_value__should_throw_iae() {
        clipEncoderServiceImpl.encode(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_encode_negative_value__should_throw_iae() {
        clipEncoderServiceImpl.encode(-12345);
    }
}
