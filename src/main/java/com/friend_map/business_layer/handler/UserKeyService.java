package com.friend_map.business_layer.handler;

import com.friend_map.persistence_layer.entities.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserKeyService {

    public static ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, List<String>> friends = new ConcurrentHashMap<>();


}
