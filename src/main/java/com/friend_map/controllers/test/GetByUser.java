package com.friend_map.controllers.test;

import com.friend_map.components.converter.JsonConverter;
import com.friend_map.persistence_layer.pojo.coordinate.Coordinate;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetByUser {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    JsonConverter jsonConverter;
    @Autowired
    Gson gson;
    private static final String coordinate_key = "coordinate_key";

    @RequestMapping(value = "coordinate/{user}", method = RequestMethod.GET)
    public Coordinate getUsers(@PathVariable(value = "user") String user) {
        return findByUser(user);
    }

    @SuppressWarnings("unchecked")
    public Coordinate findByUser(String userId) {
        try {
            String coordinateJson = redisTemplate.opsForHash().get(coordinate_key, userId).toString();
            return gson.fromJson(coordinateJson, Coordinate.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Coordinate();
    }

    /** УДАЛЯЕТ КООРДИНАТУ */
    @Async
    @SuppressWarnings("unchecked")
    public void delete(String id) throws Exception {
        redisTemplate.opsForHash().delete(coordinate_key, id);
    }

}
