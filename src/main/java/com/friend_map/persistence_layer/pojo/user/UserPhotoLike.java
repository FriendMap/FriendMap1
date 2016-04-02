package com.friend_map.persistence_layer.pojo.user;

public class UserPhotoLike {

    String user;
    String vote;

    public UserPhotoLike(String user, String vote) {
        this.user = user;
        this.vote = vote;
    }

    public UserPhotoLike() {

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}
