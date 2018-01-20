package ip.cl.clipapp.service;

import ip.cl.clipapp.TinyUrlNotFoundException;

public interface ExtenderService {

    String extend(String tinyUrl) throws TinyUrlNotFoundException;
}
