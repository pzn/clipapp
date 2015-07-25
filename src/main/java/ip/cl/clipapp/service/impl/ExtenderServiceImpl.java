package ip.cl.clipapp.service.impl;

import ip.cl.clipapp.ClipAppException;
import ip.cl.clipapp.service.ExtenderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class ExtenderServiceImpl implements ExtenderService {

    @Autowired
    private ClipEncoderServiceImpl clipEncoderService;
    @Autowired
    private SimpleLookupUrlServiceImpl   lookupUrlService;

    @Override
    public String extend(String tinyUrl) throws ClipAppException {
        Assert.hasText(tinyUrl);

        String longUrl = lookupUrlService.getLongUrl(tinyUrl);

        if (!StringUtils.hasText(longUrl)) {
            throw new ClipAppException("url not found");
        }

        return longUrl;
    }

}
