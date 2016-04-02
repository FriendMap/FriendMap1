package com.friend_map.controllers.api.friend;

import com.friend_map.business_layer.friend.FriendStatusService;
import com.friend_map.business_layer.friend.GetFriendByStatus;
import com.friend_map.components.CommandStatusResult;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "friend")
@Secured({Roles.ROLE_ADMIN, Roles.ROLE_SUPER_ADMIN, Roles.ROLE_USER})
public class FriendGetController {

    @Autowired
    GetFriendByStatus getFriendByStatus;
    @Autowired
    FriendStatusService friendStatusService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserService userService;

    /** список пользователей подавших заявку на дружбу */
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public List<User> newUsers() {
        List<User> users = new ArrayList<>();
        try {
            return getFriendByStatus.getUsersByStatus(FriendStatus.NEW);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /** список пользователей которым подали заявку на дружбу */
    @RequestMapping(value = "requests", method = RequestMethod.GET)
    public List<User> requestUsers() {
        List<User> users = new ArrayList<>();
        try {
            return getFriendByStatus.getUsersByStatus(FriendStatus.REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /** список пользователей с которыми в состоянии дружбы */
    @RequestMapping(value = "friends", method = RequestMethod.GET)
    public List<User> getFriends() {
        List<User> users = new ArrayList<>();
        try {
            return getFriendByStatus.getUsersByStatus(FriendStatus.FRIEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /** список подписанных пользователей */
    @RequestMapping(value = "subscribers", method = RequestMethod.GET)
    public List<User> getSubscribers() {
        List<User> users = new ArrayList<>();
        try {
            return getFriendByStatus.getUsersByStatus(FriendStatus.SUBSCRIBER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /** список пользователей на которых отправил запрос на дружбу */
    @RequestMapping(value = "follow", method = RequestMethod.GET)
    public List<User> getRequestUsers() {
        List<User> users = new ArrayList<>();
        try {
            return getFriendByStatus.getUsersByStatus(FriendStatus.FOLLOW);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /** узнать состояние дружбы с определенным пользователем
     * FRIEND - друзья
     * REQUEST - отправил заявку
     * NEW - получил заявку
     * FOLLOW - подписан
     * SUBSCRIBER - подписчик
     * CURRENT_USER - текущий пользователь
     * NULL - не состоит в дружеских отношениях */
    @RequestMapping(value = "status/{id}", method = RequestMethod.GET)
    public CommandStatusResult getIgnoreUsers(@PathVariable(value = "id") UUID id) {
        User currentUser = userDetailsService.getCurrentUserMin();
        User anotherUser = userService.findById(id);
        return new CommandStatusResult(friendStatusService.friendStatus(currentUser, anotherUser));
    }
}
