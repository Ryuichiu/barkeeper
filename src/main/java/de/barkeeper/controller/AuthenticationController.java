package de.barkeeper.controller;

import de.barkeeper.model.User;
import de.barkeeper.model.VerificationToken;
import de.barkeeper.service.UserService;
import de.barkeeper.service.VerificationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Calendar;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/authentication")
public class AuthenticationController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    private UserService userService;
    private VerificationTokenService verificationTokenService;

    @Autowired
    public AuthenticationController(UserService userService, VerificationTokenService verificationTokenService) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
    }


    @PostMapping("/register")
    public ModelAndView registerNewUser(@Valid User user) throws MessagingException {
        ModelAndView mav = new ModelAndView();
        User userExists = userService.findByEmailOrUsername(user);

        if (userExists != null) {
            mav.setViewName("");
        } else {
            userService.registerNewUser(user);
            mav.setViewName("");
        }

        return mav;
    }

    @GetMapping(value = "/registration/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView confirmRegistration(@PathVariable("token") String token) {
        ModelAndView mav = new ModelAndView();

        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        User foundUser = verificationToken.getUser();

        if (verificationToken == null) {
            mav.setViewName("");
        }

        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0)) {
            mav.setViewName("");
            LOG.info("VerificationToken with id {} has been expired.", verificationToken.getId());
        }

        userService.confirmRegistration(foundUser);

        mav.setViewName("");
        return mav;
    }
}
