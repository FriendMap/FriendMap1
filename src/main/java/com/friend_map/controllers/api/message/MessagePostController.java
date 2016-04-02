package com.friend_map.controllers.api.message;

import com.friend_map.business_layer.message.UserMessageDelete;
import com.friend_map.business_layer.message.UserMessageSend;
import com.friend_map.components.CommandStatusResult;
import com.friend_map.components.enums.CommandStatus;
import com.friend_map.components.enums.Roles;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Secured({Roles.ROLE_ADMIN, Roles.ROLE_SUPER_ADMIN, Roles.ROLE_USER})
public class MessagePostController {

    @Autowired
    UserService userService;
    @Autowired
    UserMessageSend userMessageSend;
    @Autowired
    UserMessageDelete userMessageDelete;

    @RequestMapping(value = "message/send", method = RequestMethod.POST)
    public CommandStatusResult send(@RequestParam(value = "id") UUID id,
                                    @RequestParam(value = "text") String text) {
        User user = userService.findById(id);
        try {
            return new CommandStatusResult(userMessageSend.sendMessage(user, text));
        } catch (Exception e) {
            return new CommandStatusResult(CommandStatus.ERROR);
        }
    }

    @RequestMapping(value = "message/delete", method = RequestMethod.POST)
    public CommandStatusResult deleteMessage(@RequestParam(value = "id") UUID id) {
        return new CommandStatusResult(userMessageDelete.deleteMessage(id));
    }

    @RequestMapping(value = "dialog/delete", method = RequestMethod.POST)
    public CommandStatusResult deleteDialog(@RequestParam(value = "id") UUID id) {
        return new CommandStatusResult(userMessageDelete.deleteDialog(id));
    }
}
