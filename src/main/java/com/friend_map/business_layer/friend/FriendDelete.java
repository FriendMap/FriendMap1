package com.friend_map.business_layer.friend;

import com.friend_map.components.enums.CommandStatus;
import com.friend_map.components.enums.FriendStatus;
import com.friend_map.persistence_layer.entities.friend.Friend;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.friend.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendDelete {

    @Autowired
    FriendService friendService;
    @Autowired
    FriendStatusService friendStatusService;

    public CommandStatus deleteFriend(User currentUser, User anotherUser) {
        if (currentUser.getId().equals(anotherUser.getId())) {
            return CommandStatus.CURRENT_USER;
        }

        Friend friend = friendStatusService.getByUsers(currentUser, anotherUser);
        if (friend != null) {
            FriendStatus friendStatus = friendStatusService.friendStatus(friend, currentUser);
            switch (friendStatus) {
                case SUBSCRIBER:
                    return CommandStatus.SUBSCRIBER;
                case NEW:
                    return delete(currentUser, friend);
                case IGNORE:
                    return CommandStatus.IGNORE;
                case IGNORED:
                    return CommandStatus.IGNORED;
                case FRIEND:
                    return delete(currentUser, friend);
                case REQUEST:
                    return cancelSubscribe(friend);
                case FOLLOW:
                    return cancelSubscribe(friend);
            }
        } else {
            return CommandStatus.EMPTY;
        }
        return CommandStatus.ERROR;
    }

    /** ОБНОВЛЯЕТ КАК ПОДПИСЧИК */
    public CommandStatus delete(User currentUser,Friend friend) {
        if (friend.getFirst_user().getId().equals(currentUser.getId())) {
            friend.setFirst_user_status(FriendStatus.SUBSCRIBER.toString());
            friend.setSecond_user_status(FriendStatus.FOLLOW.toString());
            return friendService.update(friend);
        }
        if (friend.getSecond_user().getId().equals(currentUser.getId())) {
            friend.setFirst_user_status(FriendStatus.FOLLOW.toString());
            friend.setSecond_user_status(FriendStatus.SUBSCRIBER.toString());
            return friendService.update(friend);
        }
        return cancelSubscribe(friend);
    }

    /** ОТМЕНИТЬ ПОДПИСКУ */
    public CommandStatus cancelSubscribe(Friend friend) {
        return friendService.delete(friend);
    }
}
