package com.friend_map.components.beans;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JsonBuilder {

    @Bean
    public GsonBuilder gsonBuilder() {
        return new GsonBuilder();
    }

    @Bean
    @Autowired
    public Gson gson(GsonBuilder gsonBuilder) {
        return gsonBuilder.create();
    }
}
