package com.friend_map.controllers.api.friend;

import com.friend_map.business_layer.friend.FriendAdd;
import com.friend_map.business_layer.friend.FriendDelete;
import com.friend_map.components.CommandStatusResult;
import com.friend_map.components.beans.UserDetailsServiceImpl;
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
@RequestMapping(value = "friend")
@Secured({Roles.ROLE_ADMIN, Roles.ROLE_SUPER_ADMIN, Roles.ROLE_USER})
public class FriendPostController {

    @Autowired
    FriendAdd friendAdd;
    @Autowired
    FriendDelete friendDelete;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserService userService;

    /**
     *  SUCCESS - добавлено
     *  FRIEND - друг
     *  REQUEST - запрос был отправлен
     *  CURRENT_USER - сам себя
     *  ERROR - ошибка */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public CommandStatusResult add(@RequestParam(value = "id") UUID id) {
        User user = userDetailsService.getCurrentUserMin();
        User secondUser = userService.findById(id);
        return new CommandStatusResult(friendAdd.addFriend(user, secondUser));
    }

    /**
     * SUCCESS - удален и добавлен в список подписчиков
     * SUBSCRIBER - раннее был удален и находится в списке подписчиков
     * ERROR - ошибка */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public CommandStatusResult delete(@RequestParam(value = "id") UUID id) {
        User user = userDetailsService.getCurrentUserMin();
        User secondUser = userService.findById(id);
        return new CommandStatusResult(friendDelete.deleteFriend(user, secondUser));
    }
}
