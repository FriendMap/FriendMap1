package com.friend_map.controllers.api.user;

import com.friend_map.business_layer.friend.FriendStatusService;
import com.friend_map.business_layer.friend.GetFriendByStatus;
import com.friend_map.components.beans.UserDetailsServiceImpl;
import com.friend_map.components.enums.FriendStatus;
import com.friend_map.components.enums.Roles;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.user.UserService;
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
public class UserGetController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserService userService;
    @Autowired
    FriendStatusService friendStatusService;
    @Autowired
    GetFriendByStatus getFriendByStatus;

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    @Secured({Roles.ROLE_ADMIN, Roles.ROLE_SUPER_ADMIN, Roles.ROLE_USER})
    public User profile() {
        User user = userDetailsService.getCurrentUser();
        user.setFriends(getFriendByStatus.getUsersByStatus(user, FriendStatus.FRIEND));
        return user;
    }

    @RequestMapping(value = "user/id/{id}", method = RequestMethod.GET)
    @Secured({Roles.ROLE_ADMIN, Roles.ROLE_SUPER_ADMIN, Roles.ROLE_USER})
    public User findById(@PathVariable(value = "id")UUID id) {
        User current_user = userDetailsService.getCurrentUser();
        User another_user = userService.findById(User.class, id);
        FriendStatus friendStatus = friendStatusService.friendStatus(current_user, another_user);
        another_user.setFriedStatus(friendStatus.toString());
        return another_user;
    }

    @RequestMapping(value = "user/{nickname}", method = RequestMethod.GET)
    public List<User> findById(@PathVariable(value = "nickname")String nickname) {
        return userService.likeByNickname(nickname);
    }

    @RequestMapping(value = "user/{nickname}/{number}", method = RequestMethod.GET)
    public List<User> findById(@PathVariable(value = "nickname")String nickname,
                               @PathVariable(value = "number") int number) {
        return userService.likeByNickname(nickname, number);
    }

}
