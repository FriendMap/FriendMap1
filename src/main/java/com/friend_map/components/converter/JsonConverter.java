package com.friend_map.components.converter;

import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.pojo.coordinate.Coordinate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Component
public class JsonConverter {

    @Autowired
    Gson gson;

    public String mapToJson(Map<String, String> map) {
        return gson.toJson(map);
    }

    public Map<String, String> jsonToMap(String text) {
        Type itemsListType = new TypeToken<Map<String, String>>() {}.getType();
        return new Gson().fromJson(text, itemsListType);
    }

    public String listUserToJson(List<User> users) {
        return gson.toJson(users);
    }

    public List<User> jsonToListUser(String text) {
        Type itemsListType = new TypeToken<List<User>>() {}.getType();
        return new Gson().fromJson(text, itemsListType);
    }

    public String listCoordinateToJson(List<Coordinate> coordinates) {
        return gson.toJson(coordinates);
    }

    public List<Coordinate> jsonToListCoordinate(String text) {
        Type itemsListType = new TypeToken<List<Coordinate>>() {}.getType();
        return new Gson().fromJson(text, itemsListType);
    }
}
