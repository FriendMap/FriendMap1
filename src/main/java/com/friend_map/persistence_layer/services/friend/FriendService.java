package com.friend_map.persistence_layer.services.friend;

import com.friend_map.persistence_layer.dao.friend.FriendDao;
import com.friend_map.persistence_layer.entities.friend.Friend;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService extends BaseService {

    @Autowired
    FriendDao friendDao;

    /** ПОИСК ОТНОШЕНИЙ ПОЛЬЗОВАТЕЛЯ ПО СТАТУСУ */
    public List<Friend> getByUserAndStatus(User user, String status) {
        return friendDao.getByUserAndStatus(user, status);
    }

    /** ПОИСК ОТНОШЕНИЙ ПОЛЬЗОВАТЕЛЯ PS МОЖЕТ ПРИГОДИТСЯ */
    public List<Friend> getByUser(User user) {
        return friendDao.getByUser(user);
    }

    /** ПОИСК ОТНОШЕНИЯ МЕЖДУ ПОЛЬЗОВАТЕЛЯМИ */
    public Friend getByUsers(User user, User anotherUser) {
        return friendDao.getByUsers(user, anotherUser);
    }

    public FriendService() {
    }
}
