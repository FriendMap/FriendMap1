package com.friend_map.business_layer.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;


@Service
public class AuthUserDetails {

    String currentUserName;
    String currentUserPassword;
    Object currentUserRole;

    public ModelAndView redirect(String viewName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(viewName);
        if (getAuthUserName().equals("anonymousUser")) {
            return new ModelAndView("redirect:/auth");
        }
        return modelAndView;
    }

    public String getAuthUserName() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                currentUserName = ((UserDetails)principal).getUsername();
            } else {
                currentUserName = principal.toString();
            }
            return currentUserName;
        } catch (NullPointerException e) {
            currentUserName = e.toString();
            return currentUserName;
        }
        catch (Exception e) {
            currentUserName = e.toString();
            return currentUserName;
        }
    }

    public String getAuthUserPassword() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                currentUserPassword = ((UserDetails)principal).getPassword();
            } else {
                currentUserPassword = principal.toString();
            }
            return currentUserPassword;
        } catch (NullPointerException e) {
            currentUserPassword = e.toString();
            return currentUserPassword;
        }
        catch (Exception e) {
            currentUserPassword = e.toString();
            return currentUserPassword;
        }
    }

    public Object getAuthUserRole() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication();
            if (principal instanceof UserDetails) {
                currentUserRole = ((UserDetails)principal).getAuthorities();
            } else {
                currentUserRole = principal;
            }
            return currentUserRole;
        } catch (NullPointerException e) {
            currentUserRole = e.toString();
            return currentUserRole;
        }
        catch (Exception e) {
            currentUserRole = e.toString();
            return currentUserRole;
        }
    }
}
