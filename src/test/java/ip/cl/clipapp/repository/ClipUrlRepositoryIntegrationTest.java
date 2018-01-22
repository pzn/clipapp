package ip.cl.clipapp.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cache.Cache;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import ip.cl.clipapp.config.TestCacheConfiguration;
import ip.cl.clipapp.model.entity.ClipUrl;

import static ip.cl.clipapp.ClipAppProfile.DATABASE;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@ActiveProfiles(DATABASE)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestCacheConfiguration.class)
@DataJpaTest
public class ClipUrlRepositoryIntegrationTest {

    private AtomicInteger id = new AtomicInteger(0);
    private static final String URL = "my-long-url";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ClipUrlRepository clipUrlRepository;
    @Autowired
    private Cache clipUrlByIdCache;
    @Autowired
    private Cache clipUrlByLongUrlCache;

    @Before
    public void before() throws Exception {
        jdbcTemplate.execute("DELETE FROM clipurl");
        clipUrlByIdCache.clear();
        clipUrlByLongUrlCache.clear();
        jdbcTemplate.execute(String.format("INSERT INTO clipurl(id, long_url) VALUES(%d, '%s')", id.incrementAndGet(), URL));
    }

    @Test
    public void can_use_clipUrlById_cache() throws Exception {

        // Execute & Verify (1)
        ClipUrl firstTime = clipUrlRepository.findOne(id.get());
        verifyIntegrityOf(firstTime);
        verifyClipUrlByIdCache(firstTime);

        // Execute & Verify (2)
        ClipUrl secondTime = clipUrlRepository.findOne(id.get());
        verifyIntegrityOf(firstTime);
        verifyClipUrlByIdCache(firstTime);
        assertThat(firstTime, is(secondTime));
    }

    @Test
    public void can_use_clipUrlByLongUrlCache_cache() throws Exception {

        // Execute & Verify (1)
        ClipUrl firstTime = clipUrlRepository.findByLongUrl(URL);
        verifyIntegrityOf(firstTime);
        verifyClipUrlByLongUrlCache(firstTime);

        // Execute & Verify (2)
        ClipUrl secondTime = clipUrlRepository.findByLongUrl(URL);
        verifyIntegrityOf(firstTime);
        verifyClipUrlByLongUrlCache(firstTime);
        assertThat(firstTime, is(secondTime));
    }

    private void verifyIntegrityOf(ClipUrl clipUrl) {
        assertThat(clipUrl, is(notNullValue()));
        assertThat(clipUrl.getId(), is(id.get()));
        assertThat(clipUrl.getLongUrl(), is(URL));
    }

    private void verifyClipUrlByIdCache(ClipUrl clipUrl) {
        verifyCache(clipUrlByIdCache, id.get(), clipUrl);
    }

    private void verifyClipUrlByLongUrlCache(ClipUrl clipUrl) {
        verifyCache(clipUrlByLongUrlCache, URL, clipUrl);
    }

    private void verifyCache(Cache cache, Object cacheKey, ClipUrl clipUrl) {
        assertThat(((ConcurrentMap)cache.getNativeCache()).size(), is(1));
        assertThat(cache.get(cacheKey).get(), is(instanceOf(ClipUrl.class)));
        ClipUrl clipUrlFromCache = (ClipUrl) cache.get(cacheKey).get();
        assertThat(clipUrlFromCache, is(clipUrl));
    }
}
