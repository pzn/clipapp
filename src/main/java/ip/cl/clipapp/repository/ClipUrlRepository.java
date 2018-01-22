package ip.cl.clipapp.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

import ip.cl.clipapp.model.entity.ClipUrl;

import static ip.cl.clipapp.ClipAppProfile.DATABASE;

@Profile(DATABASE)
public interface ClipUrlRepository extends CrudRepository<ClipUrl, Integer> {

    @Cacheable("clipUrlByLongUrl")
    ClipUrl findByLongUrl(String longUrl);
    @Cacheable("clipUrlById")
    ClipUrl findOne(Integer id);
}
