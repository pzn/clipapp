package ip.cl.clipapp.service.impl;

import ip.cl.clipapp.ClipAppException;
import ip.cl.clipapp.service.ClipEncoderService;
import ip.cl.clipapp.service.ExtenderService;
import ip.cl.clipapp.service.LookupUrlService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import static org.springframework.util.Assert.hasText;

@Service
public class ExtenderServiceImpl implements ExtenderService {

    @Autowired
    private ClipEncoderService clipEncoderService;
    @Autowired
    private LookupUrlService lookupUrlService;

    @Override
    public String extend(String tinyUrl) throws ClipAppException {

        hasText(tinyUrl);

        String longUrl = lookupUrlService.getLongUrl(tinyUrl);
        if (!StringUtils.hasText(longUrl)) {
            throw new ClipAppException("url not found");
        }

        return longUrl;
    }
}
