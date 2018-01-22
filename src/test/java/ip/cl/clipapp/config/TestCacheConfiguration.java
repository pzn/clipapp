package ip.cl.clipapp.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@TestConfiguration
@EnableCaching
public class TestCacheConfiguration {

    @Bean
    public CacheManager cacheManager() {

        List<Cache> caches = new ArrayList<>(2);
        caches.add(clipUrlByLongUrlCache().getObject());
        caches.add(clipUrlByIdCache().getObject());

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    @Bean
    public ConcurrentMapCacheFactoryBean clipUrlByLongUrlCache() {
        ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName("clipUrlByLongUrl");
        return cacheFactoryBean;
    }

    @Bean
    public ConcurrentMapCacheFactoryBean clipUrlByIdCache() {
        ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName("clipUrlById");
        return cacheFactoryBean;
    }
}
