package com.friend_map.business_layer.handler;

import com.friend_map.persistence_layer.pojo.coordinate.Coordinate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

public class Coordinates {

    public static ConcurrentHashMap<String, Coordinate> map = new ConcurrentHashMap<>();

}
