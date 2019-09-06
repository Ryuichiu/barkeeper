package de.barkeeper.controller;

import de.barkeeper.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {

    private MailService mailService;

    @Autowired
    public HomeController(MailService mailService) {
        this.mailService = mailService;
    }


    @GetMapping({"/", "/home"})
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }
}
