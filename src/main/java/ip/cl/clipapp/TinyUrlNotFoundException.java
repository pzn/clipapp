package ip.cl.clipapp;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND, reason = "tiny url not found")
public class TinyUrlNotFoundException extends ClipAppException {

}
