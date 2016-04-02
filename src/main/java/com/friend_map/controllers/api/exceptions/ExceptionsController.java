package com.friend_map.controllers.api.exceptions;

import com.friend_map.components.CommandStatusResult;
import com.friend_map.components.enums.CommandStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionsController {

    /** TODO: ДОБАВИТЬ ДРУГИЕ ИСКЛЮЧЕНИЯ
     * - АВТОРИЗАЦИЯ
     * - ИТД
     * */


    @RequestMapping(value = "403", method = RequestMethod.GET)
    public CommandStatusResult access_denied() {
        return new CommandStatusResult(CommandStatus.ACCESS_DENIED);
    }
}
