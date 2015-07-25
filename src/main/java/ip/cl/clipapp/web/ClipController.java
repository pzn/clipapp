package ip.cl.clipapp.web;

import ip.cl.clipapp.ClipAppException;
import ip.cl.clipapp.service.impl.ExtenderServiceImpl;
import ip.cl.clipapp.service.impl.ShortenerServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClipController {

    @Autowired
    private ShortenerServiceImpl shortenerService;
    @Autowired
    private ExtenderServiceImpl  extenderService;

    @RequestMapping(params = "u", method = RequestMethod.GET)
    public String clip(@RequestParam(value = "u") String longUrl) {
        return shortenerService.shorten(longUrl);
    }

    @RequestMapping(value = "{tinyUrl}", method = RequestMethod.GET)
    public String unclip(@PathVariable("tinyUrl") String tinyUrl) throws ClipAppException {
        return extenderService.extend(tinyUrl);
    }

}
