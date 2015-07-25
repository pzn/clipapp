package ip.cl.clipapp.service;

import ip.cl.clipapp.ClipAppRuntimeException;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ShortenerService {

    private UrlValidator       urlValidator = new UrlValidator(new String[] { "http", "https" });

    @Autowired
    private ClipEncoderService clipEncoderService;
    @Autowired
    private LookupUrlService   lookupUrlService;

    public String shorten(String longUrl) {
        Assert.hasText(longUrl);
        if (!urlValidator.isValid(longUrl)) {
            throw new ClipAppRuntimeException("url is invalid");
        }

        String tinyUrl = lookupUrlService.getOrAddLongUrl(longUrl);

        return tinyUrl;
    }
}
