package com.friend_map.controllers.views.auth;

import com.friend_map.business_layer.auth.AuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    @Autowired
    AuthUserDetails authUserDetails;

    @RequestMapping(value = "auth", method = RequestMethod.GET)
    public ModelAndView loginGet(ModelAndView mv) {
        if (!authUserDetails.getAuthUserName().equals("anonymousUser")) {
            return new ModelAndView("redirect:/");
        }
        mv.setViewName("auth/login");
        return mv;
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public ModelAndView register(ModelAndView mv) {
        if (!authUserDetails.getAuthUserName().equals("anonymousUser")) {
            return new ModelAndView("redirect:/");
        }
        mv.setViewName("auth/register");
        return mv;
    }
}
