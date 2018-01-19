package ip.cl.clipapp.service.impl;

import ip.cl.clipapp.service.ClipEncoderService;

import org.springframework.stereotype.Service;

@Service
public class ClipEncoderServiceImpl implements ClipEncoderService {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALPHABET.length();

    @Override
    public String encode(int v) {

        StringBuilder sb = new StringBuilder();
        while (v > 0) {
            sb.append(ALPHABET.charAt(v % BASE));
            v /= BASE;
        }
        return sb.reverse().toString();
    }

    @Override
    public int decode(String str) {

        int v = 0;
        char[] chars = str.toCharArray();

        for (char c : chars) {
            v = (v * BASE) + ALPHABET.indexOf(c);
        }
        return v;
    }
}
