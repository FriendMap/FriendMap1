package com.friend_map.persistence_layer.pojo.coordinate;


import com.friend_map.persistence_layer.entities.user.User;

public class Coordinate {

    String latitude;
    String longitude;
    User user;

    public Coordinate(String x, String y, User user) {
        this.latitude = x;
        this.longitude = y;
        this.user = user;
    }

    public Coordinate() {
    }

    public String getX() {
        return latitude;
    }

    public void setX(String x) {
        this.latitude = x;
    }

    public String getY() {
        return longitude;
    }

    public void setY(String y) {
        this.longitude = y;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
