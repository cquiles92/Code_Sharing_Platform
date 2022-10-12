package platform.controller.html;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import platform.service.CodeService;

@Controller
@RequestMapping("/code")
public class WebCodeController {
    @Autowired
    private CodeService service;

    @GetMapping(value = "/{uuid}")
    public ModelAndView returnCode(@PathVariable String uuid) {
        ModelAndView modelAndView = new ModelAndView("CodeBody.html");
        service.getSingleCodePage(modelAndView, uuid);
        return modelAndView;
    }

    @GetMapping(value = "/new")
    public ModelAndView returnWebPage() {
        return new ModelAndView("CodeForm.html");
    }

    @GetMapping(value = "/latest")
    public ModelAndView returnLatestCodeHTML() {
        ModelAndView modelAndView = new ModelAndView("CodeBody.html");
        service.getLatestCodePage(modelAndView);
        return modelAndView;
    }
}
