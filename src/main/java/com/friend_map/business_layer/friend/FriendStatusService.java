package com.friend_map.business_layer.friend;

import com.friend_map.components.enums.FriendStatus;
import com.friend_map.persistence_layer.entities.friend.Friend;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.friend.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendStatusService {

    @Autowired
    FriendService friendService;

    public boolean isFriend(User current_user, User another_user) {
        FriendStatus friendStatus = friendStatus(current_user, another_user);
        return friendStatus == FriendStatus.FRIEND;
    }

    /** ПОЛУЧИТЬ СТАТУС ОТНОШЕНИЯ С ПОЛЬЗОВАТЕЛЕМ */
    public FriendStatus friendStatus(Friend friend, User currentUser) {
        if (friend == null) {
            return FriendStatus.NULL;
        }
        User anotherUser = friend.getSecond_user();
        User thisUser = friend.getFirst_user();

        if (currentUser.getId().equals(anotherUser.getId())) {
            return FriendStatus.valueOf(friend.getSecond_user_status());
        }
        if (currentUser.getId().equals(thisUser.getId())) {
            return FriendStatus.valueOf(friend.getFirst_user_status());
        }
        return FriendStatus.NULL;
    }

    /** ПОЛУЧИТЬ СТАТУС МЕЖДУ ПОЛЬЗОВАТЕЛЯМИ */
    public FriendStatus friendStatus(User currentUser, User anotherUser) {
        Friend friend = getByUsers(currentUser, anotherUser);
        return friendStatus(friend, currentUser);
    }

    /** ВЫЗОВ ИЗ СЕРВИСА */
    public Friend getByUsers(User currentUser, User anotherUser) {
        return friendService.getByUsers(currentUser, anotherUser);
    }

    /** ВЫЗОВ ИЗ СЕРВИСА */
    public List<Friend> getByUserAndStatus(User currentUser, String status) {
        return friendService.getByUserAndStatus(currentUser, status);
    }
}
