package ip.cl.clipapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ip.cl.clipapp.TinyUrlNotFoundException;
import ip.cl.clipapp.service.ExtenderService;
import ip.cl.clipapp.service.LookupUrlService;

import static org.springframework.util.Assert.hasText;

@Service
public class ExtenderServiceImpl implements ExtenderService {

    @Autowired
    private LookupUrlService lookupUrlService;

    @Override
    public String extend(String tinyUrl) throws TinyUrlNotFoundException {

        hasText(tinyUrl, "tiny url cannot be null or empty!");

        String longUrl = lookupUrlService.getLongUrl(tinyUrl);
        if (!StringUtils.hasText(longUrl)) {
            throw new TinyUrlNotFoundException();
        }
        return longUrl;
    }
}
