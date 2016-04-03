package com.friend_map.business_layer.friend;

import com.friend_map.components.beans.UserDetailsServiceImpl;
import com.friend_map.components.enums.FriendStatus;
import com.friend_map.persistence_layer.entities.friend.Friend;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.friend.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class GetFriendByStatus {

    Logger logger = Logger.getLogger(GetFriendByStatus.class.getName());

    @Autowired
    FriendService friendService;
    @Autowired
    FriendStatusService friendStatusService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public List<String> getUserFriendsId(User user) {
        List<User> users = getUsersByStatus(user, FriendStatus.FRIEND);
        List<String> strings = new ArrayList<>();
        for (User friend : users) {
            strings.add(friend.getId().toString());
        }
        return strings;
    }

    public List<User> getUsersByStatus(FriendStatus friendStatus) {
        User user = userDetailsService.getCurrentUser();
        return getUsersByStatus(user, friendStatus);
    }

    /** ПОЛУЧИТЬ ПОЛЬЗОВАТЕЛЕЙ ПО СТАТУСУ ОТНОШЕНИЯ PS: НАПРИМЕР ДРУЗЕЙ, ВЕРНЕТ ТОЛЬКО ДРУЗЕЙ */
    public List<User> getUsersByStatus(User user, FriendStatus friendStatus) {
        List<User> userList = new ArrayList<>();
        List<Friend> friendList = friendStatusService.getByUserAndStatus(user, friendStatus.toString());
        for (Friend friend : friendList) {
            User firstUser = friend.getFirst_user();
            User secondUser = friend.getSecond_user();
            FriendStatus currentUserStatus = friendStatusService.friendStatus(user,firstUser);
            if (currentUserStatus == FriendStatus.CURRENT_USER) {
                userList.add(secondUser);
            } else {
                userList.add(firstUser);
            }
        }
        return userList;
    }
}
