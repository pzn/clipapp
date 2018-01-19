package ip.cl.clipapp.service.impl;

import org.springframework.stereotype.Service;

import ip.cl.clipapp.service.ClipEncoderService;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.isTrue;

@Service
public class ClipEncoderServiceImpl implements ClipEncoderService {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALPHABET.length();

    @Override
    public String encode(int v) {

        isTrue(v > 0, "value cannot be zero or negative!");

        StringBuilder sb = new StringBuilder();
        while (v > 0) {
            sb.append(ALPHABET.charAt(v % BASE));
            v /= BASE;
        }
        return sb.reverse().toString();
    }

    @Override
    public int decode(String str) {

        hasText(str, "string cannot be null or empty!");

        int v = 0;
        char[] chars = str.toCharArray();

        for (char c : chars) {
            v = (v * BASE) + ALPHABET.indexOf(c);
        }
        return v;
    }
}
