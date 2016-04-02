package com.friend_map.controllers.api;

import com.friend_map.business_layer.auth.AuthUserDetails;
import com.friend_map.components.CommandStatusResult;
import com.friend_map.components.enums.CommandStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestStatus {

    @Autowired
    AuthUserDetails authUserDetails;

    @RequestMapping(value = "status")
    public CommandStatusResult status() {
        if (!authUserDetails.getAuthUserName().equals("anonymousUser")) {
            return new CommandStatusResult(CommandStatus.OK);
        }
        return new CommandStatusResult(CommandStatus.ANONYM);
    }
}
