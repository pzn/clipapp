package ip.cl.clipapp.service.impl;

import ip.cl.clipapp.ClipAppProfile;
import ip.cl.clipapp.model.ClipUrl;
import ip.cl.clipapp.repository.ClipUrlRepository;
import ip.cl.clipapp.service.ClipEncoderService;
import ip.cl.clipapp.service.LookupUrlService;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static ip.cl.clipapp.ClipAppProfile.DATABASE;

@Profile(DATABASE)
@Service
@Transactional
public class DbLookupUrlServiceImpl implements LookupUrlService {

    @Autowired
    private ClipEncoderService clipEncoderService;
    @Autowired
    private ClipUrlRepository clipUrlRepository;

    @Override
    public String getOrAddLongUrl(String longUrl) {

        Integer key = findKey(longUrl);
        if (key == null) {
            String tinyUrl = addLongUrl(longUrl);
            return tinyUrl;
        }
        return clipEncoderService.encode(key);
    }

    private Integer findKey(String longUrl) {

        ClipUrl clipUrl = clipUrlRepository.findByLongUrl(longUrl);
        if (clipUrl == null) {
            return null;
        }
        return clipUrl.getId();
    }

    private String addLongUrl(String longUrl) {

        ClipUrl clipUrl = new ClipUrl();
        clipUrl.setLongUrl(longUrl);
        clipUrlRepository.save(clipUrl);
        return clipEncoderService.encode(clipUrl.getId());
    }

    @Override
    public String getLongUrl(String tinyUrl) {

        int key = clipEncoderService.decode(tinyUrl);
        ClipUrl clipUrl = clipUrlRepository.findOne(key);
        if (clipUrl == null) {
            return null;
        }
        return clipUrl.getLongUrl();
    }
}
