package com.friend_map.persistence_layer.entities.image;


import java.util.Date;
import java.util.UUID;

public interface UserImage {

    UUID getId();
    String getFull_directory();
    String getMiniature();
    String getFull_image();
    String getImage_category();
    Date getDate();

    void setId(UUID uuid);
    void setFull_directory(String string);
    void setMiniature(String string);
    void setFull_image(String string);
    void setImage_category(String string);
    void setDate(Date date);

}
