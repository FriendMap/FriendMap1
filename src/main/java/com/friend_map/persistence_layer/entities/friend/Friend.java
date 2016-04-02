package com.friend_map.persistence_layer.entities.friend;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.friend_map.persistence_layer.entities.user.User;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "friend_table")
public class Friend {

    public static final String ID = "id";
    public static final String FIRST_USER_STATUS = "first_user_status";
    public static final String SECOND_USER_STATUS = "second_user_status";
    public static final String FIRST_USER = "first_user";
    public static final String SECOND_USER = "second_user";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID, columnDefinition = "BINARY(16)", unique = true)
    private UUID id;

    /** СТАТУС 1 ПОЛЬЗОВАТЕЛЯ
     * ОПРЕДЕЛЯЕТ В КАКОМ ОТНОШЕНИИ НАХОДИТСЯ ПОЛЬЗОВАТЕЛЬ С 2-ЫМ ПОЛЬЗОВАТЕЛЕМ
     * */
    @Column(name = FIRST_USER_STATUS, nullable = false)
    private String first_user_status;

    /** СТАТУС 2 ПОЛЬЗОВАТЕЛЯ
     * ОПРЕДЕЛЯЕТ В КАКОМ ОТНОШЕНИИ НАХОДИТСЯ ПОЛЬЗОВАТЕЛЬ С 1-ЫМ ПОЛЬЗОВАТЕЛЕМ
     * */
    @Column(name = SECOND_USER_STATUS, nullable = false)
    private String second_user_status;

    /** 1 ПОЛЬЗОВАТЕЛЬ */
    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = FIRST_USER)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private User first_user;

    /** 2 ПОЛЬЗОВАТЕЛЬ */
    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = SECOND_USER)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private User second_user;

    /** ПОЛУЧИТЬ УНИКАЛЬНЫЙ НОМЕР ОТНОШЕНИИ МЕЖДУ ПОЛЬЗОВАТЕЛЯМИ */
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirst_user_status() {
        return first_user_status;
    }

    public void setFirst_user_status(String this_user_status) {
        this.first_user_status = this_user_status;
    }

    public String getSecond_user_status() {
        return second_user_status;
    }

    public void setSecond_user_status(String another_user_status) {
        this.second_user_status = another_user_status;
    }

    public User getFirst_user() {
        return first_user;
    }

    public void setFirst_user(User this_user) {
        this.first_user = this_user;
    }

    public User getSecond_user() {
        return second_user;
    }

    public void setSecond_user(User another_user) {
        this.second_user = another_user;
    }

}
