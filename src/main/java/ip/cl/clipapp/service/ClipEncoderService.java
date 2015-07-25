package ip.cl.clipapp.service;

import org.springframework.stereotype.Service;

@Service
public class ClipEncoderService {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int    BASE     = ALPHABET.length();

    public String encode(int v) {
        StringBuilder sb = new StringBuilder();
        while (v > 0) {
            sb.append(ALPHABET.charAt(v % BASE));
            v /= BASE;
        }
        return sb.reverse().toString();
    }

    public int decode(String str) {
        int v = 0;
        char[] chars = str.toCharArray();

        for (char c : chars) {
            v = (v * BASE) + ALPHABET.indexOf(c);
        }
        return v;
    }

}
