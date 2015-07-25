package ip.cl.clipapp.repository;

import ip.cl.clipapp.ClipAppProfile;
import ip.cl.clipapp.model.ClipUrl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

@Profile(ClipAppProfile.DATABASE)
public interface ClipUrlRepository extends CrudRepository<ClipUrl, Integer> {

    ClipUrl findByLongUrl(String longUrl);

}
