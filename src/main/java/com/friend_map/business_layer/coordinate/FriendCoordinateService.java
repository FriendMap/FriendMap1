package com.friend_map.business_layer.coordinate;

import com.friend_map.business_layer.friend.GetFriendByStatus;
import com.friend_map.business_layer.handler.Coordinates;
import com.friend_map.business_layer.handler.UserKeyService;
import com.friend_map.components.converter.JsonConverter;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.pojo.coordinate.Coordinate;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendCoordinateService {

    @Autowired
    GetFriendByStatus getFriendByStatus;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    JsonConverter jsonConverter;
    @Autowired
    Gson gson;
    private static final String coordinate_key = "coordinate_key";

    public List<Coordinate> getFriendsCoordinates(User user) {
        return findByUsers(getFriendByStatus.getUserFriendsId(user));
    }

    public List<Coordinate> getFriendsCoordinates(String key) {
        return findByUsers(UserKeyService.friends.get(key));
    }

    @SuppressWarnings("unchecked")
    public List<Coordinate> findByUsers(List<String> strings) {
        try {
            //String coordinateJson = redisTemplate.opsForHash().multiGet(coordinate_key, strings).toString();
            List<Coordinate> coordinates = new ArrayList<>();
            for (String s : strings) {
                coordinates.add(Coordinates.map.get(s));
            }
            //return jsonConverter.jsonToListCoordinate(coordinateJson);
            return coordinates;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
