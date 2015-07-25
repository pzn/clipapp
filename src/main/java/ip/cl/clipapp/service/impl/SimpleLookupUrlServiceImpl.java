package ip.cl.clipapp.service.impl;

import ip.cl.clipapp.service.ClipEncoderService;
import ip.cl.clipapp.service.LookupUrlService;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleLookupUrlServiceImpl implements LookupUrlService {

    private AtomicInteger        counter = new AtomicInteger(1);
    private Map<Integer, String> urlMap  = new ConcurrentHashMap<>();

    @Autowired
    private ClipEncoderService   clipEncoderService;

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
        for (Entry<Integer, String> entry : urlMap.entrySet()) {
            if (entry.getValue().equals(longUrl)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private String addLongUrl(String longUrl) {
        Integer key = counter.getAndIncrement();
        urlMap.put(key, longUrl);
        return clipEncoderService.encode(key);
    }

    @Override
    public String getLongUrl(String tinyUrl) {
        int key = clipEncoderService.decode(tinyUrl);
        String longUrl = urlMap.get(key);
        return longUrl;
    }

}
