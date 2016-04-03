package com.friend_map.controllers.test;

import com.friend_map.business_layer.friend.GetFriendByStatus;
import com.friend_map.business_layer.handler.UserKeyService;
import com.friend_map.components.CommandStatusResult;
import com.friend_map.components.beans.UserDetailsServiceImpl;
import com.friend_map.components.enums.CommandStatus;
import com.friend_map.persistence_layer.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetCoordinateController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    GetFriendByStatus getFriendByStatus;

    @RequestMapping(value = "key/create")
    public CommandStatusResult getKey() {
        try {
            String key = UUID.randomUUID().toString();
            User user = userDetailsService.getCurrentUser();
            UserKeyService.userMap.put(key, user);
            UserKeyService.friends.put(key, getFriendByStatus.getUserFriendsId(user));
            System.out.println(UserKeyService.friends.size());
            return new CommandStatusResult(key);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommandStatusResult(CommandStatus.ERROR);
        }
    }
}
