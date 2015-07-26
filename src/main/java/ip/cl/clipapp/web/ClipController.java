package ip.cl.clipapp.web;

import ip.cl.clipapp.ClipAppException;
import ip.cl.clipapp.service.ExtenderService;
import ip.cl.clipapp.service.ShortenerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClipController {

    private static final String TINY_URL_PARAM = "tinyUrl";

    @Autowired
    private ShortenerService shortenerService;
    @Autowired
    private ExtenderService  extenderService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        modelMap.putIfAbsent(TINY_URL_PARAM, "");
        return "index";
    }

    @RequestMapping(params = "u", method = RequestMethod.POST)
    public @ResponseBody String clip(@RequestParam(value = "u") String longUrl) {
        return shortenerService.shorten(longUrl);
    }

    @RequestMapping(value = "{tinyUrl}", method = RequestMethod.POST)
    public @ResponseBody String unclip(@PathVariable("tinyUrl") String tinyUrl) throws ClipAppException {
        return extenderService.extend(tinyUrl);
    }

    @RequestMapping(value = "{tinyUrl}", method = RequestMethod.GET)
    public ModelAndView redirect(@PathVariable("tinyUrl") String tinyUrl, RedirectAttributes redirectAttrs) throws ClipAppException {
        String longUrl = null;
        try {
            longUrl = extenderService.extend(tinyUrl);
        }
        catch (ClipAppException e) {
            redirectAttrs.addFlashAttribute(TINY_URL_PARAM, tinyUrl);
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:" + longUrl);
    }

}
