package com.friend_map.components.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Map;

@Component
public class JsonConverter {

    public String postLikeToJson(Map<String, String> map) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(map);
    }

    public Map<String, String> toPostLikeList(String text) {
        Type itemsListType = new TypeToken<Map<String, String>>() {}.getType();
        return new Gson().fromJson(text, itemsListType);
    }


}
