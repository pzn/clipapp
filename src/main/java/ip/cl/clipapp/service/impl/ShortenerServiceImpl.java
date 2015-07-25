package ip.cl.clipapp.service.impl;

import ip.cl.clipapp.ClipAppRuntimeException;
import ip.cl.clipapp.service.ShortenerService;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ShortenerServiceImpl implements ShortenerService {

    private UrlValidator       urlValidator = new UrlValidator(new String[] { "http", "https" });

    @Autowired
    private ClipEncoderServiceImpl clipEncoderService;
    @Autowired
    private SimpleLookupUrlServiceImpl   lookupUrlService;

    @Override
    public String shorten(String longUrl) {
        Assert.hasText(longUrl);
        if (!urlValidator.isValid(longUrl)) {
            throw new ClipAppRuntimeException("url is invalid");
        }

        String tinyUrl = lookupUrlService.getOrAddLongUrl(longUrl);

        return tinyUrl;
    }
}
