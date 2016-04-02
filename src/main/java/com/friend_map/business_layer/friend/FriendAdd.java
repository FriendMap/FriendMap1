package com.friend_map.business_layer.friend;

import com.friend_map.components.enums.CommandStatus;
import com.friend_map.components.enums.FriendStatus;
import com.friend_map.persistence_layer.entities.friend.Friend;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.friend.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendAdd {

    @Autowired
    FriendService friendService;
    @Autowired
    FriendStatusService friendStatusService;

    public CommandStatus addFriend(User currentUser, User anotherUser) {
        if (currentUser.getId().equals(anotherUser.getId())) {
            return CommandStatus.CURRENT_USER;
        }
        if (currentUser.getId() == null || anotherUser.getId() == null) {
            return CommandStatus.NULL;
        }
        Friend friend = friendStatusService.getByUsers(currentUser, anotherUser);
        if (friend != null) {
            FriendStatus friendStatus = friendStatusService.friendStatus(friend, currentUser);
            switch (friendStatus) {
                case SUBSCRIBER:
                    return acceptFriend(friend);
                case NEW:
                    return acceptFriend(friend);
                case IGNORE:
                    return CommandStatus.IGNORE;
                case IGNORED:
                    return CommandStatus.IGNORED;
                case FRIEND:
                    return CommandStatus.FRIEND;
                case REQUEST:
                    return CommandStatus.REQUEST;
                case FOLLOW:
                    return CommandStatus.FOLLOW;
                case NULL:
                    return newFriend(currentUser, anotherUser);
            }
        } else {
            return newFriend(currentUser, anotherUser);
        }
        return CommandStatus.ERROR;
    }

    /** ЗАПИСЫВАЕТ В БАЗУ НОВОЕ ОТНОШЕНИЕ */
    public CommandStatus newFriend(User currentUser, User anotherUser) {
        Friend friend1 = new Friend();
        friend1.setFirst_user(currentUser);
        friend1.setSecond_user(anotherUser);
        friend1.setSecond_user_status(FriendStatus.NEW.toString());
        friend1.setFirst_user_status(FriendStatus.REQUEST.toString());
        return friendService.persist(friend1);
    }

    /** ОБНОВЛЯЕТ ОТНОШЕНИЕ КАК ДРУЗЬЯ */
    public CommandStatus acceptFriend(Friend friend) {
        friend.setSecond_user_status(FriendStatus.FRIEND.toString());
        friend.setFirst_user_status(FriendStatus.FRIEND.toString());
        return friendService.update(friend);
    }
}
