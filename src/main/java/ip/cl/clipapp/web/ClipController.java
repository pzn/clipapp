package ip.cl.clipapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ip.cl.clipapp.TinyUrlNotFoundException;
import ip.cl.clipapp.model.web.UnclippedResponse;
import ip.cl.clipapp.model.web.ClippedResponse;
import ip.cl.clipapp.service.ExtenderService;
import ip.cl.clipapp.service.ShortenerService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
public class ClipController {

    private static final String TINY_URL_PARAM = "tinyUrl";

    @Autowired
    private ShortenerService shortenerService;
    @Autowired
    private ExtenderService extenderService;

    @GetMapping(value = "/")
    public String index(ModelMap modelMap) {
        modelMap.putIfAbsent(TINY_URL_PARAM, "");
        return "index";
    }

    @PostMapping(params = "u", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ClippedResponse clip(@RequestParam(value = "u") String longUrl) {
        return new ClippedResponse(shortenerService.shorten(longUrl));
    }

    @PostMapping(value = "{tinyUrl}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody UnclippedResponse unclip(@PathVariable("tinyUrl") String tinyUrl) throws TinyUrlNotFoundException {
        return new UnclippedResponse(extenderService.extend(tinyUrl));
    }

    @GetMapping(value = "{tinyUrl}")
    public ModelAndView redirect(@PathVariable("tinyUrl") String tinyUrl, RedirectAttributes redirectAttrs) throws TinyUrlNotFoundException {

        try {
            String longUrl = extenderService.extend(tinyUrl);
            return new ModelAndView("redirect:" + longUrl);

        } catch (TinyUrlNotFoundException e) {
            redirectAttrs.addFlashAttribute(TINY_URL_PARAM, tinyUrl);
            return new ModelAndView("redirect:/");
        }
    }
}
