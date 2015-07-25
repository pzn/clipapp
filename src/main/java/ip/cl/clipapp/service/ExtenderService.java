package ip.cl.clipapp.service;

import ip.cl.clipapp.ClipAppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class ExtenderService {

    @Autowired
    private ClipEncoderService clipEncoderService;
    @Autowired
    private LookupUrlService   lookupUrlService;

    public String extend(String tinyUrl) throws ClipAppException {
        Assert.hasText(tinyUrl);

        String longUrl = lookupUrlService.getLongUrl(tinyUrl);

        if (!StringUtils.hasText(longUrl)) {
            throw new ClipAppException("url not found");
        }

        return longUrl;
    }

}
