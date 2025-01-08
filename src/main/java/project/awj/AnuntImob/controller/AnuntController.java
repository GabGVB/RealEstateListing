package project.awj.AnuntImob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.awj.AnuntImob.service.AnuntService;

@Controller
public class AnuntController {
    @Autowired
    private AnuntService anuntService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("anunturi", anuntService.getAllAnunturi());
        return "index";
    }
}
