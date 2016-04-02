package com.friend_map.business_layer.auth;

import com.friend_map.components.enums.CommandStatus;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserRegisterService {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoderService passwordEncoderService;
    CommandStatus commandStatus;
    User user = new User();

    /**
     * TODO: ВСЕ ПЕРЕПИСАТЬ
     * */

    public CommandStatus register(User user) {
        try {
            commandStatus = findByUserToDatabase(user);
            switch (commandStatus) {
                case OK:
                    commandStatus = insertToDataBase(user);
                    return commandStatus;
                case ERROR:
                    commandStatus = CommandStatus.ERROR;
                    return commandStatus;
                case NULL:
                    commandStatus = insertToDataBase(user);
                    return commandStatus;
                case USERNAME_EXIST:
                    commandStatus = CommandStatus.USERNAME_EXIST;
                    return commandStatus;
                default:
                    commandStatus = CommandStatus.ERROR;
                    return commandStatus;
            }
        } catch (Exception e) {
            return CommandStatus.ERROR;
        }
    }

    public CommandStatus insertToDataBase(User user) {
        try {
            this.user = user;
            this.user.setPassword(passwordEncoderService.encodingPassword(user.getPassword()));
            commandStatus = userService.persist(this.user);
            return commandStatus;
        } catch (Exception e) {
            return CommandStatus.ERROR;
        }
    }

    public CommandStatus findByUserToDatabase(User user) {
        try {
            this.user = userService.findByUsername(user.getUsername());
            if (this.user.getUsername() == null) {
                return CommandStatus.OK;
            } else {
                return CommandStatus.USERNAME_EXIST;
            }
        } catch (NullPointerException e) {
            return CommandStatus.NULL;
        } catch (Exception e) {
            return CommandStatus.ERROR;
        }
    }
}
