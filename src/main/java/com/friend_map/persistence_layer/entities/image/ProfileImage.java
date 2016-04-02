package com.friend_map.persistence_layer.entities.image;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.friend_map.persistence_layer.entities.user.User;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "user_profile_image_table")
public class ProfileImage implements UserImage {

    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String MINIATURE = "miniature";
    public static final String FULL_IMAGE = "full_image";
    public static final String IMAGE_DIRECTORY = "image_directory";
    public static final String IMAGE_CATEGORY = "image_category";
    public static final String USER = "user";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID, columnDefinition = "BINARY(16)", unique = true)
    private UUID id;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm")
    @Column(name = DATE)
    private Date date;

    /** МИНИАТЮРА */
    @Column(name = MINIATURE)
    private String miniature;

    /** ПОЛНОЕ ИЗОБРАЖЕНИЕ */
    @Column(name = FULL_IMAGE)
    private String full_image;

    /** ПУТЬ К ФАЙЛУ */
    @Column(name = IMAGE_DIRECTORY)
    private String full_directory;

    /** КАТЕГОРИЯ ИЗОБРАЖЕНИИ */
    @Column(name = IMAGE_CATEGORY)
    private String image_category;

    /** ПОЛЬЗОВАТЕЛЬ */
    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = USER)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private User user;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMiniature() {
        return miniature;
    }

    public void setMiniature(String miniature) {
        this.miniature = miniature;
    }

    public String getFull_image() {
        return full_image;
    }

    public void setFull_image(String full_image) {
        this.full_image = full_image;
    }

    public String getFull_directory() {
        return full_directory;
    }

    public void setFull_directory(String full_directory) {
        this.full_directory = full_directory;
    }

    public String getImage_category() {
        return image_category;
    }

    public void setImage_category(String image_category) {
        this.image_category = image_category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
