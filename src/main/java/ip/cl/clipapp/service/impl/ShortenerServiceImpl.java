package ip.cl.clipapp.service.impl;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import ip.cl.clipapp.ClipAppRuntimeException;
import ip.cl.clipapp.service.LookupUrlService;
import ip.cl.clipapp.service.ShortenerService;

@Service
public class ShortenerServiceImpl implements ShortenerService {

    private UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});

    @Autowired
    private LookupUrlService lookupUrlService;

    @Override
    public String shorten(String longUrl) {

        Assert.hasText(longUrl);
        if (!urlValidator.isValid(longUrl)) {
            throw new ClipAppRuntimeException("url is invalid");
        }

        return lookupUrlService.getOrAddLongUrl(longUrl);
    }
}
