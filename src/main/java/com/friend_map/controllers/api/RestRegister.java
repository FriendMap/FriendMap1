package com.friend_map.controllers.api;

import com.friend_map.business_layer.auth.AuthUserDetails;
import com.friend_map.business_layer.auth.UserRegisterService;
import com.friend_map.components.CommandStatusResult;
import com.friend_map.components.enums.CommandStatus;
import com.friend_map.components.enums.Roles;
import com.friend_map.persistence_layer.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "register")
public class RestRegister {

    @Autowired
    UserRegisterService registerService;
    @Autowired
    AuthUserDetails userDetails;
    CommandStatus commandStatus;

    @RequestMapping(method = RequestMethod.POST)
    @Secured({Roles.ROLE_ANONYMOUS})
    public CommandStatusResult register(@RequestParam(User.USERNAME) String username,
                                        @RequestParam(User.PASSWORD) String password) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(Roles.ROLE_USER);
            commandStatus = registerService.register(user);
        }
        catch (Exception e) {
            commandStatus = CommandStatus.ERROR;
        }
        return new CommandStatusResult(commandStatus.toString());
    }
}
