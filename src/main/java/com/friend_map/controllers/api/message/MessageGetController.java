package com.friend_map.controllers.api.message;

import com.friend_map.business_layer.message.UserMessageGet;
import com.friend_map.components.enums.Roles;
import com.friend_map.persistence_layer.entities.messages.Dialog;
import com.friend_map.persistence_layer.entities.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Secured({Roles.ROLE_ADMIN, Roles.ROLE_SUPER_ADMIN, Roles.ROLE_USER})
public class MessageGetController {

    @Autowired
    UserMessageGet userMessageGet;

    @RequestMapping(value = "message/{id}", method = RequestMethod.GET)
    public List<UserMessage> inbox(@PathVariable(value = "id") UUID id) {
        return userMessageGet.getUserMessages(id);
    }

    @RequestMapping(value = "message/{id}/{first}", method = RequestMethod.GET)
    public List<UserMessage> inbox(@PathVariable(value = "id") UUID id,
                                   @PathVariable(value = "first") int first) {
        return userMessageGet.getUserMessages(id, first);
    }

    @RequestMapping(value = "dialog/all", method = RequestMethod.GET)
    public List<Dialog> allDialog() {
        return userMessageGet.getUserDialogs();
    }

}
