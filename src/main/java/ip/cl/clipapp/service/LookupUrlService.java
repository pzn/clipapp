package ip.cl.clipapp.service;

public interface LookupUrlService {

    String getOrAddLongUrl(String longUrl);

    String getLongUrl(String tinyUrl);

}
