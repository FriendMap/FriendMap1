package com.friend_map.persistence_layer.services.user;


import com.friend_map.persistence_layer.dao.user.UserDao;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService extends BaseService {

    public static final int max = 30;
    public static final int first = 0;
    public static Date date = new Date();

    @Autowired
    private UserDao userDao;

    public User findById(UUID id) {
        return userDao.findById(id);
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User findByUsernameMin(String username) {
        return userDao.findByUsername(username);
    }

    public List<User> likeByNickname(String nickname, int first) {
        return userDao.likeByNickname(nickname, max, first);
    }

    public List<User> likeByNickname(String nickname) {
        return userDao.likeByNickname(nickname, max, first);
    }

}