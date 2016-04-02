package com.friend_map.controllers.views;

import com.friend_map.business_layer.auth.AuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Welcome {

    @Autowired
    AuthUserDetails authUserDetails;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView welcome() {
        ModelAndView modelAndView = authUserDetails.redirect("index");
        modelAndView.addObject("main", "active");
        if (authUserDetails.getAuthUserName().equals("anonymousUser")) {
            return new ModelAndView("redirect:/auth");
        }
        return modelAndView;
    }
}
