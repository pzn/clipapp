package ip.cl.clipapp.service;

import ip.cl.clipapp.ClipAppException;

public interface ExtenderService {

    String extend(String tinyUrl) throws ClipAppException;

}
