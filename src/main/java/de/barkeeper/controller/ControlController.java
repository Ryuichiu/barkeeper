package de.barkeeper.controller;

import de.barkeeper.model.Control;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/control")
public class ControlController {

    private static final Logger LOG = LoggerFactory.getLogger(ControlController.class);

    private Control control = new Control();


    @GetMapping("/view")
    public ModelAndView controlView() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @PostMapping(value = "/setMove", produces = MediaType.APPLICATION_JSON_VALUE)
    public void getMoveFromGUI(@RequestParam("move") int move) {
        LOG.info("MOVE {}", move);
        control.setMove(move);
    }

    @GetMapping(value = "/getMove", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public int sendMoveToRaspberry() {
        int test = 2;
        return test;
    }
}
