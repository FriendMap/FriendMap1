package com.friend_map.persistence_layer.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.friend_map.persistence_layer.entities.image.ProfileImage;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "user_table", indexes = {
        @Index(columnList = User.USERNAME, name = User.USERNAME+"_index")
})
public class User {

    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String NICKNAME = "nickname";
    public static final String PASSWORD = "password";
    public static final String ROLE = "role";
    public static final String USER_DATA = "userData";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID, columnDefinition = "BINARY(16)", unique = true)
    private UUID id;

    /** ЛОГИН */
    @Column(name = USERNAME, unique = true, nullable = false, length = 32)
    private String username;

    /** НИК */
    @Column(name = NICKNAME, unique = true, nullable = false, length = 32)
    private String nickname;

    /** ПАРОЛЬ */
    @Column(name = PASSWORD, nullable = false)
    @JsonIgnore
    private String password;

    /** РОЛЬ */
    @Column(name = ROLE, nullable = false, length = 24)
    private String role;

    /** ИСПОЛЬЗУЕТСЯ ДЛЯ ОПРЕДЕЛЕНИЯ ОТНОШЕНИЯ */
    @Transient
    @JsonSerialize
    @JsonDeserialize
    private String friedStatus;

    /** ДРУЗЬЯ */
    @Transient
    @JsonSerialize
    @JsonDeserialize
    private List<User> friends = new ArrayList<>();

    /**
     * АВАТАРКА TODO: ДОЛЖНО БЫТЬ 1 К 1, НЕ ПОСТАВИЛ ИЗ ЗА ПРОБЛЕМ С КЭШИРОВАНИЕМ
     *  */
    @JsonManagedReference
    @OneToMany(mappedBy = ProfileImage.USER, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<ProfileImage> profileImage = new ArrayList<>();

    /** ДАННЫЕ */
    @JsonIgnore
    @OneToMany(mappedBy = UserData.USER, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<UserData> userData = new ArrayList<>();

    public ProfileImage getProfileImage() {
        if (profileImage.iterator().hasNext()) {
            return profileImage.iterator().next();
        } else {
            return null;
        }
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage.clear();
        this.profileImage.add(profileImage);
    }

    public User(UUID id) {
        this.id = id;
    }

    public User() {}

    public User(UUID uuid, String username, String nickname) {
        this.id = uuid;
        this.username = username;
        this.nickname = nickname;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFriedStatus() {
        return friedStatus;
    }

    public void setFriedStatus(String friedStatus) {
        this.friedStatus = friedStatus;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
