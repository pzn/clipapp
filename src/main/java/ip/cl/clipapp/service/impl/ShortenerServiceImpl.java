package ip.cl.clipapp.service.impl;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ip.cl.clipapp.service.LookupUrlService;
import ip.cl.clipapp.service.ShortenerService;

import static org.springframework.util.Assert.hasText;

@Service
public class ShortenerServiceImpl implements ShortenerService {

    private final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
    @Autowired
    private LookupUrlService lookupUrlService;

    @Override
    public String shorten(String longUrl) {

        hasText(longUrl, "long url cannot be null or empty!");
        if (!urlValidator.isValid(longUrl)) {
            throw new IllegalArgumentException("url is invalid");
        }

        return lookupUrlService.getOrAddLongUrl(longUrl);
    }
}
